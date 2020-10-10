// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.StatementList;
import openjava.ptree.MethodDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import openjava.ptree.ParseTree;

abstract class OJMethodImp
{
    @Override
    public abstract String toString();
    
    abstract OJClass getDeclaringClass();
    
    abstract String getName();
    
    abstract String getIdentifiableName();
    
    abstract OJModifier getModifiers();
    
    abstract OJClass getReturnType();
    
    abstract OJClass[] getParameterTypes();
    
    abstract OJClass[] getExceptionTypes();
    
    abstract ParseTree getSuffix(final String p0);
    
    abstract Environment getEnvironment();
    
    abstract Object invoke(final Object p0, final Object[] p1) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, CannotExecuteException;
    
    abstract boolean isAlterable();
    
    abstract boolean isExecutable();
    
    abstract Method getByteCode() throws CannotExecuteException;
    
    abstract MethodDeclaration getSourceCode() throws CannotAlterException;
    
    abstract StatementList getBody() throws CannotAlterException;
    
    abstract void setDeclaringClass(final OJClass p0) throws CannotAlterException;
    
    abstract void setName(final String p0) throws CannotAlterException;
    
    abstract void setModifiers(final int p0) throws CannotAlterException;
    
    abstract void setReturnType(final OJClass p0) throws CannotAlterException;
    
    abstract void setExceptionTypes(final OJClass[] p0) throws CannotAlterException;
    
    abstract StatementList setBody(final StatementList p0) throws CannotAlterException;
}
