// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class AssertStatement extends NonLeaf implements Statement, ParseTree
{
    public AssertStatement(final Expression p) {
        this.set(p);
    }
    
    public AssertStatement(final Expression p2, final Expression p3) {
        this.set(p2, p3);
    }
    
    AssertStatement() {
    }
    
    public Expression getExpression() {
        return (Expression)this.elementAt(0);
    }
    
    public void setExpression(final Expression p) {
        this.setElementAt(p, 0);
    }
    
    public Expression getExpression2() {
        return (Expression)this.elementAt(1);
    }
    
    public void setExpression2(final Expression p) {
        this.setElementAt(p, 1);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
