// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.util.Vector;
import java.util.Enumeration;
import openjava.tools.DebugOut;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Hashtable;

public class ClosedEnvironment extends Environment
{
    protected Hashtable table;
    protected Hashtable symbol_table;
    
    public ClosedEnvironment(final Environment parent) {
        this.table = new Hashtable();
        this.symbol_table = new Hashtable();
        this.parent = parent;
    }
    
    public Hashtable getTable() {
        return this.table;
    }
    
    public Hashtable getSymbolTable() {
        return this.symbol_table;
    }
    
    @Override
    public String toString() {
        final StringWriter out = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(out);
        printWriter.println("ClosedEnvironment");
        printWriter.println("class object table : " + this.table);
        printWriter.println("binding table : " + this.symbol_table);
        printWriter.println("parent env : " + this.parent);
        printWriter.flush();
        return out.toString();
    }
    
    @Override
    public void record(final String str, final OJClass value) {
        DebugOut.println("ClosedEnvironment#record() : " + str + " " + value.getName());
        final OJClass put = (OJClass) this.table.put(str, value);
        if (put != null) {
            System.err.println(str + " is already binded on " + put.toString());
        }
    }
    
    @Override
    public void recordGenerics(final String str, final OJClass ojClass) {
        DebugOut.println("ClosedEnvironment#recordGenerics() : " + str + " " + ojClass.getName());
        if (this.parent != null && !(this.parent instanceof GlobalEnvironment)) {
            this.parent.record(str, ojClass);
        }
    }
    
    @Override
    public OJClass lookupClass(final String key) {
        final OJClass ojClass = (OJClass) this.table.get(key);
        if (ojClass != null) {
            return ojClass;
        }
        return this.parent.lookupClass(key);
    }
    
    @Override
    public void bindVariable(final String key, final OJClass value) {
        this.symbol_table.put(key, value);
    }
    
    @Override
    public OJClass lookupBind(final String key) {
        final OJClass ojClass = (OJClass) this.symbol_table.get(key);
        if (ojClass != null) {
            return ojClass;
        }
        if (this.parent == null) {
            return null;
        }
        return this.parent.lookupBind(key);
    }
    
    @Override
    public String toQualifiedName(final String anObject) {
        final Enumeration<String> keys = this.table.keys();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            if (s.equals(anObject)) {
                return s;
            }
        }
        return this.parent.toQualifiedName(anObject);
    }
    
    @Override
    public Vector getImportedClasses() {
        return this.parent.getImportedClasses();
    }
    
    @Override
    public Vector getImportedPackages() {
        return this.parent.getImportedPackages();
    }
    
    @Override
    public Environment getParentEnvironment() {
        return this.parent;
    }
}
