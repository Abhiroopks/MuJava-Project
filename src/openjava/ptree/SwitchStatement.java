// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class SwitchStatement extends NonLeaf implements Statement
{
    public SwitchStatement(final Expression p2, CaseGroupList p3) {
        if (p3 == null) {
            p3 = new CaseGroupList();
        }
        this.set(p2, p3);
    }
    
    SwitchStatement() {
    }
    
    public Expression getExpression() {
        return (Expression)this.elementAt(0);
    }
    
    public void setExpression(final Expression p) {
        this.setElementAt(p, 0);
    }
    
    public CaseGroupList getCaseGroupList() {
        return (CaseGroupList)this.elementAt(1);
    }
    
    public void setCaseGroupList(final CaseGroupList p) {
        this.setElementAt(p, 1);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
