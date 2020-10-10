// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class CaseGroup extends NonLeaf
{
    public CaseGroup(final ExpressionList p2, final StatementList p3) {
        this.set(p2, p3);
    }
    
    CaseGroup() {
    }
    
    public ExpressionList getLabels() {
        return (ExpressionList)this.elementAt(0);
    }
    
    public StatementList getStatements() {
        return (StatementList)this.elementAt(1);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
