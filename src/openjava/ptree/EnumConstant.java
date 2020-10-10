// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class EnumConstant extends NonLeaf implements ParseTree
{
    EnumConstant() {
    }
    
    public EnumConstant(final ModifierList p5, final String p6, final ExpressionList p7, final MemberDeclarationList p8, final String p9) {
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
    
    public void setName(final String p) {
        this.setElementAt(p, 1);
    }
    
    public ExpressionList getArguments() {
        return (ExpressionList)this.elementAt(2);
    }
    
    public MemberDeclarationList getClassBody() {
        return (MemberDeclarationList)this.elementAt(3);
    }
    
    public String getEnumType() {
        return (String)this.elementAt(4);
    }
    
    public void setEnumType(final String p) {
        this.setElementAt(p, 4);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
