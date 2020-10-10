// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class BreakStatement extends NonLeaf implements Statement
{
    public BreakStatement(final String p) {
        this.set(p);
    }
    
    public BreakStatement() {
        this(null);
    }
    
    public String getLabel() {
        return (String)this.elementAt(0);
    }
    
    public void setLabel(final String p) {
        this.setElementAt(p, 0);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
