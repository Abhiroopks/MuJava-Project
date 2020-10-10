// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class DoWhileStatement extends NonLeaf implements Statement, ParseTree
{
    public DoWhileStatement(final StatementList p2, final Expression p3) {
        this.set(p2, p3);
    }
    
    DoWhileStatement() {
    }
    
    public StatementList getStatements() {
        return (StatementList)this.elementAt(0);
    }
    
    public void setStatements(final StatementList p) {
        this.setElementAt(p, 0);
    }
    
    public Expression getExpression() {
        return (Expression)this.elementAt(1);
    }
    
    public void setExpression(final Expression p) {
        this.setElementAt(p, 1);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
