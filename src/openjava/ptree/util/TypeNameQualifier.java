// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree.util;

import openjava.ptree.ParseTree;
import openjava.ptree.ClassLiteral;
import openjava.ptree.MethodDeclaration;
import openjava.ptree.ConstructorDeclaration;
import openjava.ptree.ParseTreeException;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.TypeName;
import openjava.mop.Environment;

public class TypeNameQualifier extends ScopeHandler
{
    private String newName;
    
    public TypeNameQualifier(final Environment environment) {
        this(environment, null);
    }
    
    public TypeNameQualifier(final Environment environment, final String newName) {
        super(environment);
        this.newName = null;
        this.newName = newName;
    }
    
    private String qualify(final String s) {
        return this.getEnvironment().toQualifiedName(s);
    }
    
    private TypeName qualifyName(final TypeName typeName) {
        return new TypeName(this.qualify(typeName.getName()), typeName.getDimension());
    }
    
    private TypeName[] qualifyNames(final TypeName[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.qualifyName(array[i]);
        }
        return array;
    }
    
    @Override
    public void visit(final ClassDeclaration classDeclaration) throws ParseTreeException {
        if (classDeclaration.getBaseclasses() != null) {
            classDeclaration.setBaseclasses(this.qualifyNames(classDeclaration.getBaseclasses()));
        }
        if (classDeclaration.getInterfaces() != null) {
            classDeclaration.setInterfaces(this.qualifyNames(classDeclaration.getInterfaces()));
        }
        super.visit(classDeclaration);
    }
    
    @Override
    public void visit(final ConstructorDeclaration constructorDeclaration) throws ParseTreeException {
        if (this.newName != null) {
            constructorDeclaration.setName(this.newName);
        }
        constructorDeclaration.setThrows(this.qualifyNames(constructorDeclaration.getThrows()));
        super.visit(constructorDeclaration);
    }
    
    @Override
    public void visit(final MethodDeclaration methodDeclaration) throws ParseTreeException {
        methodDeclaration.setThrows(this.qualifyNames(methodDeclaration.getThrows()));
        super.visit(methodDeclaration);
    }
    
    @Override
    public void visit(final TypeName typeName) throws ParseTreeException {
        typeName.setName(this.qualify(typeName.getName()));
        super.visit(typeName);
    }
    
    @Override
    public void visit(final ClassLiteral classLiteral) throws ParseTreeException {
        classLiteral.replace(new ClassLiteral(this.qualifyName(classLiteral.getTypeName())));
    }
}
