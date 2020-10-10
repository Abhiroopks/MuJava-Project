// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree.util;

import openjava.mop.OJClassNotFoundException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Vector;
import openjava.mop.OJField;
import openjava.mop.OJClass;
import openjava.mop.NoSuchMemberException;
import openjava.mop.FileEnvironment;
import openjava.mop.AnonymousClassEnvironment;
import openjava.ptree.MethodCall;
import openjava.ptree.ParseTreeException;
import openjava.ptree.TypeName;
import openjava.ptree.Variable;
import openjava.tools.DebugOut;
import openjava.ptree.Expression;
import openjava.ptree.FieldAccess;
import openjava.mop.Environment;

public class MemberAccessCorrector extends VariableBinder
{
    private String errorState;
    
    public MemberAccessCorrector(final Environment environment) {
        super(environment);
        this.errorState = null;
    }
    
    public String getErrorState() {
        return this.errorState;
    }
    
    @Override
    public Expression evaluateDown(final FieldAccess fieldAccess) throws ParseTreeException {
        super.evaluateDown(fieldAccess);
        if (fieldAccess.getReferenceType() != null) {
            return fieldAccess;
        }
        final Expression referenceExpr = fieldAccess.getReferenceExpr();
        final String name = fieldAccess.getName();
        if (referenceExpr == null) {
            if (this.isVariable(name)) {
                DebugOut.println("MC variable - " + name);
                return new Variable(name);
            }
            if (this.isField(name)) {
                DebugOut.println("MC field access - " + name);
            }
            else if (this.getEnvironment().getImportedClasses() != null || this.getEnvironment().getImportedPackages() != null) {
                final FieldAccess returnStaticFieldAccess = this.returnStaticFieldAccess(name);
                if (returnStaticFieldAccess != null) {
                    fieldAccess.setReferenceType(returnStaticFieldAccess.getReferenceType());
                }
            }
            else {
                System.err.println("unknown field or variable : " + name);
                System.err.println(this.getEnvironment());
            }
        }
        else if (referenceExpr instanceof Variable) {
            final FieldAccess name2fieldaccess = this.name2fieldaccess(referenceExpr.toString(), name);
            final TypeName referenceType = name2fieldaccess.getReferenceType();
            final Expression referenceExpr2 = name2fieldaccess.getReferenceExpr();
            if (referenceType != null) {
                fieldAccess.setReferenceType(referenceType);
            }
            else {
                fieldAccess.setReferenceExpr(referenceExpr2);
            }
        }
        return fieldAccess;
    }
    
    @Override
    public Expression evaluateDown(final MethodCall methodCall) throws ParseTreeException {
        super.evaluateDown(methodCall);
        if (methodCall.getReferenceType() != null) {
            return methodCall;
        }
        final Expression referenceExpr = methodCall.getReferenceExpr();
        if (referenceExpr == null || !(referenceExpr instanceof Variable)) {
            if (this.getEnvironment().getImportedClasses() != null || this.getEnvironment().getImportedPackages() != null) {
                final MethodCall returnStaticMethodCall = this.returnStaticMethodCall(methodCall.getName(), methodCall);
                if (returnStaticMethodCall != null) {
                    methodCall.setReferenceType(returnStaticMethodCall.getReferenceType());
                }
            }
            return methodCall;
        }
        final String name = methodCall.getName();
        if (referenceExpr instanceof Variable) {
            final FieldAccess name2fieldaccess = this.name2fieldaccess(referenceExpr.toString(), name);
            final TypeName referenceType = name2fieldaccess.getReferenceType();
            final Expression referenceExpr2 = name2fieldaccess.getReferenceExpr();
            if (referenceType != null) {
                methodCall.setReferenceType(referenceType);
            }
            else {
                methodCall.setReferenceExpr(referenceExpr2);
            }
        }
        return methodCall;
    }
    
    private FieldAccess name2fieldaccess(final String s, final String s2) {
        String str = getFirst(s);
        String s3 = getRest(s);
        Expression expression;
        if (this.isVariable(str)) {
            DebugOut.println("MC variable - " + str);
            expression = new Variable(str);
        }
        else if (this.isField(str)) {
            DebugOut.println("MC field - " + str);
            expression = new FieldAccess((Expression)null, str);
        }
        else {
            while (s3 != null && !this.isClass(str)) {
                str = str + "." + getFirst(s3);
                s3 = getRest(s3);
            }
            while (this.isClass(str + "." + getFirst(s3))) {
                str = str + "." + getFirst(s3);
                s3 = getRest(s3);
            }
            if (this.isClass(str)) {
                DebugOut.println("MC class - " + str);
            }
            else {
                System.err.println("unknown class : " + str);
            }
            final TypeName typeName = new TypeName(str);
            if (s3 == null) {
                return new FieldAccess(typeName, s2);
            }
            final String first = getFirst(s3);
            s3 = getRest(s3);
            expression = new FieldAccess(typeName, first);
        }
        while (s3 != null) {
            final String first2 = getFirst(s3);
            s3 = getRest(s3);
            expression = new FieldAccess(expression, first2);
        }
        return new FieldAccess(expression, s2);
    }
    
