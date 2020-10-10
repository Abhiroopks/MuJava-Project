// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree.util;

import openjava.mop.OJClassNotFoundException;
import openjava.tools.DebugOut;
import openjava.mop.OJClass;
import openjava.ptree.TypeParameter;
import openjava.ptree.Parameter;
import openjava.ptree.VariableDeclarator;
import openjava.ptree.TypeName;
import openjava.mop.Toolbox;
import openjava.ptree.ForStatement;
import openjava.ptree.ParseTreeException;
import openjava.ptree.Statement;
import openjava.ptree.VariableDeclaration;
import openjava.mop.Environment;

public class VariableBinder extends ScopeHandler
{
    public VariableBinder(final Environment environment) {
        super(environment);
    }
    
    @Override
    public Statement evaluateDown(final VariableDeclaration variableDeclaration) throws ParseTreeException {
        super.evaluateDown(variableDeclaration);
        bindLocalVariable(variableDeclaration, this.getEnvironment());
        return variableDeclaration;
    }
    
    @Override
    public Statement evaluateDown(final ForStatement forStatement) throws ParseTreeException {
        super.evaluateDown(forStatement);
        final TypeName initDeclType = forStatement.getInitDeclType();
        if (initDeclType == null) {
            return forStatement;
        }
        final VariableDeclarator[] initDecls = forStatement.getInitDecls();
        if (initDecls != null) {
            bindForInit(initDeclType, initDecls, this.getEnvironment());
        }
        else {
            bindName(this.getEnvironment(), Toolbox.nameToJavaClassName(initDeclType.toString()), forStatement.getIdentifier());
        }
        return forStatement;
    }
    
    @Override
    public Parameter evaluateDown(final Parameter parameter) throws ParseTreeException {
        super.evaluateDown(parameter);
        bindParameter(parameter, this.getEnvironment());
        return parameter;
    }
    
    private static void bindLocalVariable(final VariableDeclaration variableDeclaration, final Environment environment) {
        bindName(environment, variableDeclaration.getTypeSpecifier().toString(), variableDeclaration.getVariable());
    }
    
    private static void bindForInit(final TypeName typeName, final VariableDeclarator[] array, final Environment environment) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                bindName(environment, typeName.toString() + array[i].dimensionString(), array[i].getVariable());
            }
        }
    }
    
    private static void bindParameter(final Parameter parameter, final Environment environment) {
        final String variable = parameter.getVariable();
        String s;
        if (!parameter.isVarargs()) {
            s = parameter.getTypeSpecifier().toString();
        }
        else {
            s = parameter.getTypeSpecifier().toString() + "[]";
        }
        bindName(environment, s, variable);
    }
    
    @Override
    public TypeParameter evaluateDown(final TypeParameter typeParameter) throws ParseTreeException {
        super.evaluateDown(typeParameter);
        final String name = typeParameter.getName();
        if (typeParameter.getTypeBound() != "") {
            final String[] split = typeParameter.getTypeBound().split("&");
            for (int length = split.length, i = 0; i < length; ++i) {
                record(this.getEnvironment(), split[i], name);
            }
        }
        else {
            this.getEnvironment().recordGenerics(name, OJClass.forClass(Object.class));
        }
        return typeParameter;
    }
    
    private static void record(final Environment x, final String s, final String str) {
        final String qualifiedName = x.toQualifiedName(s);
        try {
            OJClass ojClass = x.lookupClass(qualifiedName);
            if (ojClass == null) {
                ojClass = OJClass.forName(qualifiedName);
            }
            x.record(str, ojClass);
            DebugOut.println("record\t" + str + "\t: " + qualifiedName);
        }
        catch (OJClassNotFoundException ex) {
            System.err.println("VariableBinder.record() " + ex.toString() + " : " + qualifiedName);
            System.err.println(x);
        }
    }
    
    private static void bindName(final Environment x, final String s, final String str) {
        final String qualifiedName = x.toQualifiedName(s);
        try {
            OJClass ojClass = x.lookupClass(qualifiedName);
            if (ojClass == null) {
                ojClass = OJClass.forName(qualifiedName, x);
            }
            x.bindVariable(str, ojClass);
            DebugOut.println("binds variable\t" + str + "\t: " + qualifiedName);
        }
        catch (OJClassNotFoundException ex) {
            System.err.println("VariableBinder.bindName() " + ex.toString() + " : " + qualifiedName);
            System.err.println(x);
        }
    }
}
