// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.mop.OJClass;
import openjava.mop.Environment;
import openjava.ptree.util.ParseTreeVisitor;

public class SelfAccess extends Leaf implements Expression
{
    public static final int THIS = 0;
    public static final int SUPER = 1;
    private static SelfAccess _constantSuper;
    private static SelfAccess _constantThis;
    protected String qualifier;
    int id;
    
    SelfAccess() {
        this(null);
    }
    
    SelfAccess(final String s) {
        super(((s == null) ? "" : (s + ".")) + "this");
        this.qualifier = null;
        this.id = -1;
        this.qualifier = s;
        this.id = 0;
    }
    
    SelfAccess(final int id) {
        super((id == 1) ? "super" : "this");
        this.qualifier = null;
        this.id = -1;
        this.id = id;
    }
    
    public String getQualifier() {
        return this.qualifier;
    }
    
    public int getAccessType() {
        return this.id;
    }
    
    public boolean isSuperAccess() {
        return this.id == 1;
    }
    
    public static SelfAccess makeSuper() {
        return constantSuper();
    }
    
    public static SelfAccess makeThis() {
        return constantThis();
    }
    
    public static SelfAccess makeThis(final String s) {
        if (s == null || s.equals("")) {
            return constantThis();
        }
        return new SelfAccess(s);
    }
    
    public static SelfAccess constantSuper() {
        if (SelfAccess._constantSuper == null) {
            SelfAccess._constantSuper = new SelfAccess(1);
        }
        return SelfAccess._constantSuper;
    }
    
    public static SelfAccess constantThis() {
        if (SelfAccess._constantThis == null) {
            SelfAccess._constantThis = new SelfAccess();
        }
        return SelfAccess._constantThis;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        final OJClass lookupClass = environment.lookupClass(environment.currentClassName());
        if (this.isSuperAccess()) {
            return lookupClass.getSuperclass();
        }
        final String qualifier = this.getQualifier();
        if (qualifier == null) {
            return lookupClass;
        }
        return environment.lookupClass(environment.toQualifiedName(qualifier));
    }
    
    static {
        SelfAccess._constantSuper = null;
        SelfAccess._constantThis = null;
    }
}