    private boolean isVariable(final String s) {
        return this.getEnvironment().lookupBind(s) != null;
    }
    
    private boolean isField(final String s) {
        final Environment environment = this.getEnvironment();
        final String qualifiedName = environment.toQualifiedName(environment.currentClassName());
        if (qualifiedName.indexOf("anonymous class") >= 0) {
            Environment environment2 = this.getEnvironment();
            if (environment2 instanceof AnonymousClassEnvironment && ((AnonymousClassEnvironment)environment2).isField(s)) {
                return true;
            }
            do {
                final Environment parentEnvironment = environment2.getParentEnvironment();
                if (parentEnvironment instanceof AnonymousClassEnvironment && ((AnonymousClassEnvironment)parentEnvironment).isField(s)) {
                    return true;
                }
                environment2 = parentEnvironment;
            } while (!(environment2 instanceof FileEnvironment));
        }
        OJClass ojClass;
        OJField field;
        for (ojClass = environment.lookupClass(qualifiedName), field = null; ojClass != null && field == null; ojClass = ojClass.getDeclaringClass()) {
            try {
                field = ojClass.getField(s, ojClass);
            }
            catch (NoSuchMemberException ex) {}
        }
        return field != null;
    }
    
    private FieldAccess returnStaticFieldAccess(final String s) {
        FieldAccess importedField = null;
        final Environment environment = this.getEnvironment();
        final Vector importedClasses = environment.getImportedClasses();
        for (int i = 0; i < importedClasses.size(); ++i) {
            final String s2 = (String) importedClasses.get(i);
            if (s2.indexOf("static") >= 0) {
                importedField = this.findImportedField(this.getStaticClass(s2), s);
                if (importedField != null) {
                    return importedField;
                }
            }
        }
        final Vector importedPackages = environment.getImportedPackages();
        for (int j = 0; j < importedPackages.size(); ++j) {
            final String s3 = (String) importedPackages.get(j);
            if (s3.indexOf("static") >= 0) {
                return this.findImportedField(this.getStaticPackage(s3), s);
            }
        }
        return importedField;
    }
    
    private MethodCall returnStaticMethodCall(final String s, final MethodCall methodCall) {
        MethodCall methodCall2 = null;
        final Environment environment = this.getEnvironment();
        final Vector importedClasses = environment.getImportedClasses();
        for (int i = 0; i < importedClasses.size(); ++i) {
            final String s2 = (String) importedClasses.get(i);
            if (s2.indexOf("static") >= 0) {
                methodCall2 = this.findImportedMethodCall(this.getStaticClass(s2), s, methodCall);
                if (methodCall2 != null) {
                    return methodCall2;
                }
            }
        }
        final Vector importedPackages = environment.getImportedPackages();
        for (int j = 0; j < importedPackages.size(); ++j) {
            final String s3 = (String) importedPackages.get(j);
            if (s3.indexOf("static") >= 0) {
                methodCall2 = this.findImportedMethodCall(this.getStaticPackage(s3), s, methodCall);
                if (methodCall2 != null) {
                    return methodCall2;
                }
            }
        }
        return methodCall2;
    }
    
    private String getStaticClass(final String s) {
        final String[] split = s.split(" ");
        return split[1].substring(0, split[1].lastIndexOf("."));
    }
    
    private String getStaticPackage(final String s) {
        return s.split(" ")[1];
    }
    
    private FieldAccess findImportedField(final String className, final String anObject) {
        final FieldAccess fieldAccess = null;
        Class<?> forName = null;
        try {
            forName = Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        if (forName != null) {
            final Field[] fields = forName.getFields();
            for (int length = fields.length, i = 0; i < length; ++i) {
                if (fields[i].getName().equals(anObject)) {
                    return new FieldAccess(new TypeName(className), anObject);
                }
            }
        }
        return fieldAccess;
    }
    
    private MethodCall findImportedMethodCall(final String className, final String anObject, final MethodCall methodCall) {
        final MethodCall methodCall2 = null;
        Class<?> forName = null;
        try {
            forName = Class.forName(className);
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        if (forName != null) {
            final Method[] methods = forName.getMethods();
            for (int length = methods.length, i = 0; i < length; ++i) {
                if (methods[i].getName().equals(anObject)) {
                    return new MethodCall(new TypeName(className), anObject, methodCall.getArguments());
                }
            }
        }
        return methodCall2;
    }
    
    private boolean isClass(final String s) {
        final Environment environment = this.getEnvironment();
        final String qualifiedName = environment.toQualifiedName(s);
        try {
            OJClass.forName(qualifiedName);
            return true;
        }
        catch (OJClassNotFoundException ex) {
            return environment.lookupClass(qualifiedName) != null;
        }
    }
    
    private static final String getFirst(final String s) {
        if (s == null) {
            return null;
        }
        final int index = s.indexOf(46);
        if (index == -1) {
            return s;
        }
        return s.substring(0, index);
    }
    
    private static final String getRest(final String s) {
        if (s == null) {
            return null;
        }
        final int index = s.indexOf(46);
        if (index == -1) {
            return null;
        }
        return s.substring(index + 1);
    }
}
