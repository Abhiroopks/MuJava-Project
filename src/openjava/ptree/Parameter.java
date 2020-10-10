// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class Parameter extends NonLeaf
{
    boolean isVarargs;
    
    public Parameter(ModifierList p4, final TypeName p5, final String p6, final boolean isVarargs) {
        this.isVarargs = false;
        if (p4 == null) {
            p4 = new ModifierList();
        }
        this.set(p4, p5, p6);
        this.isVarargs = isVarargs;
    }
    
    public Parameter(final TypeName typeName, final String s) {
        this(new ModifierList(), typeName, s, false);
    }
    
    public ModifierList getModifiers() {
        return (ModifierList)this.elementAt(0);
    }
    
    public void setModifiers(final ModifierList p) {
        this.setElementAt(p, 0);
    }
    
    public TypeName getTypeSpecifier() {
        return (TypeName)this.elementAt(1);
    }
    
    public void setTypeSpecifier(final TypeName p) {
        this.setElementAt(p, 1);
    }
    
    public String getVariable() {
        return (String)this.elementAt(2);
    }
    
    public void setVariable(final String p) {
        this.setElementAt(p, 2);
    }
    
    public boolean isVarargs() {
        return this.isVarargs;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
