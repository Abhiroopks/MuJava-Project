// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.tools.DebugOut;
import java.util.Enumeration;
import java.util.Vector;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Hashtable;

public class GlobalEnvironment extends Environment
{
    private Hashtable table;
    
    public GlobalEnvironment() {
        this.table = new Hashtable();
    }
    
    @Override
    public String toString() {
        final StringWriter out = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(out);
        printWriter.println("GlobalEnvironment");
        printWriter.print("class object table: ");
        printWriter.println(this.table.toString());
        printWriter.flush();
        return out.toString();
    }
    
    private static void writeStringVector(final PrintWriter printWriter, final Vector vector) {
        final Enumeration<String> elements = vector.elements();
        while (elements.hasMoreElements()) {
            printWriter.print(elements.nextElement() + " ");
        }
    }
    
    public String getPackageName() {
        return null;
    }
    
    @Override
    public OJClass lookupClass(final String key) {
        if (key == null) {
            return null;
        }
        return (OJClass) this.table.get(key);
    }
    
    @Override
    public void record(final String s, final OJClass value) {
        String str;
        try {
            str = value.toString();
        }
        catch (Exception ex) {
            str = "<" + ex.toString() + ">";
        }
        DebugOut.println("Genv#record(): " + s + " " + str);
        this.table.put(s, value);
    }
    
    @Override
    public String toQualifiedName(final String s) {
        return s;
    }
    
    @Override
    public void bindVariable(final String s, final OJClass ojClass) {
        System.err.println("error : illegal binding on GlobalEnvironment");
    }
    
    @Override
    public OJClass lookupBind(final String s) {
        return null;
    }
}
