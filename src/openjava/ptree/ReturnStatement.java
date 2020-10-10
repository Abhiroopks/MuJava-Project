// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class ReturnStatement extends NonLeaf implements Statement, ParseTree
{
    public ReturnStatement(final Expression p) {
        this.set(p);
    }
    
    public ReturnStatement() {
        this(null);
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
