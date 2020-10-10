// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class CatchBlock extends NonLeaf
{
    public CatchBlock(final Parameter p2, StatementList p3) {
        if (p3 == null) {
            p3 = new StatementList();
        }
        this.set(p2, p3);
    }
    
    CatchBlock() {
    }
    
    public Parameter getParameter() {
        return (Parameter)this.elementAt(0);
    }
    
    public void setParameter(final Parameter p) {
        this.setElementAt(p, 0);
    }
    
    public StatementList getBody() {
        return (StatementList)this.elementAt(1);
    }
    
    public void setBody(final StatementList p) {
        this.setElementAt(p, 1);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
