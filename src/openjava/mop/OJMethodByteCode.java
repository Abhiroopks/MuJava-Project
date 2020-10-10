// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.StatementList;
import openjava.ptree.MethodDeclaration;
import java.lang.reflect.InvocationTargetException;
import openjava.ptree.ParseTree;
import java.lang.reflect.Method;

class OJMethodByteCode extends OJMethodImp
{
    private Method javaMethod;
    
    OJMethodByteCode(final Method javaMethod) {
        this.javaMethod = null;
        this.javaMethod = javaMethod;
    }
    
    @Override
    public String toString() {
        return this.javaMethod.toString();
    }
    
    @Override
    OJClass getDeclaringClass() {
        return OJClass.forClass(this.javaMethod.getDeclaringClass());
    }
    
    @Override
    String getName() {
        return this.javaMethod.getName();
    }
    
    @Override
    String getIdentifiableName() {
        return this.getDeclaringClass().getName() + "." + this.getName() + "()";
    }
    
    @Override
    OJModifier getModifiers() {
        return OJModifier.forModifier(this.javaMethod.getModifiers());
    }
    
    @Override
    OJClass getReturnType() {
        return OJClass.forClass(this.javaMethod.getReturnType());
    }
    
    @Override
    OJClass[] getParameterTypes() {
        return OJClass.arrayForClasses(this.javaMethod.getParameterTypes());
    }
    
    @Override
    OJClass[] getExceptionTypes() {
        return OJClass.arrayForClasses(this.javaMethod.getExceptionTypes());
    }
    
    @Override
    ParseTree getSuffix(final String s) {
        return null;
    }
    
    @Override
    Environment getEnvironment() {
        return new ClosedEnvironment(this.getDeclaringClass().getEnvironment());
    }
    
    @Override
    Object invoke(final Object obj, final Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, CannotExecuteException {
        return this.javaMethod.invoke(obj, args);
    }
    
    @Override
    boolean isAlterable() {
        return false;
    }
    
    @Override
    boolean isExecutable() {
        return true;
    }
    
    @Override
    Method getByteCode() throws CannotExecuteException {
        return this.javaMethod;
    }
    
    @Override
    MethodDeclaration getSourceCode() throws CannotAlterException {
        throw new CannotAlterException("getSourceCode()");
    }
    
    @Override
    StatementList getBody() throws CannotAlterException {
        throw new CannotAlterException("getBody()");
    }
    
    @Override
    void setDeclaringClass(final OJClass ojClass) throws CannotAlterException {
        throw new CannotAlterException("setDeclaringClass()");
    }
    
    @Override
    final void setName(final String s) throws CannotAlterException {
        throw new CannotAlterException("setName()");
    }
    
    @Override
    final void setModifiers(final int n) throws CannotAlterException {
        throw new CannotAlterException("setModifiers()");
    }
    
    @Override
    final void setReturnType(final OJClass ojClass) throws CannotAlterException {
        throw new CannotAlterException("setReturnType()");
    }
    
    @Override
    final void setExceptionTypes(final OJClass[] array) throws CannotAlterException {
        throw new CannotAlterException("setExceptionTypes()");
    }
    
    @Override
    StatementList setBody(final StatementList list) throws CannotAlterException {
        throw new CannotAlterException("setBody()");
    }
}
