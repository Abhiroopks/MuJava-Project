// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import openjava.mop.Environment;
import openjava.mop.OJClass;

public class ArrayAllocationExpression extends NonLeaf implements Expression
{
    public ArrayAllocationExpression(final TypeName typeName, final ExpressionList list) {
        this(typeName, list, null);
    }
    
    public ArrayAllocationExpression(final TypeName p3, ExpressionList p4, final ArrayInitializer p5) {
        if (p4 == null) {
            p4 = new ExpressionList();
        }
        this.set(p3, p4, p5);
    }
    
    public ArrayAllocationExpression(final OJClass ojClass, final ExpressionList list) {
        this(TypeName.forOJClass(ojClass), list);
    }
    
    public ArrayAllocationExpression(final OJClass ojClass, final ExpressionList list, final ArrayInitializer arrayInitializer) {
        this(TypeName.forOJClass(ojClass), list, arrayInitializer);
    }
    
    ArrayAllocationExpression() {
    }
    
    public TypeName getTypeName() {
        return (TypeName)this.elementAt(0);
    }
    
    public void setTypeName(final TypeName p) {
        this.setElementAt(p, 0);
    }
    
    public ExpressionList getDimExprList() {
        return (ExpressionList)this.elementAt(1);
    }
    
    public void setDimExprList(final ExpressionList p) {
        this.setElementAt(p, 1);
    }
    
    public ArrayInitializer getInitializer() {
        return (ArrayInitializer)this.elementAt(2);
    }
    
    public void setInitializer(final ArrayInitializer p) {
        this.setElementAt(p, 2);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        return environment.lookupClass(environment.toQualifiedName(this.getTypeName() + TypeName.stringFromDimension(this.getDimExprList().size())));
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
