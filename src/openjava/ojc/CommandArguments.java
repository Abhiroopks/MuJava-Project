// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ojc;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;
import java.io.IOException;
import java.io.File;
import java.util.Hashtable;

public class CommandArguments
{
    public static final int DEBUG_VERBOSE = 4;
    public static final int DEBUG_CALLER = 8;
    private final String[] originalArgs;
    private Hashtable options;
    private File[] files;
    
    public CommandArguments(final String[] originalArgs) throws IOException {
        this.options = new Hashtable();
        this.originalArgs = originalArgs;
        this.files = this.initFiles();
        this.checkArguments();
    }
    
    public File[] getFiles() {
        return this.files;
    }
    
    public File[] initFiles() {
        if (this.originalArgs == null) {
            return new File[0];
        }
        final Vector<File> vector = new Vector<File>();
        for (int i = 0; i < this.originalArgs.length; ++i) {
            if (isOption(this.originalArgs[i])) {
                this.registerOption(this.originalArgs[i].substring(1));
            }
            else {
                addFiles(this.originalArgs[i], vector);
            }
        }
        final File[] array = new File[vector.size()];
        for (int j = 0; j < array.length; ++j) {
            array[j] = vector.elementAt(j);
        }
        return array;
    }
    
    private static void addFiles(final String str, final Vector vector) {
        if (!str.startsWith("@")) {
            vector.add(new File(str));
            return;
        }
        FileReader in;
        try {
            in = new FileReader(str.substring(1));
        }
        catch (IOException ex) {
            System.err.println("Bad file name ignored : " + str);
            return;
        }
        try {
            String line;
            while ((line = new BufferedReader(in).readLine()) != null) {
                final String trim = line.trim();
                if (trim.equals("")) {
                    break;
                }
                vector.add(new File(trim));
            }
        }
        catch (IOException ex2) {
            System.err.println("Bad file format : " + str);
        }
    }
    
    private static boolean isOption(final String s) {
        return s != null && s.startsWith("-");
    }
    
    private static int countUpOptions(final String[] array) {
        if (array == null) {
            return 0;
        }
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (isOption(array[i])) {
                ++n;
            }
        }
        return n;
    }
    
    public void registerOption(final String s) {
        Vector<String> value = (Vector<String>) this.options.get(optionKind(s));
        if (value == null) {
            value = new Vector<String>();
        }
        value.addElement(optionValue(s));
        this.options.put(optionKind(s), value);
    }
    
    private static String optionKind(final String s) {
        final int index = s.indexOf(61);
        return (index == -1) ? s : s.substring(0, index).trim();
    }
    
    private static String optionValue(final String s) {
        final int index = s.indexOf(61);
        return (index == -1) ? "" : s.substring(index + 1).trim();
    }
    
    public String getOption(final String key) {
        final Vector<String> vector = (Vector<String>) this.options.get(key);
        if (vector == null || vector.isEmpty()) {
            return null;
        }
        return vector.elementAt(0);
    }
    
    public String[] getOptions(final String key) {
        final Vector<String> vector = (Vector<String>) this.options.get(key);
        final String[] array = new String[(vector == null) ? 0 : vector.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = vector.elementAt(i);
        }
        return array;
    }
    
    public String getOption(final String s, final String s2) {
        String s3 = this.getOption(s);
        if (s3 == null) {
            s3 = this.getOption(s2);
        }
        return s3;
    }
    
    public int getDebugLevel() {
        int intValue = 0;
        final String option = this.getOption("g");
        if (option != null) {
            intValue = Integer.valueOf(option);
        }
        if (this.getOption("verbose") != null) {
            intValue |= 0x4;
        }
        return intValue;
    }
    
    public JavaCompiler getJavaCompiler() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String option = this.getOption("compiler", "c");
        if (option == null) {
            option = "jp.ac.tsukuba.openjava.SunJavaCompiler";
        }
        return (JavaCompiler)Class.forName(option).newInstance();
    }
    
    public boolean callerTranslation() {
        return this.getOption("calleroff") == null;
    }
    
    public boolean qualifyNameFirst() {
        String option = this.getOption("callerfast");
        if (option == null) {
            option = "true";
        }
        return !option.equals("false");
    }
    
    private void checkArguments() throws IOException {
        final File[] files = this.getFiles();
        if (files.length == 0) {
            System.err.println("Files are not specified.");
            throw new IOException();
        }
        for (int i = 0; i < files.length; ++i) {
            if (!files[i].canRead()) {
                System.err.println("cannot find file " + files[i]);
                throw new IOException();
            }
            if (!files[i].getName().endsWith(".oj")) {
                System.err.println("illegal file name " + files[i]);
                throw new IOException();
            }
        }
        final String option = this.getOption("d");
        if (option != null && !new File(option).isDirectory()) {
            System.err.println("Directory does not exist : " + option);
            throw new IOException();
        }
    }
}
