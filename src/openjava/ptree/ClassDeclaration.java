// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import java.util.Hashtable;

public class ClassDeclaration extends NonLeaf implements Statement, MemberDeclaration, ParseTree
{
    private String[] metaclazz;
    private Hashtable suffixes;
    private boolean _isInterface;
    private boolean _isEnum;
    
    public ClassDeclaration(final ModifierList list, final String s, final TypeParameterList list2, final TypeName[] array, final TypeName[] array2, final MemberDeclarationList list3) {
        this(list, s, list2, array, array2, list3, true);
    }
    
    public ClassDeclaration(final ModifierList p7, final String p8, final TypeParameterList p9, TypeName[] p10, TypeName[] p11, final MemberDeclarationList p12, final boolean b) {
        this.metaclazz = null;
        this.suffixes = null;
        this._isInterface = false;
        this._isEnum = false;
        p10 = ((p10 == null) ? new TypeName[0] : p10);
        p11 = ((p11 == null) ? new TypeName[0] : p11);
        this.set(p7, p8, p9, p10, p11, p12);
        this._isInterface = !b;
    }
    
    public ClassDeclaration(final MemberDeclaration memberDeclaration) {
        this.metaclazz = null;
        this.suffixes = null;
        this._isInterface = false;
        this._isEnum = false;
        this.set_isEnum(true);
        this.set(((EnumDeclaration)memberDeclaration).getModifiers(), ((EnumDeclaration)memberDeclaration).getName(), null, new TypeName[0], (((EnumDeclaration)memberDeclaration).getImplementsList() == null) ? new TypeName[0] : ((EnumDeclaration)memberDeclaration).getImplementsList(), ((EnumDeclaration)memberDeclaration).getClassBodayDeclaration(), false, ((EnumDeclaration)memberDeclaration).getEnumConstantList());
    }
    
    public boolean isInterface() {
        return this._isInterface;
    }
    
    public void beInterface(final boolean isInterface) {
        this._isInterface = isInterface;
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
    
    public TypeParameterList getTypeParameters() {
        return (TypeParameterList)this.elementAt(2);
    }
    
    public void setTypeParameters(final TypeParameterList p) {
        this.setElementAt(p, 2);
    }
    
    public TypeName[] getBaseclasses() {
        return (TypeName[])this.elementAt(3);
    }
    
    public TypeName getBaseclass() {
        final TypeName[] baseclasses = this.getBaseclasses();
        if (baseclasses.length == 0) {
            return null;
        }
        return baseclasses[0];
    }
    
    public void setBaseclasses(final TypeName[] p) {
        this.setElementAt(p, 3);
    }
    
    public void setBaseclass(final TypeName typeName) {
        this.setElementAt(new TypeName[] { typeName }, 3);
    }
    
    public TypeName[] getInterfaces() {
        return (TypeName[])this.elementAt(4);
    }
    
    public void setInterfaces(final TypeName[] p) {
        this.setElementAt(p, 4);
    }
    
    public MemberDeclarationList getBody() {
        return (MemberDeclarationList)this.elementAt(5);
    }
    
    public void setBody(final MemberDeclarationList p) {
        this.setElementAt(p, 5);
    }
    
    public EnumConstantList getEnumConstants() {
        return (EnumConstantList)this.elementAt(7);
    }
    
    public void setEnumConstants(final EnumConstantList p) {
        this.setElementAt(p, 7);
    }
    
    public void setSuffixes(final Hashtable suffixes) {
        this.suffixes = suffixes;
    }
    
    public Hashtable getSuffixes() {
        return this.suffixes;
    }
    
    public void setMetaclass(final String s) {
        this.metaclazz = new String[] { s };
    }
    
    public void setMetaclass(final String[] metaclazz) {
        this.metaclazz = metaclazz;
    }
    
    public String getMetaclass() {
        if (this.metaclazz == null || this.metaclazz.length == 0) {
            return null;
        }
        return this.metaclazz[0];
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    public void set_isEnum(final boolean isEnum) {
        this._isEnum = isEnum;
    }
    
    public boolean isEnumeration() {
        return this._isEnum;
    }
}
