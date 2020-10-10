// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class Block extends NonLeaf implements Statement
{
    public Block(StatementList p) {
        if (p == null) {
            p = new StatementList();
        }
        this.set(p);
    }
    
    public Block() {
        this(new StatementList());
    }
    
    public StatementList getStatements() {
        return (StatementList)this.elementAt(0);
    }
    
    public void setStatements(final StatementList p) {
        this.setElementAt(p, 0);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
