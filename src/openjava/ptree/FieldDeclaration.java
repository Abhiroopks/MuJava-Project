// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import java.util.Hashtable;

public class FieldDeclaration extends NonLeaf implements MemberDeclaration
{
    private Hashtable suffixes;
    
    public FieldDeclaration(final ModifierList p3, final TypeName p4, final VariableDeclarator p5) {
        this.suffixes = null;
        this.set(p3, p4, p5);
    }
    
    public FieldDeclaration(final ModifierList p4, final TypeName p5, final String s, final VariableInitializer variableInitializer) {
        this.suffixes = null;
        this.set(p4, p5, new VariableDeclarator(s, variableInitializer));
    }
    
    FieldDeclaration() {
        this.suffixes = null;
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
    
    public String getName() {
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
