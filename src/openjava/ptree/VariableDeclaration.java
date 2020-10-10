// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class VariableDeclaration extends NonLeaf implements Statement
{
    public VariableDeclaration(ModifierList p3, final TypeName p4, final VariableDeclarator p5) {
        if (p3 == null) {
            p3 = new ModifierList();
        }
        this.set(p3, p4, p5);
    }
    
    public VariableDeclaration(final TypeName typeName, final VariableDeclarator variableDeclarator) {
        this(new ModifierList(), typeName, variableDeclarator);
    }
    
    public VariableDeclaration(final ModifierList list, final TypeName typeName, final String s, final VariableInitializer variableInitializer) {
        this(list, typeName, new VariableDeclarator(s, variableInitializer));
    }
    
    public VariableDeclaration(final TypeName typeName, final String s, final VariableInitializer variableInitializer) {
        this(new ModifierList(), typeName, s, variableInitializer);
    }
    
    VariableDeclaration() {
    }
    
    public ModifierList getModifiers() {
        return (ModifierList)this.elementAt(0);
    }
    
    public void setModifiers(final ModifierList p) {
        this.setElementAt(p, 0);
    }
    
    public TypeName getTypeSpecifier() {
        final TypeName typeName = (TypeName)this.elementAt(1);
        final VariableDeclarator variableDeclarator = (VariableDeclarator)this.elementAt(2);
        final TypeName typeName2 = (TypeName)typeName.makeCopy();
        typeName2.addDimension(variableDeclarator.getDimension());
        return typeName2;
    }
    
    public void setTypeSpecifier(final TypeName p) {
        this.setElementAt(p, 1);
    }
    
    public VariableDeclarator getVariableDeclarator() {
        return (VariableDeclarator)this.elementAt(2);
    }
    
    public void setVariableDeclarator(final VariableDeclarator p) {
        this.setElementAt(p, 2);
    }
    
    public String getVariable() {
        return this.getVariableDeclarator().getVariable();
    }
    
    public void setVariable(final String variable) {
        this.getVariableDeclarator().setVariable(variable);
    }
    
    public VariableInitializer getInitializer() {
        return this.getVariableDeclarator().getInitializer();
    }
    
    public void setInitializer(final VariableInitializer initializer) {
        this.getVariableDeclarator().setInitializer(initializer);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
