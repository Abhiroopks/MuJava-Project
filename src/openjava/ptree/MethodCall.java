// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.mop.NoSuchMemberException;
import openjava.mop.Signature;
import openjava.mop.OJMethod;
import openjava.mop.Environment;
import openjava.ptree.util.ParseTreeVisitor;
import openjava.mop.OJClass;

public class MethodCall extends NonLeaf implements Expression
{
    public MethodCall(final Expression p3, final String p4, ExpressionList p5) {
        if (p5 == null) {
            p5 = new ExpressionList();
        }
        this.set(p3, null, p4, p5);
    }
    
    public MethodCall(final String s, final ExpressionList list) {
        this((Expression)null, s, list);
    }
    
    public MethodCall(final TypeName p3, final String p4, ExpressionList p5) {
        if (p5 == null) {
            p5 = new ExpressionList();
        }
        this.set(null, p3, p4, p5);
    }
    
    public MethodCall(final OJClass ojClass, final String s, final ExpressionList list) {
        this(TypeName.forOJClass(ojClass), s, list);
    }
    
    MethodCall() {
    }
    
    public Expression getReferenceExpr() {
        return (Expression)this.elementAt(0);
    }
    
    public void setReferenceExpr(final Expression p) {
        this.setElementAt(p, 0);
        this.setElementAt(null, 1);
    }
    
    public TypeName getReferenceType() {
        return (TypeName)this.elementAt(1);
    }
    
    public void setReferenceType(final TypeName p) {
        this.setElementAt(null, 0);
        this.setElementAt(p, 1);
    }
    
    public String getName() {
        return (String)this.elementAt(2);
    }
    
    public void setName(final String p) {
        this.setElementAt(p, 2);
    }
    
    public ExpressionList getArguments() {
        return (ExpressionList)this.elementAt(3);
    }
    
    public void setArguments(ExpressionList p) {
        if (p == null) {
            p = new ExpressionList();
        }
        this.setElementAt(p, 3);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        final OJClass lookupClass = environment.lookupClass(environment.currentClassName());
        final Expression referenceExpr = this.getReferenceExpr();
        final String name = this.getName();
        OJClass ojClass = null;
        if (referenceExpr != null) {
            ojClass = referenceExpr.getType(environment);
        }
        else {
            final TypeName referenceType = this.getReferenceType();
            if (referenceType != null) {
                ojClass = environment.lookupClass(environment.toQualifiedName(referenceType.toString()));
            }
        }
        final ExpressionList arguments = this.getArguments();
        final OJClass[] array = new OJClass[arguments.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = arguments.get(i).getType(environment);
        }
        OJMethod pickupMethod = null;
        if (ojClass != null) {
            pickupMethod = pickupMethod(ojClass, name, array);
        }
        if (pickupMethod != null) {
            return pickupMethod.getReturnType();
        }
        if (ojClass == null) {
            for (OJClass declaringClass = lookupClass; declaringClass != null; declaringClass = declaringClass.getDeclaringClass()) {
                final OJMethod pickupMethod2 = pickupMethod(declaringClass, name, array);
                if (pickupMethod2 != null) {
                    return pickupMethod2.getReturnType();
                }
                final OJClass[] declaredClasses = declaringClass.getDeclaredClasses();
                for (int j = 0; j < declaredClasses.length; ++j) {
                    final OJMethod pickupMethod3 = pickupMethod(declaredClasses[j], name, array);
                    if (pickupMethod3 != null) {
                        return pickupMethod3.getReturnType();
                    }
                }
            }
            ojClass = lookupClass;
        }
        //System.out.println("MethodCall method: " + this.getReferenceType() + "; " + this.getName());
        if (this.getReferenceType() != null && this.getName() != null) {
            final OJMethod ojMethod = new OJMethod(environment, ojClass, this);
            //System.out.println("MethodCall getType method: " + (ojMethod != null) + "; " + ojMethod.getReturnType());
            if (ojMethod != null) {
                return ojMethod.getReturnType();
            }
        }
        return ojClass.resolveException(new NoSuchMemberException(new Signature(name, array).toString()), name, array).getReturnType();
    }
    
    private static OJMethod pickupMethod(final OJClass ojClass, final String s, final OJClass[] array) {
        try {
            return ojClass.getAcceptableMethod(s, array, ojClass);
        }
        catch (NoSuchMemberException ex) {
            return null;
        }
    }
}
