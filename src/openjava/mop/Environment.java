// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.util.Vector;

public abstract class Environment
{
    protected Environment parent;
    
    public Environment() {
        this.parent = null;
    }
    
    public Environment(final Environment parent) {
        this.parent = parent;
    }
    
    @Override
    public abstract String toString();
    
    public String getPackage() {
        if (this.parent == null) {
            System.err.println("Environment.getPackage() : not specified.");
            return null;
        }
        return this.parent.getPackage();
    }
    
    public OJClass lookupClass(final String s) {
        if (this.parent == null) {
            System.err.println("Environment.lookupClass() : not specified.");
            return null;
        }
        return this.parent.lookupClass(s);
    }
    
    public abstract void record(final String p0, final OJClass p1);
    
    public OJClass lookupBind(final String s) {
        if (this.parent == null) {
            return null;
        }
        return this.parent.lookupBind(s);
    }
    
    public abstract void bindVariable(final String p0, final OJClass p1);
    
    public String toQualifiedName(final String s) {
        if (this.parent == null) {
            return s;
        }
        return this.parent.toQualifiedName(s);
    }
    
    public String toQualifiedNameForEnum(final String s) {
        if (this.parent == null) {
            return s;
        }
        return this.parent.toQualifiedNameForEnum(s);
    }
    
    public static boolean isQualifiedName(final String s) {
        return s.indexOf(46) >= 0;
    }
    
    public static String toSimpleName(final String s) {
        final int lastIndex = s.lastIndexOf(46);
        if (lastIndex < 0) {
            return s;
        }
        return s.substring(lastIndex + 1);
    }
    
    public static String toPackageName(final String s) {
        final int lastIndex = s.lastIndexOf(46);
        return (lastIndex < 0) ? "" : s.substring(0, lastIndex);
    }
    
    public String currentClassName() {
        if (this.parent == null) {
            return null;
        }
        return this.parent.currentClassName();
    }
    
    public boolean isRegisteredModifier(final String s) {
        return this.parent != null && this.parent.isRegisteredModifier(s);
    }
    
    public Vector getImportedClasses() {
        return this.parent.getImportedClasses();
    }
    
    public Vector getImportedPackages() {
        return this.parent.getImportedPackages();
    }
    
    public void recordGenerics(final String s, final OJClass ojClass) {
    }
    
    public Environment getParentEnvironment() {
        return this.parent;
    }
}
