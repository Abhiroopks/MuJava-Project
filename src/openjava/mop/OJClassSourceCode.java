// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.net.URL;
import java.io.InputStream;
import java.util.Hashtable;
import openjava.ptree.ParseTree;
import openjava.ptree.TypeName;
import openjava.ptree.MemberDeclaration;
import openjava.ptree.MemberDeclarationList;
import openjava.ptree.EnumConstantList;
import openjava.ptree.MemberInitializer;
import openjava.ptree.ConstructorDeclaration;
import openjava.ptree.MethodDeclaration;
import openjava.ptree.FieldDeclaration;
import java.util.Vector;
import openjava.ptree.ClassDeclaration;

class OJClassSourceCode extends OJClassImp
{
    private OJClass declarer;
    private ClassDeclaration definition;
    private ClassEnvironment env;
    private Vector classes;
    private Vector fields;
    private Vector methods;
    private Vector constrs;
    private Vector enumConstants;
    private MetaInfo metainfo;
    
    OJClassSourceCode(final OJClass ojClass, final Environment environment, final OJClass declarer, final ClassDeclaration definition) {
        this.classes = new Vector();
        this.fields = new Vector();
        this.methods = new Vector();
        this.constrs = new Vector();
        this.enumConstants = new Vector();
        this.declarer = declarer;
        this.definition = definition;
        String s;
        if (declarer == null) {
            s = environment.toQualifiedName(this.definition.getName());
        }
        else {
            s = environment.currentClassName() + "." + this.definition.getName();
        }
        this.env = new ClassEnvironment(environment, s);
        this.metainfo = new MetaInfo(ojClass.getClass().getName(), s);
        if (definition.isEnumeration()) {
            final EnumConstantList enumConstants = definition.getEnumConstants();
            if (enumConstants != null) {
                for (int i = 0; i < enumConstants.size(); ++i) {
                    final OJField ojField = new OJField(this.env, ojClass, enumConstants.get(i));
                    this.enumConstants.addElement(ojField);
                    this.fields.addElement(ojField);
                }
            }
        }
        if (definition.getBody() != null) {
            final MemberDeclarationList body = definition.getBody();
            for (int j = 0; j < body.size(); ++j) {
                final MemberDeclaration value = body.get(j);
                if (value instanceof ClassDeclaration) {
                    final ClassDeclaration classDeclaration = (ClassDeclaration)value;
                    this.env.recordMemberClass(classDeclaration.getName());
                    try {
                        this.classes.addElement(OJClass.forParseTree(this.env, ojClass, classDeclaration));
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else if (value instanceof FieldDeclaration) {
                    this.fields.addElement(new OJField(this.env, ojClass, (FieldDeclaration)value));
                }
                else if (value instanceof MethodDeclaration) {
                    this.methods.addElement(new OJMethod(this.env, ojClass, (MethodDeclaration)value));
                }
                else if (value instanceof ConstructorDeclaration) {
                    this.constrs.addElement(new OJConstructor(this.env, ojClass, (ConstructorDeclaration)value));
                }
                else if (value instanceof MemberInitializer) {}
            }
        }
    }
    
    @Override
    public String toString() {
        return "class " + this.getName();
    }
    
    @Override
    ClassEnvironment getEnvironment() {
        return this.env;
    }
    
    @Override
    boolean isInterface() {
        return this.definition.isInterface();
    }
    
    @Override
    boolean isEnumeration() {
        return this.definition.isEnumeration();
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
        if (this.declarer == null) {
            return this.env.toQualifiedName(this.definition.getName());
        }
        return this.declarer.getName() + "." + this.definition.getName();
    }
    
    @Override
    OJClass getSuperclass() {
        if (this.isInterface()) {
            return null;
        }
        final TypeName baseclass = this.definition.getBaseclass();
        return this.forNameAnyway((baseclass == null) ? "java.lang.Object" : baseclass.toString());
    }
    
    @Override
    OJClass[] getInterfaces() {
        TypeName[] array;
        if (this.isInterface()) {
            array = this.definition.getBaseclasses();
        }
        else {
            array = this.definition.getInterfaces();
        }
        final String[] array2 = new String[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = array[i].toString();
        }
        return this.arrayForNames(array2);
    }
    
    @Override
    OJClass getComponentType() {
        return null;
    }
    
    @Override
    OJModifier getModifiers() {
        return OJModifier.forParseTree(this.definition.getModifiers());
    }
    
    @Override
    ParseTree getSuffix(final String key) {
        final Hashtable suffixes = this.definition.getSuffixes();
        if (suffixes == null) {
            return null;
        }
        return (ParseTree) suffixes.get(key);
    }
    
    @Override
    OJClass getDeclaringClass() {
        return this.declarer;
    }
    
    @Override
    OJClass[] getDeclaredClasses() {
        final OJClass[] array = new OJClass[this.classes.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (OJClass)this.classes.elementAt(i);
        }
        return array;
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
        final OJMethod[] array = new OJMethod[this.methods.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (OJMethod)this.methods.elementAt(i);
        }
        return array;
    }
    
    @Override
    OJConstructor[] getDeclaredConstructors() {
        final OJConstructor[] array = new OJConstructor[this.constrs.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = (OJConstructor)this.constrs.elementAt(i);
        }
        return array;
    }
    
    InputStream getResourceAsStream(final String s) throws CannotInspectException {
        throw new CannotInspectException("getResourceAsStream()");
    }
    
    URL getResource(final String s) throws CannotInspectException {
        throw new CannotInspectException("getResource()");
    }
    
    @Override
    boolean isExecutable() {
        return false;
    }
    
    @Override
    boolean isAlterable() {
        return true;
    }
    
    @Override
    Class getByteCode() throws CannotExecuteException {
        throw new CannotExecuteException("getByteCode()");
    }
    
    @Override
    ClassDeclaration getSourceCode() throws CannotAlterException {
        return this.definition;
    }
    
    @Override
    Class getCompatibleJavaClass() {
        return this.getSuperclass().getCompatibleJavaClass();
    }
    
    @Override
    void setDeclaringClass(final OJClass declarer) throws CannotAlterException {
        this.declarer = declarer;
    }
    
    @Override
    OJClass addClass(final OJClass obj) throws CannotAlterException {
        if (!obj.isAlterable()) {
            throw new CannotAlterException("cannot add by addClass()");
        }
        this.classes.addElement(obj);
        this.getSourceCode().getBody().add(obj.getSourceCode());
        return obj;
    }
    
    @Override
    OJClass removeClass(final OJClass obj) throws CannotAlterException {
        if (!obj.isAlterable()) {
            throw new CannotAlterException("cannot remove by removeClass()");
        }
        if (!this.classes.removeElement(obj)) {
            return null;
        }
        final ClassDeclaration sourceCode = obj.getSourceCode();
        final MemberDeclarationList body = this.getSourceCode().getBody();
        for (int i = 0; i < body.size(); ++i) {
            if (body.get(i) == sourceCode) {
                body.remove(i--);
            }
        }
        return obj;
    }
    
    @Override
    OJField addField(final OJField obj) throws CannotAlterException {
        if (!obj.isAlterable()) {
            throw new CannotAlterException("cannot add by addField()");
        }
        this.fields.addElement(obj);
        this.getSourceCode().getBody().add(obj.getSourceCode());
        return obj;
    }
    
    @Override
    OJField removeField(final OJField obj) throws CannotAlterException {
        if (!obj.isAlterable()) {
            throw new CannotAlterException("cannot remove by removeField()");
        }
        if (!this.fields.removeElement(obj)) {
            return null;
        }
        final FieldDeclaration sourceCode = obj.getSourceCode();
        final MemberDeclarationList body = this.getSourceCode().getBody();
        for (int i = 0; i < body.size(); ++i) {
            if (body.get(i) == sourceCode) {
                body.remove(i--);
            }
        }
        return obj;
    }
    
    @Override
    OJMethod addMethod(final OJMethod obj) throws CannotAlterException {
        if (!obj.isAlterable()) {
            throw new CannotAlterException("cannot add by addMethod()");
        }
        this.methods.addElement(obj);
        this.getSourceCode().getBody().add(obj.getSourceCode());
        return obj;
    }
    
    @Override
    OJMethod removeMethod(final OJMethod obj) throws CannotAlterException {
        if (!obj.isAlterable()) {
            throw new CannotAlterException("cannot remove by removeMethod()");
        }
        if (!this.methods.removeElement(obj)) {
            return null;
        }
        final MethodDeclaration sourceCode = obj.getSourceCode();
        final MemberDeclarationList body = this.getSourceCode().getBody();
        for (int i = 0; i < body.size(); ++i) {
            if (body.get(i) == sourceCode) {
                body.remove(i--);
            }
        }
        return obj;
    }
    
    @Override
    OJConstructor addConstructor(final OJConstructor obj) throws CannotAlterException {
        if (!obj.isAlterable()) {
            throw new CannotAlterException("cannot add by addConstructor()");
        }
        this.constrs.addElement(obj);
        this.getSourceCode().getBody().add(obj.getSourceCode());
        return obj;
    }
    
    @Override
    OJConstructor removeConstructor(final OJConstructor obj) throws CannotAlterException {
        if (!obj.isAlterable()) {
            throw new CannotAlterException("cannot remove by removeConstructor()");
        }
        if (!this.constrs.removeElement(obj)) {
            return null;
        }
        final ConstructorDeclaration sourceCode = obj.getSourceCode();
        final MemberDeclarationList body = this.getSourceCode().getBody();
        for (int i = 0; i < body.size(); ++i) {
            if (body.get(i) == sourceCode) {
                body.remove(i--);
            }
        }
        return obj;
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
        return this.metainfo.put(s, s2);
    }
    
    @Override
    void writeMetaInfo(final Writer writer) throws IOException {
        this.metainfo.write(writer);
    }
}
