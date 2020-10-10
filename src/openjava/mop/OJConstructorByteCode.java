// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.ConstructorInvocation;
import openjava.ptree.StatementList;
import openjava.ptree.ConstructorDeclaration;
import java.lang.reflect.InvocationTargetException;
import openjava.ptree.ParseTree;
import java.lang.reflect.Constructor;

class OJConstructorByteCode extends OJConstructorImp
{
    private Constructor javaConstructor;
    
    OJConstructorByteCode(final Constructor javaConstructor) {
        this.javaConstructor = null;
        this.javaConstructor = javaConstructor;
    }
    
    @Override
    public String toString() {
        return this.javaConstructor.toString();
    }
    
    @Override
    Environment getEnvironment() {
        return new ClosedEnvironment(this.getDeclaringClass().getEnvironment());
    }
    
    @Override
    OJClass getDeclaringClass() {
        return OJClass.forClass(this.javaConstructor.getDeclaringClass());
    }
    
    @Override
    String getName() {
        return this.javaConstructor.getName();
    }
    
    @Override
    String getIdentifiableName() {
        return this.getDeclaringClass().getName() + "()";
    }
    
    @Override
    OJModifier getModifiers() {
        return OJModifier.forModifier(this.javaConstructor.getModifiers());
    }
    
    @Override
    OJClass[] getParameterTypes() {
        return OJClass.arrayForClasses(this.javaConstructor.getParameterTypes());
    }
    
    @Override
    OJClass[] getExceptionTypes() {
        return OJClass.arrayForClasses(this.javaConstructor.getExceptionTypes());
    }
    
    @Override
    ParseTree getSuffix(final String s) {
        return null;
    }
    
    @Override
    Object newInstance(final Object[] initargs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return this.javaConstructor.newInstance(initargs);
    }
    
    @Override
    boolean isExecutable() {
        return true;
    }
    
    @Override
    boolean isAlterable() {
        return false;
    }
    
    @Override
    Constructor getByteCode() throws CannotExecuteException {
        return this.javaConstructor;
    }
    
    @Override
    ConstructorDeclaration getSourceCode() throws CannotAlterException {
        throw new CannotAlterException("getSourceCode()");
    }
    
    @Override
    StatementList getBody() throws CannotAlterException {
        throw new CannotAlterException("getBody()");
    }
    
    @Override
    ConstructorInvocation getTransference() throws CannotAlterException {
        throw new CannotAlterException("getTransference()");
    }
    
    @Override
    void setDeclaringClass(final OJClass ojClass) throws CannotAlterException {
        throw new CannotAlterException("setDeclaringClass()");
    }
    
    final void setName(final String s) throws CannotAlterException {
        throw new CannotAlterException("setName()");
    }
    
    @Override
    final void setModifiers(final int n) throws CannotAlterException {
        throw new CannotAlterException("setModifiers()");
    }
    
    @Override
    final void setExceptionTypes(final OJClass[] array) throws CannotAlterException {
        throw new CannotAlterException("setExceptionTypes()");
    }
    
    @Override
    StatementList setBody(final StatementList list) throws CannotAlterException {
        throw new CannotAlterException("setBody()");
    }
    
    @Override
    ConstructorInvocation setTransference(final ConstructorInvocation constructorInvocation) throws CannotAlterException {
        throw new CannotAlterException("setTransference()");
    }
}
