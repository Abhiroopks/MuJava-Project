// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class IfStatement extends NonLeaf implements Statement, ParseTree
{
    public IfStatement(final Expression p2, StatementList p3, StatementList p4) {
        if (p3 == null) {
            p3 = new StatementList();
        }
        if (p4 == null) {
            p4 = new StatementList();
        }
        this.set(p2, p3, p4);
    }
    
    public IfStatement(final Expression expression, final StatementList list) {
        this(expression, list, null);
    }
    
    IfStatement() {
    }
    
    public Expression getExpression() {
        return (Expression)this.elementAt(0);
    }
    
    public void setExpression(final Expression p) {
        this.setElementAt(p, 0);
    }
    
    public StatementList getStatements() {
        return (StatementList)this.elementAt(1);
    }
    
    public void setStatements(final StatementList p) {
        this.setElementAt(p, 1);
    }
    
    public StatementList getElseStatements() {
        return (StatementList)this.elementAt(2);
    }
    
    public void setElseStatements(final StatementList p) {
        this.setElementAt(p, 2);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
