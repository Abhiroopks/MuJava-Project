// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import openjava.tools.DebugOut;
import openjava.tools.parser.ParseException;
import java.util.Hashtable;
import openjava.ojc.JavaCompiler;
import java.util.Vector;

public class OJSystem
{
    public static OJClass BOOLEAN;
    public static OJClass BYTE;
    public static OJClass CHAR;
    public static OJClass SHORT;
    public static OJClass INT;
    public static OJClass LONG;
    public static OJClass FLOAT;
    public static OJClass DOUBLE;
    public static OJClass VOID;
    public static OJClass STRING;
    public static OJClass OBJECT;
    public static OJClass NULLTYPE;
    public static String NextLineChar;
    public static final String NULLTYPE_NAME = "<type>null";
    public static final GlobalEnvironment env;
    private static final Vector additionalClasses;
    private static JavaCompiler javac;
    private static Hashtable table;
    public static Object orderingLock;
    public static OJClass waited;
    public static final Hashtable underConstruction;
    public static final Vector waitingPool;
    
    public static final void initConstants() {
        OJSystem.BOOLEAN = OJClass.forClass(Boolean.TYPE);
        OJSystem.BYTE = OJClass.forClass(Byte.TYPE);
        OJSystem.CHAR = OJClass.forClass(Character.TYPE);
        OJSystem.SHORT = OJClass.forClass(Short.TYPE);
        OJSystem.INT = OJClass.forClass(Integer.TYPE);
        OJSystem.LONG = OJClass.forClass(Long.TYPE);
        OJSystem.FLOAT = OJClass.forClass(Float.TYPE);
        OJSystem.DOUBLE = OJClass.forClass(Double.TYPE);
        OJSystem.VOID = OJClass.forClass(Void.TYPE);
        OJSystem.STRING = OJClass.forClass(String.class);
        OJSystem.OBJECT = OJClass.forClass(Object.class);
        OJSystem.NULLTYPE = new OJClass();
        OJSystem.env.record("<type>null", OJSystem.NULLTYPE);
    }
    
    public static void addNewClass(final OJClass obj) throws MOPException {
        OJSystem.additionalClasses.addElement(obj);
        OJSystem.env.record(obj.getName(), obj);
    }
    
    public static JavaCompiler getJavaCompiler() {
        return OJSystem.javac;
    }
    
    public static void setJavaCompiler(final JavaCompiler javac) {
        OJSystem.javac = javac;
    }
    
    public static OJClass[] addedClasses() {
        final OJClass[] array = new OJClass[OJSystem.additionalClasses.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (OJClass)OJSystem.additionalClasses.elementAt(i);
        }
        return array;
    }
    
    public static void metabind(final String key, String s) throws ParseException {
        if (s == null) {
            if (OJSystem.table.get(key) != null) {
                return;
            }
            s = "openjava.mop.OJClass";
        }
        Class<?> forName;
        try {
            forName = Class.forName(s);
        }
        catch (ClassNotFoundException ex) {
            throw new ParseException(ex.toString());
        }
        DebugOut.println("class " + key + " : " + s);
        OJSystem.table.put(key, forName);
    }
    
    public static Class getMetabind(final String s) {
        Class<OJClass> searchMetaclassInTable = (Class<OJClass>)searchMetaclassInTable(s);
        if (searchMetaclassInTable == null) {
            searchMetaclassInTable = OJClass.class;
        }
        return searchMetaclassInTable;
    }
    
    private static Class searchMetaclassInTable(final String key) {
        final Class clazz = (Class) OJSystem.table.get(key);
        if (clazz != null) {
            return clazz;
        }
        final Class clazz2 = (Class) OJSystem.table.get(toPackageSuffix(key) + "*");
        if (clazz2 != null) {
            return clazz2;
        }
        final Class clazz3 = (Class) OJSystem.table.get(toPackageSuffix(key) + "-");
        if (clazz3 != null) {
            return clazz3;
        }
        for (String s = toPackage(key); s != null; s = toPackage(s)) {
            final Class clazz4 = (Class) OJSystem.table.get(toPackageSuffix(s) + "-");
            if (clazz4 != null) {
                return clazz4;
            }
        }
        return null;
    }
    
    private static String toPackageSuffix(final String s) {
        return s.substring(0, s.lastIndexOf(".") + 1);
    }
    
    private static String toPackage(final String s) {
        final int lastIndex = s.lastIndexOf(".");
        if (lastIndex == -1) {
            return null;
        }
        return s.substring(0, lastIndex);
    }
    
    static {
        final StringWriter out = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(out);
        printWriter.println();
        printWriter.close();
        OJSystem.NextLineChar = out.toString();
        env = new GlobalEnvironment();
        additionalClasses = new Vector();
        OJSystem.javac = null;
        OJSystem.table = new Hashtable();
        OJSystem.waited = null;
        underConstruction = new Hashtable();
        waitingPool = new Vector();
    }
}
