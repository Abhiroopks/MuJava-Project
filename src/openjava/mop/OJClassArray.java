// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ParseTree;
import java.util.Vector;

class OJClassArray extends OJClassImp
{
    private OJClass componentType;
    private OJClass[] classes;
    private OJMethod[] methods;
    private OJConstructor[] constrs;
    private Vector fields;
    private Vector enumConstants;
    private MetaInfo metainfo;
    
    OJClassArray(final OJClass componentType) {
        this.classes = new OJClass[0];
        this.methods = new OJMethod[0];
        this.constrs = new OJConstructor[0];
        this.fields = new Vector();
        this.enumConstants = new Vector();
        this.componentType = componentType;
        this.fields.addElement(this.makeLengthField());
        this.metainfo = new MetaInfo(componentType.getName() + "[]");
    }
    
    private final OJField makeLengthField() {
        return new OJField(this.componentType, new OJModifier(17), OJClass.forClass(Integer.TYPE), "length");
    }
    
    @Override
    ClassEnvironment getEnvironment() {
        return (ClassEnvironment)this.componentType.getEnvironment();
    }
    
    @Override
    public String toString() {
        return "class " + this.componentType.getName() + "[]";
    }
    
    @Override
    boolean isInterface() {
        return false;
    }
    
    @Override
    boolean isEnumeration() {
        return false;
    }
    
    @Override
    boolean isArray() {
        return true;
    }
    
    @Override
    boolean isPrimitive() {
        return false;
    }
    
    @Override
    String getName() {
        return this.componentType.getName() + "[]";
    }
    
    ClassLoader getClassLoader() throws CannotInspectException {
        throw new CannotInspectException("getClassLoader()");
    }
    
    @Override
    OJClass getSuperclass() {
        return OJClass.forClass(Object[].class.getSuperclass());
    }
    
    @Override
    OJClass[] getInterfaces() {
        return OJClass.arrayForClasses(Object[].class.getInterfaces());
    }
    
    @Override
    OJClass getComponentType() {
        return this.componentType;
    }
    
    @Override
    OJModifier getModifiers() {
        return OJModifier.forModifier(Object[].class.getModifiers());
    }
    
    @Override
    ParseTree getSuffix(final String s) {
        return null;
    }
    
    @Override
    OJClass getDeclaringClass() {
        return null;
    }
    
    @Override
    OJClass[] getDeclaredClasses() {
        return this.classes;
    }
    
    @Override
    OJField[] getDeclaredFields() {
        final OJField[] array = new OJField[this.fields.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (OJField)this.fields.elementAt(i);
        }
        return array;
    }
    
    OJEnumConstant[] getDeclaredEnumConstants() {
        final OJEnumConstant[] array = new OJEnumConstant[this.enumConstants.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (OJEnumConstant)this.enumConstants.elementAt(i);
        }
        return array;
    }
    
    @Override
    OJMethod[] getDeclaredMethods() {
        return this.methods;
    }
    
    @Override
    OJConstructor[] getDeclaredConstructors() {
        return this.constrs;
    }
    
    @Override
    boolean isExecutable() {
        return false;
    }
    
    @Override
    boolean isAlterable() {
        return false;
    }
    
    @Override
    Class getByteCode() throws CannotExecuteException {
        throw new CannotExecuteException("getByteCode()");
    }
    
    @Override
    ClassDeclaration getSourceCode() throws CannotAlterException {
        throw new CannotAlterException("getSourceCode()");
    }
    
    @Override
    Class getCompatibleJavaClass() {
        return this.getSuperclass().getCompatibleJavaClass();
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
        return null;
    }
    
    @Override
    Enumeration getMetaInfoKeys() {
        return new Vector().elements();
    }
    
    @Override
    Enumeration getMetaInfoElements() {
        return new Vector().elements();
    }
    
    @Override
    String putMetaInfo(final String s, final String s2) throws CannotAlterException {
        throw new CannotAlterException("putMetaInfo()");
    }
    
    @Override
    void writeMetaInfo(final Writer writer) throws IOException {
    }
}
