// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class ConstructorInvocation extends NonLeaf
{
    private boolean _isSelfInvocation;
    
    public ConstructorInvocation(ExpressionList p) {
        this._isSelfInvocation = true;
        this._isSelfInvocation = true;
        if (p == null) {
            p = new ExpressionList();
        }
        this.set(p);
    }
    
    public ConstructorInvocation(ExpressionList p2, final Expression p3) {
        this._isSelfInvocation = true;
        this._isSelfInvocation = false;
        if (p2 == null) {
            p2 = new ExpressionList();
        }
        this.set(p2, p3);
    }
    
    ConstructorInvocation() {
        this._isSelfInvocation = true;
    }
    
    public boolean isSelfInvocation() {
        return this._isSelfInvocation;
    }
    
    public ExpressionList getArguments() {
        return (ExpressionList)this.elementAt(0);
    }
    
    public Expression getEnclosing() {
        return (Expression)this.elementAt(1);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
