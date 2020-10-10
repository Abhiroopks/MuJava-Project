// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class ForStatement extends NonLeaf implements Statement, ParseTree
{
    ForStatement() {
    }
    
    public ForStatement(final ExpressionList p3, final Expression p4, ExpressionList p5, StatementList p6) {
        if (p5 == null) {
            p5 = new ExpressionList();
        }
        if (p6 == null) {
            p6 = new StatementList();
        }
        this.set(null, null, p3, p4, p5, p6, null, null);
    }
    
    public ForStatement(final TypeName p5, final VariableDeclarator[] p6, final Expression expression, final ExpressionList list, StatementList list2) {
        if (list2 == null) {
            list2 = new StatementList();
        }
        if (p5 == null || p6 == null || p6.length == 0) {
            this.set(null, null, null, expression, list, list2, null);
        }
        else {
            this.set(p5, p6, null, expression, list, list2, null, null);
        }
    }
    
    public ForStatement(final String p5, final TypeName p6, final String p7, final Expression p8, StatementList p9) {
        if (p9 == null) {
            p9 = new StatementList();
        }
        this.set(p6, null, null, p8, null, p9, p7, p5);
    }
    
    public String getIdentifier() {
        return (String)this.elementAt(6);
    }
    
    public String getModifier() {
        return (String)this.elementAt(7);
    }
    
    public ExpressionList getInit() {
        return (ExpressionList)this.elementAt(2);
    }
    
    public void setInit(final ExpressionList p) {
        this.setElementAt(null, 0);
        this.setElementAt(null, 1);
        this.setElementAt(p, 2);
    }
    
    public TypeName getInitDeclType() {
        return (TypeName)this.elementAt(0);
    }
    
    public VariableDeclarator[] getInitDecls() {
        return (VariableDeclarator[])this.elementAt(1);
    }
    
    public void setInitDecl(final TypeName p2, final VariableDeclarator[] p3) {
        if (p2 == null || p3 == null || p3.length == 0) {
            this.setElementAt(null, 0);
            this.setElementAt(null, 1);
        }
        else {
            this.setElementAt(p2, 0);
            this.setElementAt(p3, 1);
        }
        this.setElementAt(null, 2);
    }
    
    public Expression getCondition() {
        return (Expression)this.elementAt(3);
    }
    
    public void setCondition(final Expression p) {
        this.setElementAt(p, 3);
    }
    
    public ExpressionList getIncrement() {
        return (ExpressionList)this.elementAt(4);
    }
    
    public void setIncrement(ExpressionList p) {
        if (p == null) {
            p = new ExpressionList();
        }
        this.setElementAt(p, 4);
    }
    
    public StatementList getStatements() {
        return (StatementList)this.elementAt(5);
    }
    
    public void setStatements(StatementList p) {
        if (p == null) {
            p = new StatementList();
        }
        this.setElementAt(p, 5);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
