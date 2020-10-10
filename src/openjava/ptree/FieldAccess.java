// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.mop.NoSuchMemberException;
import openjava.mop.OJField;
import openjava.mop.FileEnvironment;
import openjava.mop.AnonymousClassEnvironment;
import openjava.mop.Environment;
import openjava.ptree.util.ParseTreeVisitor;
import openjava.mop.OJClass;

public class FieldAccess extends NonLeaf implements Expression
{
    public FieldAccess(final Expression p2, final String p3) {
        this.set(p2, p3);
    }
    
    public FieldAccess(final TypeName p2, final String p3) {
        this.set(p2, p3);
    }
    
    public FieldAccess(final OJClass ojClass, final String s) {
        this(TypeName.forOJClass(ojClass), s);
    }
    
    public FieldAccess(final String s) {
        this((Expression)null, s);
    }
    
    FieldAccess() {
    }
    
    public ParseTree getReference() {
        return (ParseTree)this.elementAt(0);
    }
    
    public boolean isTypeReference() {
        return this.getReference() instanceof TypeName;
    }
    
    public Expression getReferenceExpr() {
        if (this.isTypeReference()) {
            return null;
        }
        return (Expression)this.getReference();
    }
    
    public void setReferenceExpr(final Expression p) {
        this.setElementAt(p, 0);
    }
    
    public TypeName getReferenceType() {
        if (!this.isTypeReference()) {
            return null;
        }
        return (TypeName)this.getReference();
    }
    
    public void setReferenceType(final TypeName p) {
        this.setElementAt(p, 0);
    }
    
    public String getName() {
        return (String)this.elementAt(1);
    }
    
    public void setName(final String p) {
        this.setElementAt(p, 1);
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
        Environment environment2 = environment;
        if (environment2 instanceof AnonymousClassEnvironment) {
            final OJClass returnFieldType = ((AnonymousClassEnvironment)environment2).returnFieldType(name);
            if (returnFieldType != null) {
                return returnFieldType;
            }
        }
        do {
            final Environment parentEnvironment = environment2.getParentEnvironment();
            if (parentEnvironment instanceof AnonymousClassEnvironment) {
                final OJClass returnFieldType2 = ((AnonymousClassEnvironment)parentEnvironment).returnFieldType(name);
                if (returnFieldType2 != null) {
                    return returnFieldType2;
                }
            }
            environment2 = parentEnvironment;
        } while (!(environment2 instanceof FileEnvironment));
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
        if (ojClass != null) {
            final OJField pickupField = pickupField(ojClass, name);
            if (pickupField != null) {
                return pickupField.getType();
            }
        }
        if (ojClass == null) {
            for (OJClass declaringClass = lookupClass; declaringClass != null; declaringClass = declaringClass.getDeclaringClass()) {
                final OJField pickupField2 = pickupField(declaringClass, name);
                if (pickupField2 != null) {
                    return pickupField2.getType();
                }
                final OJClass[] declaredClasses = declaringClass.getDeclaredClasses();
                for (int i = 0; i < declaredClasses.length; ++i) {
                    final OJField pickupField3 = pickupField(declaredClasses[i], name);
                    if (pickupField3 != null) {
                        return pickupField3.getType();
                    }
                }
            }
            ojClass = lookupClass;
        }
        if (this.getReferenceType() != null && this.getName() != null) {
            final OJField ojField = new OJField(environment, ojClass, this);
            System.out.println("FieldAccess getType field: " + (ojField != null) + "; " + ojField.getType());
            if (ojField != null) {
                return ojField.getType();
            }
        }
        return ojClass.resolveException(new NoSuchMemberException(name), name).getType();
    }
    
    private static OJField pickupField(final OJClass ojClass, final String s) {
        try {
            return ojClass.getField(s, ojClass);
        }
        catch (NoSuchMemberException ex) {
            return null;
        }
    }
}
