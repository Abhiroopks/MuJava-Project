// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ParseTree;

abstract class OJClassImp
{
    @Override
    public abstract String toString();
    
    abstract ClassEnvironment getEnvironment();
    
    abstract boolean isInterface();
    
    abstract boolean isArray();
    
    abstract boolean isPrimitive();
    
    abstract boolean isEnumeration();
    
    abstract String getName();
    
    abstract OJClass getSuperclass();
    
    abstract OJClass[] getInterfaces();
    
    abstract OJClass getComponentType();
    
    abstract OJModifier getModifiers();
    
    abstract ParseTree getSuffix(final String p0);
    
    abstract OJClass getDeclaringClass();
    
    abstract OJClass[] getDeclaredClasses();
    
    abstract OJField[] getDeclaredFields();
    
    abstract OJMethod[] getDeclaredMethods();
    
    abstract OJConstructor[] getDeclaredConstructors();
    
    abstract boolean isExecutable();
    
    abstract boolean isAlterable();
    
    abstract Class getByteCode() throws CannotExecuteException;
    
    abstract ClassDeclaration getSourceCode() throws CannotAlterException;
    
    abstract Class getCompatibleJavaClass();
    
    abstract void setDeclaringClass(final OJClass p0) throws CannotAlterException;
    
    abstract OJClass addClass(final OJClass p0) throws CannotAlterException;
    
    abstract OJClass removeClass(final OJClass p0) throws CannotAlterException;
    
    abstract OJField addField(final OJField p0) throws CannotAlterException;
    
    abstract OJField removeField(final OJField p0) throws CannotAlterException;
    
    abstract OJMethod addMethod(final OJMethod p0) throws CannotAlterException;
    
    abstract OJMethod removeMethod(final OJMethod p0) throws CannotAlterException;
    
    abstract OJConstructor addConstructor(final OJConstructor p0) throws CannotAlterException;
    
    abstract OJConstructor removeConstructor(final OJConstructor p0) throws CannotAlterException;
    
    abstract String getMetaInfo(final String p0);
    
    abstract Enumeration getMetaInfoKeys();
    
    abstract Enumeration getMetaInfoElements();
    
    abstract String putMetaInfo(final String p0, final String p1) throws CannotAlterException;
    
    abstract void writeMetaInfo(final Writer p0) throws IOException;
    
    final OJClass forNameAnyway(final String s) {
        return Toolbox.forNameAnyway(this.getEnvironment(), s);
    }
    
    final OJClass[] arrayForNames(final String[] array) {
        return Toolbox.arrayForNames(this.getEnvironment(), array);
    }
    
    static final OJClass forClass(final Class clazz) {
        return OJClass.forClass(clazz);
    }
    
    static final String nameForJavaClassName(final String s) {
        return Toolbox.nameForJavaClassName(s);
    }
    
    static final String nameToJavaClassName(final String s) {
        return Toolbox.nameToJavaClassName(s);
    }
    
    static final OJField[] arrayForFields(final Field[] array) {
        return OJField.arrayForFields(array);
    }
    
    static final OJMethod[] arrayForMethods(final Method[] array) {
        return OJMethod.arrayForMethods(array);
    }
    
    static final OJConstructor[] arrayForConstructors(final Constructor[] array) {
        return OJConstructor.arrayForConstructors(array);
    }
}
