// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import java.util.Hashtable;

public class MethodDeclaration extends NonLeaf implements MemberDeclaration
{
    private Hashtable suffixes;
    private String genericsTypeParameters;
    
    public MethodDeclaration(final ModifierList p7, final TypeName p8, final String p9, ParameterList p10, TypeName[] p11, final StatementList p12, final TypeParameterList p13) {
        this.suffixes = null;
        this.genericsTypeParameters = "";
        if (p10 == null) {
            p10 = new ParameterList();
        }
        if (p11 == null) {
            p11 = new TypeName[0];
        }
        this.set(p7, p13, p8, p9, p10, p11, p12);
        if (p13 != null) {
            this.genericsTypeParameters = p13.toString();
        }
    }
    
    MethodDeclaration() {
        this.suffixes = null;
        this.genericsTypeParameters = "";
    }
    
    public ModifierList getModifiers() {
        return (ModifierList)this.elementAt(0);
    }
    
    public void setModifiers(final ModifierList p) {
        this.setElementAt(p, 0);
    }
    
    public TypeName getReturnType() {
        return (TypeName)this.elementAt(2);
    }
    
    public void setReturnType(final TypeName p) {
        this.setElementAt(p, 2);
    }
    
    public String getName() {
        return (String)this.elementAt(3);
    }
    
    public void setName(final String p) {
        this.setElementAt(p, 3);
    }
    
    public ParameterList getParameters() {
        return (ParameterList)this.elementAt(4);
    }
    
    public void setParameters(final ParameterList p) {
        this.setElementAt(p, 4);
    }
    
    public TypeName[] getThrows() {
        return (TypeName[])this.elementAt(5);
    }
    
    public void setThrows(final TypeName[] p) {
        this.setElementAt(p, 5);
    }
    
    public StatementList getBody() {
        return (StatementList)this.elementAt(6);
    }
    
    public void setBody(final StatementList p) {
        this.setElementAt(p, 6);
    }
    
    public TypeParameterList getTypeParameterList() {
        return (TypeParameterList)this.elementAt(1);
    }
    
    public void setTypeParameterList(final TypeParameterList p) {
        this.setElementAt(p, 1);
    }
    
    public void setSuffixes(final Hashtable suffixes) {
        this.suffixes = suffixes;
    }
    
    public String getGenericsTypeParameters() {
        return this.genericsTypeParameters;
    }
    
    public void setGenericsTypeParameters(final String genericsTypeParameters) {
        this.genericsTypeParameters = genericsTypeParameters;
    }
    
    public Hashtable getSuffixes() {
        return this.suffixes;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
