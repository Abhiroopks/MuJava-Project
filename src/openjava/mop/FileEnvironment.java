// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.tools.DebugOut;
import openjava.ptree.ClassDeclarationList;
import openjava.ptree.ClassDeclaration;
import java.util.Enumeration;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import openjava.ptree.CompilationUnit;
import java.util.Hashtable;
import java.util.Vector;
import java.io.File;

public class FileEnvironment extends Environment
{
    private final File file;
    private String packageName;
    private String pubclassName;
    private final Vector localClasses;
    private Vector importedClasses;
    private Vector importedPackages;
    private final Hashtable localClassTable;
    
    public FileEnvironment(final Environment environment, final String s, final String s2) {
        this(environment, s, s2, null);
    }
    
    public FileEnvironment(final Environment environment, String packageName, final String pubclassName, final File file) {
        super(environment);
        this.localClasses = new Vector();
        this.importedClasses = new Vector();
        this.importedPackages = new Vector();
        this.localClassTable = new Hashtable();
        if (packageName != null && packageName.equals("")) {
            packageName = null;
        }
        this.packageName = packageName;
        this.pubclassName = pubclassName;
        this.file = file;
        this.importPackage("java.lang");
    }
    
    public FileEnvironment(final Environment environment) {
        this(environment, null, null, null);
    }
    
    public FileEnvironment(final Environment environment, final CompilationUnit compilationUnit, final String s) {
        this(environment, compilationUnit.getPackage(), s);
        this.setCompilationUnit(compilationUnit);
    }
    
    @Override
    public String toString() {
        final StringWriter out = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(out);
        printWriter.println("FileEnvironment");
        printWriter.println("\tfile : " + this.file);
        printWriter.println("\tpackage : " + this.packageName);
        printWriter.println("\tmain class : " + this.file);
        printWriter.println("\tmain class : " + this.file);
        printWriter.print("\tlocal classes : ");
        writeStringVector(printWriter, this.localClasses);
        printWriter.println();
        printWriter.print("\timported classes : ");
        writeStringVector(printWriter, this.importedClasses);
        printWriter.println();
        printWriter.print("\timported packages : ");
        writeStringVector(printWriter, this.importedPackages);
        printWriter.println();
        printWriter.println("\tlocal class table : " + this.localClassTable);
        printWriter.println("parent env : " + this.parent);
        printWriter.flush();
        return out.toString();
    }
    
    private static void writeStringVector(final PrintWriter printWriter, final Vector vector) {
        final Enumeration<String> elements = vector.elements();
        while (elements.hasMoreElements()) {
            printWriter.print(elements.nextElement() + " ");
        }
    }
    
    public File getFile() {
        return this.file;
    }
    
    public void setPackage(String packageName) {
        if (packageName != null && packageName.equals("")) {
            packageName = null;
        }
        this.packageName = packageName;
    }
    
    @Override
    public String getPackage() {
        if (this.packageName == "") {
            return null;
        }
        return this.packageName;
    }
    
    public void setCompilationUnit(final CompilationUnit compilationUnit) {
        this.packageName = compilationUnit.getPackage();
        try {
            final ClassDeclaration publicClass = compilationUnit.getPublicClass();
            if (publicClass != null) {
                this.pubclassName = publicClass.getName();
            }
        }
        catch (Exception ex) {}
        final String[] declaredImports = compilationUnit.getDeclaredImports();
        for (int i = 0; i < declaredImports.length; ++i) {
            if (CompilationUnit.isOnDemandImport(declaredImports[i])) {
                this.importPackage(CompilationUnit.trimOnDemand(declaredImports[i]));
            }
            else {
                this.importClass(declaredImports[i]);
            }
        }
        final ClassDeclarationList classDeclarations = compilationUnit.getClassDeclarations();
        for (int j = 0; j < classDeclarations.size(); ++j) {
            final ClassDeclaration value = classDeclarations.get(j);
            if (!value.getModifiers().contains(1)) {
                this.recordLocalClassName(value.getName());
            }
        }
    }
    
    @Override
    public OJClass lookupClass(final String key) {
        final OJClass ojClass = (OJClass) this.localClassTable.get(key);
        if (ojClass != null) {
            return ojClass;
        }
        OJClass forName;
        try {
            forName = OJClass.forName(key);
        }
        catch (OJClassNotFoundException ex) {
            return null;
        }
        return forName;
    }
    
