// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.mop.OJClass;
import openjava.mop.Environment;
import openjava.ptree.util.ParseTreeVisitor;

public class ConditionalExpression extends NonLeaf implements Expression
{
    public ConditionalExpression(final Expression p3, final Expression p4, final Expression p5) {
        this.set(p3, p4, p5);
    }
    
    ConditionalExpression() {
    }
    
    public Expression getCondition() {
        return (Expression)this.elementAt(0);
    }
    
    public void setCondition(final Expression p) {
        this.setElementAt(p, 0);
    }
    
    public Expression getTrueCase() {
        return (Expression)this.elementAt(1);
    }
    
    public void setTrueCase(final Expression p) {
        this.setElementAt(p, 1);
    }
    
    public Expression getFalseCase() {
        return (Expression)this.elementAt(2);
    }
    
    public void setFalseCase(final Expression p) {
        this.setElementAt(p, 2);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        return BinaryExpression.chooseType(this.getTrueCase().getType(environment), this.getFalseCase().getType(environment));
    }
}
