// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import openjava.ptree.ClassDeclaration;
import java.lang.reflect.Constructor;
import openjava.ptree.ParseTree;

class OJClassByteCode extends OJClassImp
{
    private Class javaClass;
    private MetaInfo metainfo;
    
    OJClassByteCode(final Class javaClass, final MetaInfo metainfo) {
        this.javaClass = javaClass;
        this.metainfo = metainfo;
    }
    
    @Override
    ClassEnvironment getEnvironment() {
        final int lastIndex = this.getName().lastIndexOf(46);
        final String s = (lastIndex == -1) ? null : this.getName().substring(0, lastIndex);
        final String simpleName = Environment.toSimpleName(this.getName());
        return new ClassEnvironment(new FileEnvironment(OJSystem.env, s, simpleName), simpleName);
    }
    
    @Override
    public String toString() {
        return this.isPrimitive() ? this.getName() : ("class " + this.getName());
    }
    
    @Override
    boolean isInterface() {
        return this.javaClass.isInterface();
    }
    
    @Override
    boolean isEnumeration() {
        return this.javaClass.isEnum();
    }
    
    @Override
    boolean isArray() {
        return this.javaClass.isArray();
    }
    
    @Override
    boolean isPrimitive() {
        return this.javaClass.isPrimitive();
    }
    
    @Override
    String getName() {
        return OJClassImp.nameForJavaClassName(this.javaClass.getName());
    }
    
    ClassLoader getClassLoader() throws CannotInspectException {
        return this.javaClass.getClassLoader();
    }
    
    @Override
    OJClass getSuperclass() {
        final Class superclass = this.javaClass.getSuperclass();
        return (superclass == null) ? null : OJClassImp.forClass(superclass);
    }
    
    @Override
    OJClass[] getInterfaces() {
        return OJClass.arrayForClasses(this.javaClass.getInterfaces());
    }
    
    @Override
    OJClass getComponentType() {
        final Class componentType = this.javaClass.getComponentType();
        return (componentType == null) ? null : OJClassImp.forClass(componentType);
    }
    
    @Override
    OJModifier getModifiers() {
        return OJModifier.forModifier(this.javaClass.getModifiers());
    }
    
    @Override
    ParseTree getSuffix(final String s) {
        return null;
    }
    
    @Override
    OJClass getDeclaringClass() {
        final Class declaringClass = this.javaClass.getDeclaringClass();
        return (declaringClass == null) ? null : OJClassImp.forClass(declaringClass);
    }
    
    @Override
    OJClass[] getDeclaredClasses() {
        try {
            return OJClass.arrayForClasses(this.javaClass.getDeclaredClasses());
        }
        catch (SecurityException x) {
            System.err.println(x);
            return new OJClass[0];
        }
    }
    
    @Override
    OJField[] getDeclaredFields() {
        try {
            return OJClassImp.arrayForFields(this.javaClass.getDeclaredFields());
        }
        catch (SecurityException x) {
            System.err.println(x);
            return new OJField[0];
        }
    }
    
    @Override
    OJMethod[] getDeclaredMethods() {
        try {
            return OJClassImp.arrayForMethods(this.javaClass.getDeclaredMethods());
        }
        catch (SecurityException x) {
            System.err.println(x);
            return new OJMethod[0];
        }
    }
    
    @Override
    OJConstructor[] getDeclaredConstructors() {
        try {
            return OJClassImp.arrayForConstructors(this.javaClass.getDeclaredConstructors());
        }
        catch (SecurityException x) {
            System.err.println(x);
            return new OJConstructor[0];
        }
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
    Class getByteCode() throws CannotExecuteException {
        return this.javaClass;
    }
    
    @Override
    ClassDeclaration getSourceCode() throws CannotAlterException {
        throw new CannotAlterException("getSourceCode()");
    }
    
    @Override
    Class getCompatibleJavaClass() {
        return this.javaClass;
    }
    
    @Override
    void setDeclaringClass(final OJClass ojClass) throws CannotAlterException {
        throw new CannotAlterException("setDeclaringClass()");
    }
    
    @Override
    OJClass addClass(final OJClass ojClass) throws CannotAlterException {
        throw new CannotAlterException("addClass()");
    }
    
    @Override
    OJClass removeClass(final OJClass ojClass) throws CannotAlterException {
        throw new CannotAlterException("removeClass()");
    }
    
    @Override
    OJField addField(final OJField ojField) throws CannotAlterException {
        throw new CannotAlterException("addField()");
    }
    
    @Override
    OJField removeField(final OJField ojField) throws CannotAlterException {
        throw new CannotAlterException("removeField()");
    }
    
    @Override
    OJMethod addMethod(final OJMethod ojMethod) throws CannotAlterException {
        throw new CannotAlterException("addMethod()");
    }
    
    @Override
    OJMethod removeMethod(final OJMethod ojMethod) throws CannotAlterException {
        throw new CannotAlterException("removeMethod()");
    }
    
    @Override
    OJConstructor addConstructor(final OJConstructor ojConstructor) throws CannotAlterException {
        throw new CannotAlterException("addConstructor()");
    }
    
    @Override
    OJConstructor removeConstructor(final OJConstructor ojConstructor) throws CannotAlterException {
        throw new CannotAlterException("removeConstructor()");
    }
    
    @Override
    String getMetaInfo(final String s) {
        return this.metainfo.get(s);
    }
    
    @Override
    Enumeration getMetaInfoKeys() {
        return this.metainfo.keys();
    }
    
    @Override
    Enumeration getMetaInfoElements() {
        return this.metainfo.elements();
    }
    
    @Override
    String putMetaInfo(final String s, final String s2) throws CannotAlterException {
        throw new CannotAlterException("putMetaInfo()");
    }
    
    @Override
    void writeMetaInfo(final Writer writer) throws IOException {
    }
}
