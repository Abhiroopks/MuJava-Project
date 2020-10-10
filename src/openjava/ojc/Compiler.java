// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ojc;

import java.util.ArrayList;
import openjava.tools.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import openjava.tools.parser.Parser;
import java.io.FileInputStream;
import openjava.ptree.util.ExpansionApplier;
import java.util.Hashtable;
import openjava.ptree.util.MemberAccessCorrector;
import openjava.ptree.util.TypeNameQualifier;
import openjava.mop.OJClassNotFoundException;
import openjava.ptree.ParseTreeException;
import java.io.IOException;
import openjava.ptree.util.ParseTreeVisitor;
import openjava.ptree.util.SourceCodeWriter;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import openjava.ptree.ClassDeclaration;
import openjava.mop.OJClass;
import openjava.ptree.ClassDeclarationList;
import openjava.mop.Environment;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import openjava.mop.OJSystem;
import openjava.tools.DebugOut;
import openjava.ptree.CompilationUnit;
import openjava.mop.FileEnvironment;
import java.io.File;

public class Compiler
{
    final CommandArguments arguments;
    final File[] files;
    final FileEnvironment[] file_env;
    final CompilationUnit[] comp_unit;
    JavaCompiler java_compiler;
    CompilationUnit[] added_cu;
    private static final String unknownClassName = "OJ_Unknown";
    private static int nonpubclassid;
    
    Compiler(final CommandArguments arguments) {
        this.added_cu = null;
        this.arguments = arguments;
        this.files = arguments.getFiles();
        this.file_env = new FileEnvironment[this.files.length];
        this.comp_unit = new CompilationUnit[this.files.length];
        DebugOut.setDebugLevel(arguments.getDebugLevel());
        initPrimitiveTypes();
        try {
            OJSystem.setJavaCompiler(this.java_compiler = arguments.getJavaCompiler());
        }
        catch (Exception obj) {
            System.err.println("illegal java compiler : " + obj);
        }
    }
    
    public void run() {
        try {
            this.configSpecifiedMetaBind();
        }
        catch (Exception x) {
            System.err.println(x);
        }
        this.initFileEnv();
        System.err.println("Generating parse tree.");
        this.generateParseTree();
        System.err.println("..done.");
        System.err.println("Initializing parse tree.");
        this.initParseTree();
        System.err.println("..done.");
        System.err.println("Translating callee side");
        this.translateCalleeSide();
        System.err.println("..done.");
        System.err.println("Translating caller side");
        if (this.arguments.callerTranslation()) {
            this.translateCallerSide();
            System.err.println("..done.");
        }
        else {
            System.err.println("..skipped.");
        }
        this.generateAdditionalCompilationUnit();
        System.err.println("Printing parse tree.");
        this.outputToFile();
        System.err.println("..done.");
        System.err.println("Compiling into bytecode.");
        this.javac();
        System.err.println("..done.");
        System.err.flush();
    }
    
