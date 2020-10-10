// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class LabeledStatement extends NonLeaf implements Statement
{
    public LabeledStatement(final String p2, final Statement p3) {
        this.set(p2, p3);
    }
    
    LabeledStatement() {
    }
    
    public String getLabel() {
        return (String)this.elementAt(0);
    }
    
    public void setLabel(final String p) {
        this.setElementAt(p, 0);
    }
    
    public Statement getStatement() {
        return (Statement)this.elementAt(1);
    }
    
    public void setStatement(final Statement p) {
        this.setElementAt(p, 1);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
