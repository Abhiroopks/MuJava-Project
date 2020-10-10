// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class ExpressionStatement extends NonLeaf implements Statement
{
    public ExpressionStatement(final Expression p) {
        this.set(p);
    }
    
    ExpressionStatement() {
    }
    
    public Expression getExpression() {
        return (Expression)this.elementAt(0);
    }
    
    public void setExpression(final Expression p) {
        this.setElementAt(p, 0);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
