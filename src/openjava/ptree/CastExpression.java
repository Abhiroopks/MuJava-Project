// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.mop.Environment;
import openjava.ptree.util.ParseTreeVisitor;
import openjava.mop.OJClass;

public class CastExpression extends NonLeaf implements Expression
{
    public CastExpression(final TypeName p2, final Expression p3) {
        this.set(p2, p3);
    }
    
    public CastExpression(final OJClass ojClass, final Expression expression) {
        this(TypeName.forOJClass(ojClass), expression);
    }
    
    CastExpression() {
    }
    
    public TypeName getTypeSpecifier() {
        return (TypeName)this.elementAt(0);
    }
    
    public void setTypeSpecifier(final TypeName p) {
        this.setElementAt(p, 0);
    }
    
    public Expression getExpression() {
        return (Expression)this.elementAt(1);
    }
    
    public void setExpression(final Expression p) {
        this.setElementAt(p, 1);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        return environment.lookupClass(environment.toQualifiedName(this.getTypeSpecifier().toString()));
    }
}
