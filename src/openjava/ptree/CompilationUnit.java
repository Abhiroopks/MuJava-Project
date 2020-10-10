// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class CompilationUnit extends NonLeaf
{
    public CompilationUnit(final String p2, String[] p3, ClassDeclarationList p4) {
        if (p3 == null) {
            p3 = new String[0];
        }
        if (p4 == null) {
            p4 = new ClassDeclarationList();
        }
        this.set(p2, p3, p4);
    }
    
    public void setPackage(final String p) {
        this.setElementAt(p, 0);
    }
    
    public String getPackage() {
        return (String)this.elementAt(0);
    }
    
    public void setDeclaredImports(final String[] p) {
        this.setElementAt(p, 1);
    }
    
    public String[] getDeclaredImports() {
        return (String[])this.elementAt(1);
    }
    
    public void setClassDeclarations(final ClassDeclarationList p) {
        this.setElementAt(p, 2);
    }
    
    public ClassDeclarationList getClassDeclarations() {
        return (ClassDeclarationList)this.elementAt(2);
    }
    
    public ClassDeclaration getPublicClass() throws ParseTreeException {
        ClassDeclaration classDeclaration = null;
        final ClassDeclarationList classDeclarations = this.getClassDeclarations();
        for (int i = 0; i < classDeclarations.size(); ++i) {
            final ClassDeclaration value = classDeclarations.get(i);
            if (value.getModifiers().contains(1)) {
                if (classDeclaration != null) {
                    throw new ParseTreeException("getPublicClass() in CompileationUnit : multiple public class");
                }
                classDeclaration = value;
            }
        }
        return classDeclaration;
    }
    
    public static boolean isOnDemandImport(final String s) {
        return s.endsWith(".*");
    }
    
    public static String trimOnDemand(final String s) {
        if (isOnDemandImport(s)) {
            return s.substring(0, s.length() - 2);
        }
        return s;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
