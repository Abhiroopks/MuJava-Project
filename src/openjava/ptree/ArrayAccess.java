// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.mop.OJClass;
import openjava.mop.Environment;
import openjava.ptree.util.ParseTreeVisitor;

public class ArrayAccess extends NonLeaf implements Expression
{
    public ArrayAccess(final Expression p2, final Expression p3) {
        this.set(p2, p3);
    }
    
    ArrayAccess() {
    }
    
    public Expression getReferenceExpr() {
        return (Expression)this.elementAt(0);
    }
    
    public void setReferenceExpr(final Expression p) {
        this.setElementAt(p, 0);
    }
    
    public Expression getIndexExpr() {
        return (Expression)this.elementAt(1);
    }
    
    public void setIndexExpr(final Expression p) {
        this.setElementAt(p, 1);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        return this.getReferenceExpr().getType(environment).getComponentType();
    }
}