    @Override
    public void record(final String s, final OJClass ojClass) {
        DebugOut.println("Fenv#record(): " + s + " " + ojClass);
        this.localClassTable.put(s, ojClass);
        if (this.parent != null) {
            this.parent.record(s, ojClass);
        }
    }
    
    public boolean importClass(final String s) {
        DebugOut.println("FileEnvironment#importClass() : " + s);
        final String simpleName = Environment.toSimpleName(s);
        if (this.isAlreadyImportedClass(s) || this.isCrashingClassName(simpleName)) {
            return false;
        }
        this.importedClasses.addElement(s);
        return true;
    }
    
    public void importPackage(final String s) {
        DebugOut.println("FileEnvironment#importPackage() : " + s);
        this.importedPackages.addElement(s);
    }
    
    @Override
    public String toQualifiedName(final String str) {
        if (str == null || isPrimitiveType(str) || Environment.isQualifiedName(str)) {
            return str;
        }
        if (str.endsWith("[]")) {
            return this.toQualifiedName(str.substring(0, str.length() - 2)) + "[]";
        }
        if ((this.file != null && this.file.getName().endsWith(str + ".oj")) || this.localClasses.indexOf(str) != -1) {
            final String package1 = this.getPackage();
            if (package1 == null) {
                return str;
            }
            return package1 + "." + str;
        }
        else {
            final String searchImportedClasses = this.searchImportedClasses(str);
            if (searchImportedClasses != null) {
                return searchImportedClasses;
            }
            final String searchImportedPackages = this.searchImportedPackages(str);
            if (searchImportedPackages != null) {
                return searchImportedPackages;
            }
            return str;
        }
    }
    
    public void recordLocalClassName(final String s) {
        DebugOut.println("FileEnvironment#recordLocalClassName() : " + Environment.toSimpleName(s));
        this.localClasses.addElement(Environment.toSimpleName(s));
    }
    
    private static final boolean isPrimitiveType(final String s) {
        return s != null && (s.equals("boolean") || s.equals("byte") || s.equals("char") || s.equals("double") || s.equals("float") || s.equals("int") || s.equals("long") || s.equals("short") || s.equals("void"));
    }
    
    private String searchImportedClasses(String string) {
        final String str = string;
        if (string.indexOf("<") > 0 && string.indexOf(">") > 0) {
            string = string.substring(0, string.indexOf("<")) + string.substring(string.lastIndexOf(">") + 1, string.length());
        }
        final String string2 = "." + string;
        for (int i = 0; i < this.importedClasses.size(); ++i) {
            final String s = (String) this.importedClasses.elementAt(i);
            if (s.endsWith(string2) && str.indexOf("<") > 0 && str.indexOf(">") > 0) {
                return s.substring(0, s.lastIndexOf(".") + 1) + str;
            }
            if (s.endsWith(string2)) {
                return s;
            }
        }
        return null;
    }
    
    private boolean isAlreadyImportedClass(final String anObject) {
        for (int i = 0; i < this.importedClasses.size(); ++i) {
            if (((String)this.importedClasses.elementAt(i)).equals(anObject)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isCrashingClassName(final String s) {
        return this.searchImportedClasses(s) != null;
    }
    
    private String searchImportedPackages(final String s) {
        final String package1 = this.getPackage();
        String string;
        if (package1 == null || package1.equals("")) {
            string = s;
        }
        else {
            if (package1.equals(s)) {
                return null;
            }
            string = package1 + "." + s;
        }
        if (this.theClassExists(string)) {
            return string;
        }
        String string2 = null;
        for (int i = 0; i < this.importedPackages.size(); ++i) {
            final String s2 = (String) this.importedPackages.elementAt(i);
            if (this.theClassExists(s2 + "." + s)) {
                string2 = s2 + "." + s;
            }
        }
        return string2;
    }
    
    private boolean theClassExists(final String s) {
        return this.lookupClass(s) != null;
    }
    
    @Override
    public void bindVariable(final String s, final OJClass ojClass) {
        System.err.println("error : illegal binding on FileEnvironment");
    }
    
    @Override
    public OJClass lookupBind(final String s) {
        return null;
    }
    
    @Override
    public Vector getImportedClasses() {
        return this.importedClasses;
    }
    
    @Override
    public Vector getImportedPackages() {
        return this.importedPackages;
    }
    
    @Override
    public Environment getParentEnvironment() {
        return this.parent;
    }
}
