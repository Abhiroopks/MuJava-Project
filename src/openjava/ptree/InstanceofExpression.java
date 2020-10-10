// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import openjava.mop.OJClass;
import openjava.mop.Environment;

public class InstanceofExpression extends NonLeaf implements Expression
{
    public InstanceofExpression(final Expression p2, final TypeName p3) {
        this.set(p2, p3);
    }
    
    InstanceofExpression() {
    }
    
    private final boolean needsLeftPar(final Expression expression) {
        return expression instanceof AssignmentExpression || expression instanceof ConditionalExpression || expression instanceof BinaryExpression;
    }
    
    public Expression getExpression() {
        return (Expression)this.elementAt(0);
    }
    
    public void setLeft(final Expression p) {
        this.setElementAt(p, 0);
    }
    
    public TypeName getTypeSpecifier() {
        return (TypeName)this.elementAt(1);
    }
    
    public void setTypeSpecifier(final TypeName p) {
        this.setElementAt(p, 1);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        return OJClass.forClass(Boolean.TYPE);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
