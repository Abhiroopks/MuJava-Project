// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.util.StringTokenizer;
import java.io.IOException;
import java.util.Enumeration;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Hashtable;

public final class MetaInfo
{
    public static final String METACLASS_KEY = "instantiates";
    public static final String DEFAULT_METACLASS = "openjava.mop.OJClass";
    public static final String SUFFIX = "OJMI";
    public static final String FIELD_NAME = "dict";
    private Hashtable table;
    private String packname;
    private String simpleclassname;
    
    public MetaInfo(final String value, final String s) {
        (this.table = new Hashtable()).put("instantiates", value);
        this.simpleclassname = Environment.toSimpleName(s);
        this.packname = Environment.toPackageName(s);
    }
    
    public MetaInfo(final String s) {
        this(defaultMetaclass(s), s);
    }
    
    static String defaultMetaclass(final String s) {
        final Class metabind = OJSystem.getMetabind(s);
        if (metabind != null) {
            return metabind.getName();
        }
        return "openjava.mop.OJClass";
    }
    
    private String qualifiedClassName() {
        if (this.packname == null || this.packname.equals("")) {
            return this.simpleclassname;
        }
        return this.packname + "." + this.simpleclassname;
    }
    
    public MetaInfo(final Class clazz) {
        this(clazz.getName());
        final String string = clazz.getName() + "OJMI";
        try {
            final String[][] array = (String[][])Class.forName(string).getField("dict").get(null);
            for (int i = 0; i < array.length; ++i) {
                this.table.put(array[i][0], array[i][1]);
            }
        }
        catch (ClassNotFoundException ex) {
            this.table.put("instantiates", defaultMetaclass(clazz.getName()));
        }
        catch (Exception obj) {
            System.err.println("meta information class " + string + " has an illegal structure. : " + obj);
            this.table.put("instantiates", defaultMetaclass(clazz.getName()));
        }
    }
    
    public void write(final Writer out) throws IOException {
        final PrintWriter printWriter = new PrintWriter(out);
        printWriter.println("/*this file is generated by OpenJava system.*/");
        printWriter.println(this.makePack());
        printWriter.println("public final class " + this.simpleclassname + "OJMI");
        printWriter.println("{");
        printWriter.println("public static final String[][] dict={");
        final Enumeration keys = this.keys();
        final Enumeration elements = this.elements();
        if (keys.hasMoreElements()) {
            printWriter.print(" ");
            this.printSet(printWriter, keys.nextElement(), elements.nextElement());
        }
        while (keys.hasMoreElements()) {
            printWriter.print(",");
            this.printSet(printWriter, keys.nextElement(), elements.nextElement());
        }
        printWriter.println("};");
        printWriter.println("}");
        printWriter.flush();
    }
    
    private String makePack() {
        if (this.packname == null || this.packname.equals("")) {
            return "";
        }
        return "package " + this.packname + ";";
    }
    
    private void printSet(final PrintWriter printWriter, final Object o, final Object o2) {
        final String s = (String)o;
        final String s2 = (String)o2;
        printWriter.print("{\"");
        printWriter.print(toFlattenString(s));
        printWriter.print("\",\"");
        printWriter.print(toFlattenString(s2));
        printWriter.println("\"}");
    }
    
    public String put(final String key, final String value) {
        return (String) this.table.put(key, value);
    }
    
    public String get(final String key) {
        return (String) this.table.get(key);
    }
    
    public Enumeration keys() {
        return this.table.keys();
    }
    
    public Enumeration elements() {
        return this.table.elements();
    }
    
    public static String toFlattenString(final String str) {
        final StringTokenizer stringTokenizer = new StringTokenizer(str, "\\\"", true);
        final StringBuffer sb = new StringBuffer();
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            if (nextToken.equals("\\") || nextToken.equals("\"")) {
                sb.append("\\");
            }
            sb.append(nextToken);
        }
        final StringTokenizer stringTokenizer2 = new StringTokenizer(sb.toString(), "\n\r", false);
        final StringBuffer sb2 = new StringBuffer();
        while (stringTokenizer2.hasMoreTokens()) {
            sb2.append(" " + stringTokenizer2.nextToken());
        }
        return sb2.toString().trim();
    }
}