    void configSpecifiedMetaBind() throws Exception {
        final String[] options = this.arguments.getOptions("-default-meta");
        for (int i = 0; i < options.length; ++i) {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(options[i]));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final StringTokenizer stringTokenizer = new StringTokenizer(line);
                try {
                    OJSystem.metabind(stringTokenizer.nextToken(), stringTokenizer.nextToken());
                }
                catch (NoSuchElementException ex) {}
            }
            bufferedReader.close();
        }
    }
    
    void initFileEnv() {
        for (int i = 0; i < this.files.length; ++i) {
            this.file_env[i] = new FileEnvironment(OJSystem.env, null, null, this.files[i]);
        }
    }
    
    void generateParseTree() {
        for (int i = 0; i < this.files.length; ++i) {
            DebugOut.println("parsing file " + this.files[i]);
            try {
                this.comp_unit[i] = parse(this.files[i]);
                this.file_env[i].setCompilationUnit(this.comp_unit[i]);
                final ClassDeclarationList classDeclarations = this.comp_unit[i].getClassDeclarations();
                for (int j = 0; j < classDeclarations.size(); ++j) {
                    final OJClass ojClass = this.makeOJClass(this.file_env[i], classDeclarations.get(j));
                    if (ojClass.getModifiers().isPublic()) {
                        DebugOut.println("main class " + ojClass.getName());
                    }
                    else {
                        DebugOut.println("local class " + ojClass.getName());
                    }
                    OJSystem.env.record(ojClass.getName(), ojClass);
                    recordInnerClasses(ojClass);
                }
            }
            catch (Exception obj) {
                System.err.println("errors during parsing. " + obj);
                obj.printStackTrace();
            }
            DebugOut.println("file environment : ");
            DebugOut.println(this.file_env[i]);
        }
        DebugOut.println("global environment : ");
        DebugOut.println(OJSystem.env);
    }
    
    private static void recordInnerClasses(final OJClass ojClass) {
        final OJClass[] declaredClasses = ojClass.getDeclaredClasses();
        for (int i = 0; i < declaredClasses.length; ++i) {
            OJSystem.env.record(declaredClasses[i].getName(), declaredClasses[i]);
            recordInnerClasses(declaredClasses[i]);
        }
    }
    
    void generateAdditionalCompilationUnit() {
        final OJClass[] addedClasses = OJSystem.addedClasses();
        this.added_cu = new CompilationUnit[addedClasses.length];
        for (int i = 0; i < addedClasses.length; ++i) {
            ClassDeclarationList list;
            try {
                list = new ClassDeclarationList(addedClasses[i].getSourceCode());
            }
            catch (Exception ex) {
                System.err.println("errors during generating " + addedClasses[i]);
                ex.printStackTrace();
                continue;
            }
            this.added_cu[i] = new CompilationUnit(addedClasses[i].getPackage(), null, list);
        }
    }
    
    private OJClass makeOJClass(final Environment environment, final ClassDeclaration classDeclaration) {
        final String qualifiedName = environment.toQualifiedName(classDeclaration.getName());
        final Class metabind = OJSystem.getMetabind(qualifiedName);
        OJClass ojClass;
        try {
            ojClass = (OJClass) metabind.getConstructor(Environment.class, OJClass.class, ClassDeclaration.class).newInstance(environment, null, classDeclaration);
        }
        catch (Exception ex) {
            System.err.println("errors during gererating a metaobject for " + qualifiedName);
            ex.printStackTrace();
            ojClass = new OJClass(environment, null, classDeclaration);
        }
        return ojClass;
    }
    
    void initDebug() {
    }
    
    void outputToFile() {
        for (int i = 0; i < this.comp_unit.length; ++i) {
            if (this.comp_unit[i] != null) {
                File file = null;
                try {
                    file = this.getOutputFile(this.files[i], this.file_env[i], this.comp_unit[i], ".java");
                    final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                    final SourceCodeWriter sourceCodeWriter = new SourceCodeWriter(printWriter);
                    sourceCodeWriter.setDebugLevel(0);
                    this.comp_unit[i].accept(sourceCodeWriter);
                    printWriter.flush();
                    printWriter.close();
                }
                catch (IOException ex4) {
                    System.err.println("fails to create " + file);
                }
                catch (ParseTreeException ex) {
                    System.err.println("errors during printing " + file);
                    ex.printStackTrace();
                }
                try {
                    file = this.getOutputFile(this.files[i], null, this.comp_unit[i], "OJMI.java");
                    final OJClass forName = OJClass.forName(this.file_env[i].toQualifiedName(baseName(this.files[i])));
                    final FileWriter fileWriter = new FileWriter(file);
                    forName.writeMetaInfo(fileWriter);
                    fileWriter.flush();
                    fileWriter.close();
                }
                catch (OJClassNotFoundException ex5) {}
                catch (IOException ex6) {
                    System.err.println("fails to create " + file);
                }
                catch (ParseTreeException ex2) {
                    System.err.println("errors during printing " + file);
                    ex2.printStackTrace();
                }
            }
        }
        for (int j = 0; j < this.added_cu.length; ++j) {
            File outputFile = null;
            try {
                outputFile = this.getOutputFile(null, null, this.added_cu[j], ".java");
                final PrintWriter printWriter2 = new PrintWriter(new FileWriter(outputFile));
                final SourceCodeWriter sourceCodeWriter2 = new SourceCodeWriter(printWriter2);
                sourceCodeWriter2.setDebugLevel(0);
                this.added_cu[j].accept(sourceCodeWriter2);
                printWriter2.flush();
                printWriter2.close();
            }
            catch (IOException ex7) {
                System.err.println("fails to create " + outputFile);
            }
            catch (ParseTreeException ex3) {
                System.err.println("errors during printing " + outputFile);
                ex3.printStackTrace();
            }
        }
    }
    
    private static final String class2path(final String s) {
        return s.replace('.', File.separatorChar);
    }
    
    private File getOutputFile(final File file, final FileEnvironment fileEnvironment, final CompilationUnit compilationUnit, final String str) throws ParseTreeException {
        final String package1 = compilationUnit.getPackage();
        final ClassDeclaration publicClass = compilationUnit.getPublicClass();
        String s;
        if (publicClass != null) {
            s = publicClass.getName();
        }
        else {
            if (fileEnvironment != null) {
                final String path = fileEnvironment.getFile().getPath();
                return new File(path.substring(0, path.lastIndexOf(46)) + str);
            }
            s = "OJ_Unknown" + Compiler.nonpubclassid++;
        }
        return this.getOutputFile(file, package1, s, str);
    }
    
    private File getOutputFile(final File file, final String s, final String str, final String str2) {
        String option = this.arguments.getOption("d");
        File file2;
        if (option == null && file != null) {
            String parent = file.getParent();
            if (parent == null) {
                parent = ".";
            }
            file2 = new File(parent);
        }
        else {
            if (option == null) {
                option = ".";
            }
            final File parent2 = new File(option);
            if (s == null || s.equals("")) {
                file2 = parent2;
            }
            else {
                file2 = new File(parent2, class2path(s));
                if (!file2.isDirectory() && !file2.mkdirs()) {
                    System.err.println("fail to create dir : " + file2);
                    file2 = new File(".");
                }
            }
        }
        return new File(file2, str + str2);
    }
    
    private static String baseName(final File file) {
        final String name = file.getName();
        final int lastIndex = name.lastIndexOf(46);
        if (lastIndex == -1) {
            return name;
        }
        return name.substring(0, lastIndex);
    }
    
    void initParseTree() {
        for (int i = 0; i < this.comp_unit.length; ++i) {
            if (this.comp_unit[i] == null) {
                System.err.println(this.files[i] + " is skipped.");
            }
            else {
                try {
                    if (this.arguments.callerTranslation() && this.arguments.qualifyNameFirst()) {
                        this.comp_unit[i].accept(new TypeNameQualifier(this.file_env[i]));
                    }
                    this.comp_unit[i].accept(new MemberAccessCorrector(this.file_env[i]));
                }
                catch (ParseTreeException ex) {
                    System.err.println("Encountered errors during analysis.");
                    ex.printStackTrace();
                }
            }
        }
    }
    
    void translateCalleeSide() {
        final Hashtable underConstruction = OJSystem.underConstruction;
        for (int i = 0; i < this.comp_unit.length; ++i) {
            if (this.comp_unit[i] == null) {
                System.err.println(this.files[i] + " is skipped.");
            }
            else {
                final ClassDeclarationList classDeclarations = this.comp_unit[i].getClassDeclarations();
                for (int j = 0; j < classDeclarations.size(); ++j) {
                    final String qualifiedName = this.file_env[i].toQualifiedName(classDeclarations.get(j).getName());
                    OJClass forName;
                    try {
                        forName = OJClass.forName(qualifiedName);
                    }
                    catch (OJClassNotFoundException obj) {
                        System.err.println("no " + qualifiedName + " : " + obj);
                        return;
                    }
                    this.translateClassDecls(underConstruction, forName, this.file_env[i]);
                }
            }
        }
        try {
            while (!underConstruction.isEmpty()) {
                this.resolveOrder((OJClass) underConstruction.keys().nextElement());
            }
        }
        catch (InterruptedException obj2) {
            System.err.println("translation failed : " + obj2);
        }
    }
    
    private void translateClassDecls(final Hashtable hashtable, final OJClass key, final Environment environment) {
        hashtable.put(key, new TranslatorThread(environment, key));
        final OJClass[] declaredClasses = key.getDeclaredClasses();
        for (int i = 0; i < declaredClasses.length; ++i) {
            this.translateClassDecls(hashtable, declaredClasses[i], key.getEnvironment());
        }
    }
    
    private void resolveOrder(final OJClass ojClass) throws InterruptedException {
        final Object o = new Object();
        final Hashtable underConstruction = OJSystem.underConstruction;
        synchronized (ojClass) {
            OJSystem.orderingLock = o;
            ((Thread) underConstruction.get(ojClass)).start();
            ojClass.wait();
        }
        while (OJSystem.waited != null) {
            this.resolveOrder(OJSystem.waited);
            synchronized (ojClass) {
                synchronized (OJSystem.orderingLock = o) {
                    o.notifyAll();
                }
                ojClass.wait();
            }
        }
        underConstruction.remove(ojClass);
    }
    
    void translateCallerSide() {
        for (int i = 0; i < this.comp_unit.length; ++i) {
            if (this.comp_unit[i] == null) {
                System.err.println(this.files[i] + " is skipped.");
            }
            else {
                try {
                    this.comp_unit[i].accept(new ExpansionApplier(this.file_env[i]));
                }
                catch (ParseTreeException ex) {
                    System.err.println("Encountered errors during translating caller side.");
                    ex.printStackTrace();
                }
            }
        }
    }
    
    private static CompilationUnit parse(final File file) {
        Parser parser;
        try {
            parser = new Parser(new FileInputStream(file));
        }
        catch (FileNotFoundException ex2) {
            System.err.println("File " + file + " not found.");
            return null;
        }
        CompilationUnit compilationUnit;
        try {
            compilationUnit = parser.CompilationUnit(OJSystem.env);
        }
        catch (ParseException ex) {
            System.err.println("Encountered errors during parse.");
            ex.printStackTrace();
            compilationUnit = null;
        }
        return compilationUnit;
    }
    
    private static void initPrimitiveTypes() {
        OJSystem.initConstants();
    }
    
    private void javac() {
        final ArrayList<String> list = new ArrayList<String>();
        final String[] options = this.arguments.getOptions("C");
        for (int i = 0; i < options.length; ++i) {
            list.add(options[i]);
        }
        try {
            for (int j = 0; j < this.files.length; ++j) {
                this.addCompiledFile(list, this.files[j], this.file_env[j], this.comp_unit[j], ".java");
                this.addCompiledFile(list, this.files[j], null, this.comp_unit[j], "OJMI.java");
            }
            for (int k = 0; k < this.added_cu.length; ++k) {
                this.addCompiledFile(list, this.files[k], null, this.added_cu[k], ".java");
                this.addCompiledFile(list, this.files[k], null, this.added_cu[k], "OJMI.java");
            }
            final String[] array = new String[list.size()];
            for (int l = 0; l < array.length; ++l) {
                array[l] = list.get(l);
            }
            this.java_compiler.compile(array);
        }
        catch (Exception ex) {
            System.err.println("errors during compiling into bytecode.");
            ex.printStackTrace();
        }
    }
    
    private void addCompiledFile(final ArrayList list, final File file, final FileEnvironment fileEnvironment, final CompilationUnit compilationUnit, final String s) {
        try {
            final File outputFile = this.getOutputFile(file, fileEnvironment, compilationUnit, s);
            if (outputFile.exists()) {
                list.add(outputFile.getPath());
            }
        }
        catch (ParseTreeException ex) {
            System.err.println(ex.toString());
        }
    }
    
    static {
        Compiler.nonpubclassid = 0;
    }
}
