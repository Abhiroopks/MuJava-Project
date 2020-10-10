// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class TryStatement extends NonLeaf implements Statement, ParseTree
{
    public TryStatement(StatementList p, CatchList p2, StatementList p3) {
        if (p == null) {
            p = new StatementList();
        }
        if (p2 == null) {
            p2 = new CatchList();
        }
        if (p3 == null) {
            p3 = new StatementList();
        }
        this.set(p, p2, p3);
    }
    
    public TryStatement(final StatementList list, final CatchList list2) {
        this(list, list2, new StatementList());
    }
    
    public TryStatement(final StatementList list, final StatementList list2) {
        this(list, new CatchList(), list2);
    }
    
    TryStatement() {
    }
    
    public StatementList getBody() {
        return (StatementList)this.elementAt(0);
    }
    
    public void setBody(final StatementList p) {
        this.setElementAt(p, 0);
    }
    
    public CatchList getCatchList() {
        return (CatchList)this.elementAt(1);
    }
    
    public void setCatchList(final CatchList p) {
        this.setElementAt(p, 1);
    }
    
    public StatementList getFinallyBody() {
        return (StatementList)this.elementAt(2);
    }
    
    public void setFinallyBody(final StatementList p) {
        this.setElementAt(p, 2);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
