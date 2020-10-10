// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.FieldDeclaration;
import openjava.ptree.MemberDeclarationList;

public class AnonymousClassEnvironment extends ClassEnvironment
{
    private MemberDeclarationList members;
    private String base;
    
    public AnonymousClassEnvironment(final Environment environment, final String base, final MemberDeclarationList members) {
        super(environment);
        this.members = members;
        this.base = base;
    }
    
    @Override
    public String getClassName() {
        return "<anonymous class>";
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public OJClass lookupBind(final String s) {
        for (int i = 0; i < this.members.size(); ++i) {
            if (this.members.get(i) instanceof FieldDeclaration) {
                final FieldDeclaration fieldDeclaration = (FieldDeclaration)this.members.get(i);
                if (s.equals(fieldDeclaration.getName())) {
                    return this.lookupClass(fieldDeclaration.getName());
                }
            }
        }
        final OJField pickupField = ClassEnvironment.pickupField(this.lookupClass(this.base), s);
        if (pickupField != null) {
            return pickupField.getType();
        }
        return this.parent.lookupBind(s);
    }
    
    public boolean isField(final String s) {
        for (int i = 0; i < this.members.size(); ++i) {
            if (this.members.get(i) instanceof FieldDeclaration) {
                if (s.equals(((FieldDeclaration)this.members.get(i)).getName())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public OJClass returnFieldType(final String s) {
        final String s2 = "";
        for (int i = 0; i < this.members.size(); ++i) {
            if (this.members.get(i) instanceof FieldDeclaration) {
                final FieldDeclaration fieldDeclaration = (FieldDeclaration)this.members.get(i);
                if (s.equals(fieldDeclaration.getName())) {
                    return this.lookupClass(fieldDeclaration.getTypeSpecifier().toString());
                }
            }
        }
        return this.parent.lookupClass(s2);
    }
    
    @Override
    public String currentClassName() {
        return this.getClassName();
    }
    
    @Override
    public Environment getParentEnvironment() {
        return this.parent;
    }
}
