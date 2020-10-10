// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.io.IOException;
import java.io.Writer;
import java.util.Vector;
import java.util.Enumeration;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ParseTree;

class OJClassNull extends OJClassImp
{
    @Override
    ClassEnvironment getEnvironment() {
        return new ClassEnvironment(OJSystem.env, this.getName());
    }
    
    @Override
    public String toString() {
        return "<type>null";
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
        return false;
    }
    
    @Override
    boolean isPrimitive() {
        return false;
    }
    
    @Override
    String getName() {
        return null;
    }
    
    @Override
    OJClass getSuperclass() {
        return null;
    }
    
    @Override
    OJClass[] getInterfaces() {
        return null;
    }
    
    @Override
    OJClass getComponentType() {
        return null;
    }
    
    @Override
    OJModifier getModifiers() {
        return null;
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
        return null;
    }
    
    @Override
    OJField[] getDeclaredFields() {
        return null;
    }
    
    OJEnumConstant[] getDeclaredEnumConstants() {
        return null;
    }
    
    @Override
    OJMethod[] getDeclaredMethods() {
        return null;
    }
    
    @Override
    OJConstructor[] getDeclaredConstructors() {
        return null;
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
        return null;
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
