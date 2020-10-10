// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class CaseLabel extends NonLeaf
{
    public CaseLabel(final Expression p) {
        this.set(p);
    }
    
    CaseLabel() {
    }
    
    public Expression getExpression() {
        return (Expression)this.elementAt(0);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
