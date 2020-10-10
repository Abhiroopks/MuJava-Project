// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import openjava.mop.Environment;
import openjava.mop.OJClass;

public class AllocationExpression extends NonLeaf implements Expression
{
    public AllocationExpression(final Expression p4, final TypeName p5, ExpressionList p6, final MemberDeclarationList p7) {
        if (p6 == null) {
            p6 = new ExpressionList();
        }
        this.set(p5, p6, p7, p4);
    }
    
    public AllocationExpression(final TypeName typeName, final ExpressionList list, final MemberDeclarationList list2) {
        this(null, typeName, list, list2);
    }
    
    public AllocationExpression(final Expression expression, final TypeName typeName, final ExpressionList list) {
        this(expression, typeName, list, null);
    }
    
    public AllocationExpression(final TypeName typeName, final ExpressionList list) {
        this(typeName, list, null);
    }
    
    public AllocationExpression(final OJClass ojClass, final ExpressionList list) {
        this(TypeName.forOJClass(ojClass), list);
    }
    
    AllocationExpression() {
    }
    
    public Expression getEncloser() {
        return (Expression)this.elementAt(3);
    }
    
    public void setEncloser(final Expression p) {
        this.setElementAt(p, 3);
    }
    
    public TypeName getClassType() {
        return (TypeName)this.elementAt(0);
    }
    
    public void setClassType(final TypeName p) {
        this.setElementAt(p, 0);
    }
    
    public ExpressionList getArguments() {
        return (ExpressionList)this.elementAt(1);
    }
    
    public void setArguments(ExpressionList p) {
        if (p == null) {
            p = new ExpressionList();
        }
        this.setElementAt(p, 1);
    }
    
    public MemberDeclarationList getClassBody() {
        return (MemberDeclarationList)this.elementAt(2);
    }
    
    public void setClassBody(final MemberDeclarationList p) {
        this.setElementAt(p, 2);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        return environment.lookupClass(environment.toQualifiedName(this.getClassType().toString()));
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
