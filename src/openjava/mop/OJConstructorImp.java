// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.ConstructorInvocation;
import openjava.ptree.StatementList;
import openjava.ptree.ConstructorDeclaration;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import openjava.ptree.ParseTree;

abstract class OJConstructorImp
{
    @Override
    public abstract String toString();
    
    abstract Environment getEnvironment();
    
    abstract OJClass getDeclaringClass();
    
    abstract String getName();
    
    abstract String getIdentifiableName();
    
    abstract OJModifier getModifiers();
    
    abstract OJClass[] getParameterTypes();
    
    abstract OJClass[] getExceptionTypes();
    
    abstract ParseTree getSuffix(final String p0);
    
    abstract Object newInstance(final Object[] p0) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CannotExecuteException;
    
    abstract boolean isExecutable();
    
    abstract boolean isAlterable();
    
    abstract Constructor getByteCode() throws CannotExecuteException;
    
    abstract ConstructorDeclaration getSourceCode() throws CannotAlterException;
    
    abstract StatementList getBody() throws CannotAlterException;
    
    abstract ConstructorInvocation getTransference() throws CannotAlterException;
    
    abstract void setDeclaringClass(final OJClass p0) throws CannotAlterException;
    
    abstract void setModifiers(final int p0) throws CannotAlterException;
    
    abstract StatementList setBody(final StatementList p0) throws CannotAlterException;
    
    abstract void setExceptionTypes(final OJClass[] p0) throws CannotAlterException;
    
    abstract ConstructorInvocation setTransference(final ConstructorInvocation p0) throws CannotAlterException;
}
