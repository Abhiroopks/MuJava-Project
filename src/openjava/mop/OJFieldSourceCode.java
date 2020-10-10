// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.TypeName;
import openjava.ptree.ModifierList;
import java.lang.reflect.Field;
import openjava.ptree.FieldAccess;
import openjava.ptree.EnumConstant;
import openjava.ptree.FieldDeclaration;

class OJFieldSourceCode extends OJFieldImp
{
    private static int idCounter;
    private int id;
    private OJClass declarer;
    private FieldDeclaration definition;
    private EnumConstant definitionEnum;
    private Environment env;
    private FieldAccess definitionStaticImportedField;
    
    OJFieldSourceCode(final Environment env, final OJClass declarer, final FieldDeclaration definition) {
        this.definitionEnum = null;
        this.definitionStaticImportedField = null;
        this.declarer = declarer;
        this.definition = definition;
        this.env = env;
        this.id = OJFieldSourceCode.idCounter++;
    }
    
    OJFieldSourceCode(final Environment env, final OJClass declarer, final EnumConstant definitionEnum) {
        this.definitionEnum = null;
        this.definitionStaticImportedField = null;
        this.declarer = declarer;
        this.definitionEnum = definitionEnum;
        this.env = env;
        this.id = OJFieldSourceCode.idCounter++;
    }
    
    OJFieldSourceCode(final Environment env, final OJClass declarer, final FieldAccess definitionStaticImportedField) {
        this.definitionEnum = null;
        this.definitionStaticImportedField = null;
        this.declarer = declarer;
        this.definitionStaticImportedField = definitionStaticImportedField;
        this.env = env;
        this.id = OJFieldSourceCode.idCounter++;
    }
    
    @Override
    public String toString() {
        final OJClass declaringClass = this.getDeclaringClass();
        final String str = (declaringClass == null) ? "*" : declaringClass.getName();
        final StringBuffer sb = new StringBuffer();
        final String string = this.getModifiers().toString();
        if (!string.equals("")) {
            sb.append(string);
            sb.append(" ");
        }
        sb.append(this.getType().getName());
        sb.append(" ");
        sb.append(str);
        sb.append(".");
        sb.append(this.getName());
        return sb.toString();
    }
    
    @Override
    OJClass getDeclaringClass() {
        return this.declarer;
    }
    
    @Override
    String getName() {
        if (this.definition != null) {
            return this.definition.getName();
        }
        if (this.definitionEnum != null) {
            return this.definitionEnum.getName();
        }
        return this.definitionStaticImportedField.getName();
    }
    
    @Override
    String getIdentifiableName() {
        final OJClass declaringClass = this.getDeclaringClass();
        String str;
        if (declaringClass == null) {
            str = "*" + this.id;
        }
        else {
            str = declaringClass.getName();
        }
        return str + "." + this.getName();
    }
    
    @Override
    OJModifier getModifiers() {
        if (this.definitionEnum == null) {
            return OJModifier.forParseTree(this.definition.getModifiers());
        }
        return OJModifier.forParseTree(this.definitionEnum.getModifiers());
    }
    
    @Override
    OJClass getType() {
        String s;
        if (this.definition != null) {
            s = this.definition.getTypeSpecifier().toString();
        }
        else if (this.definitionEnum != null) {
            s = this.definitionEnum.getEnumType();
        }
        else {
            s = this.definitionStaticImportedField.getReferenceType().toString();
        }
        return Toolbox.forNameAnyway(this.env, s);
    }
    
    @Override
    Object get(final Object o) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("get()");
    }
    
    @Override
    boolean getBoolean(final Object o) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("getBoolean()");
    }
    
    @Override
    byte getByte(final Object o) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("getByte()");
    }
    
    @Override
    char getChar(final Object o) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("getChar()");
    }
    
    @Override
    short getShort(final Object o) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("getShort()");
    }
    
    @Override
    int getInt(final Object o) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("getInt()");
    }
    
    @Override
    long getLong(final Object o) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("getLong()");
    }
    
    @Override
    float getFloat(final Object o) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("getFloat()");
    }
    
    @Override
    double getDouble(final Object o) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("getDouble()");
    }
    
    @Override
    void set(final Object o, final Object o2) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("set()");
    }
    
    @Override
    void setBoolean(final Object o, final boolean b) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("setBoolean()");
    }
    
    @Override
    void setByte(final Object o, final byte b) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("setByte()");
    }
    
    @Override
    void setChar(final Object o, final char c) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("setChar()");
    }
    
    @Override
    void setShort(final Object o, final short n) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("setShort()");
    }
    
    @Override
    void setInt(final Object o, final int n) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("setInt()");
    }
    
    @Override
    void setLong(final Object o, final long n) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("setLong()");
    }
    
    @Override
    void setFloat(final Object o, final float n) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("setFloat()");
    }
    
    @Override
    void setDouble(final Object o, final double n) throws IllegalArgumentException, IllegalAccessException {
        throw new IllegalArgumentException("setDouble()");
    }
    
    @Override
    boolean isEnumConstant() {
        return this.definitionEnum != null;
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
    final Field getByteCode() throws CannotExecuteException {
        throw new CannotExecuteException("getByteCode()");
    }
    
    @Override
    final FieldDeclaration getSourceCode() throws CannotAlterException {
        return this.definition;
    }
    
    final EnumConstant getEnumConstant() throws CannotAlterException {
        return this.definitionEnum;
    }
    
    Environment getEnvironment() {
        return this.env;
    }
    
    final void setSourceCode(final FieldDeclaration definition) {
        this.definition = definition;
    }
    
    @Override
    void setDeclaringClass(final OJClass declarer) throws CannotAlterException {
        this.declarer = declarer;
    }
    
    @Override
    final void setName(final String variable) throws CannotAlterException {
        this.definition.setVariable(variable);
    }
    
    @Override
    final void setModifiers(final int n) throws CannotAlterException {
        this.definition.setModifiers(new ModifierList(n));
    }
    
    @Override
    final void setType(final OJClass ojClass) throws CannotAlterException {
        this.definition.setTypeSpecifier(TypeName.forOJClass(ojClass));
    }
    
    static {
        OJFieldSourceCode.idCounter = 0;
    }
}
