// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import java.util.Hashtable;

public class ConstructorDeclaration extends NonLeaf implements MemberDeclaration
{
    private Hashtable suffixes;
    
    public ConstructorDeclaration(ModifierList p6, final String p7, ParameterList p8, TypeName[] p9, final ConstructorInvocation p10, final StatementList p11) {
        this.suffixes = null;
        if (p6 == null) {
            p6 = new ModifierList();
        }
        if (p8 == null) {
            p8 = new ParameterList();
        }
        if (p9 == null) {
            p9 = new TypeName[0];
        }
        this.set(p6, p7, p8, p9, p10, p11);
    }
    
    public ConstructorDeclaration(final ModifierList list, final String s, final ParameterList list2, final TypeName[] array, final StatementList list3) {
        this(list, s, list2, array, null, list3);
    }
    
    ConstructorDeclaration() {
        this.suffixes = null;
    }
    
    public ModifierList getModifiers() {
        return (ModifierList)this.elementAt(0);
    }
    
    public void setModifiers(ModifierList p) {
        if (p == null) {
            p = new ModifierList();
        }
        this.setElementAt(p, 0);
    }
    
    public String getName() {
        return (String)this.elementAt(1);
    }
    
    public void setName(final String p) {
        this.setElementAt(p, 1);
    }
    
    public ParameterList getParameters() {
        return (ParameterList)this.elementAt(2);
    }
    
    public void setParameters(ParameterList p) {
        if (p == null) {
            p = new ParameterList();
        }
        this.setElementAt(p, 2);
    }
    
    public TypeName[] getThrows() {
        return (TypeName[])this.elementAt(3);
    }
    
    public void setThrows(TypeName[] p) {
        if (p == null) {
            p = new TypeName[0];
        }
        this.setElementAt(p, 3);
    }
    
    public ConstructorInvocation getConstructorInvocation() {
        return (ConstructorInvocation)this.elementAt(4);
    }
    
    public void setConstructorInvocation(final ConstructorInvocation p) {
        this.setElementAt(p, 4);
    }
    
    public StatementList getBody() {
        return (StatementList)this.elementAt(5);
    }
    
    public void setBody(final StatementList p) {
        this.setElementAt(p, 5);
    }
    
    public void setSuffixes(final Hashtable suffixes) {
        this.suffixes = suffixes;
    }
    
    public Hashtable getSuffixes() {
        return this.suffixes;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
