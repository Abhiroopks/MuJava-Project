// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class ClassDeclarationList extends List
{
    private static final String LNLN;
    
    public ClassDeclarationList() {
        super(ClassDeclarationList.LNLN);
    }
    
    public ClassDeclarationList(final ClassDeclaration classDeclaration) {
        super(ClassDeclarationList.LNLN, classDeclaration);
    }
    
    public ClassDeclaration get(final int n) {
        return (ClassDeclaration)this.contents_elementAt(n);
    }
    
    public void add(final ClassDeclaration classDeclaration) {
        this.contents_addElement(classDeclaration);
    }
    
    public void set(final int n, final ClassDeclaration classDeclaration) {
        this.contents_setElementAt(classDeclaration, n);
    }
    
    public ClassDeclaration remove(final int n) {
        final ClassDeclaration classDeclaration = (ClassDeclaration)this.contents_elementAt(n);
        this.contents_removeElementAt(n);
        return classDeclaration;
    }
    
    public void insertElementAt(final ClassDeclaration classDeclaration, final int n) {
        this.contents_insertElementAt(classDeclaration, n);
    }
    
    public void addAll(final ClassDeclarationList list) {
        for (int i = 0; i < list.size(); ++i) {
            this.contents_addElement(list.get(i));
        }
    }
    
    public ClassDeclarationList subList(final int n, final int n2) {
        final ClassDeclarationList list = new ClassDeclarationList();
        for (int i = n; i < n2; ++i) {
            list.add(this.get(i));
        }
        return list;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    static {
        LNLN = ParseTreeObject.LN + ParseTreeObject.LN;
    }
}
