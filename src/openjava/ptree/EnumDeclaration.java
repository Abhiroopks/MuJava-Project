// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class EnumDeclaration extends NonLeaf implements MemberDeclaration
{
    EnumDeclaration() {
    }
    
    public EnumDeclaration(final ModifierList p5, final String p6, final TypeName[] p7, final EnumConstantList p8, final MemberDeclarationList p9) {
        this.set(p5, p6, p7, p8, p9);
    }
    
    public ModifierList getModifiers() {
        return (ModifierList)this.elementAt(0);
    }
    
    public void setModifiers(final ModifierList p) {
        this.setElementAt(p, 0);
    }
    
    public String getName() {
        return (String)this.elementAt(1);
    }
    
    public TypeName[] getImplementsList() {
        return (TypeName[])this.elementAt(2);
    }
    
    public EnumConstantList getEnumConstantList() {
        return (EnumConstantList)this.elementAt(3);
    }
    
    public MemberDeclarationList getClassBodayDeclaration() {
        return (MemberDeclarationList)this.elementAt(4);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
