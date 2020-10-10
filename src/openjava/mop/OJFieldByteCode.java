// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.FieldDeclaration;
import java.lang.reflect.Field;

class OJFieldByteCode extends OJFieldImp
{
    private Field javaField;
    
    OJFieldByteCode(final Field javaField) {
        this.javaField = javaField;
    }
    
    @Override
    public String toString() {
        return this.javaField.toString();
    }
    
    @Override
    OJClass getDeclaringClass() {
        return OJClass.forClass(this.javaField.getDeclaringClass());
    }
    
    @Override
    String getName() {
        return this.javaField.getName();
    }
    
    @Override
    String getIdentifiableName() {
        return this.getDeclaringClass().getName() + "." + this.getName();
    }
    
    @Override
    OJModifier getModifiers() {
        return OJModifier.forModifier(this.javaField.getModifiers());
    }
    
    @Override
    OJClass getType() {
        return OJClass.forClass(this.javaField.getType());
    }
    
    @Override
    Object get(final Object obj) throws IllegalArgumentException, IllegalAccessException {
        return this.javaField.get(obj);
    }
    
    @Override
    boolean getBoolean(final Object obj) throws IllegalArgumentException, IllegalAccessException {
        return this.javaField.getBoolean(obj);
    }
    
    @Override
    byte getByte(final Object obj) throws IllegalArgumentException, IllegalAccessException {
        return this.javaField.getByte(obj);
    }
    
    @Override
    char getChar(final Object obj) throws IllegalArgumentException, IllegalAccessException {
        return this.javaField.getChar(obj);
    }
    
    @Override
    short getShort(final Object obj) throws IllegalArgumentException, IllegalAccessException {
        return this.javaField.getShort(obj);
    }
    
    @Override
    int getInt(final Object obj) throws IllegalArgumentException, IllegalAccessException {
        return this.javaField.getInt(obj);
    }
    
    @Override
    long getLong(final Object obj) throws IllegalArgumentException, IllegalAccessException {
        return this.javaField.getLong(obj);
    }
    
    @Override
    float getFloat(final Object obj) throws IllegalArgumentException, IllegalAccessException {
        return this.javaField.getFloat(obj);
    }
    
    @Override
    double getDouble(final Object obj) throws IllegalArgumentException, IllegalAccessException {
        return this.javaField.getDouble(obj);
    }
    
    @Override
    void set(final Object obj, final Object value) throws IllegalArgumentException, IllegalAccessException {
        this.javaField.set(obj, value);
    }
    
    @Override
    void setBoolean(final Object obj, final boolean z) throws IllegalArgumentException, IllegalAccessException {
        this.javaField.setBoolean(obj, z);
    }
    
    @Override
    void setByte(final Object obj, final byte b) throws IllegalArgumentException, IllegalAccessException {
        this.javaField.setByte(obj, b);
    }
    
    @Override
    void setChar(final Object obj, final char c) throws IllegalArgumentException, IllegalAccessException {
        this.javaField.setChar(obj, c);
    }
    
    @Override
    void setShort(final Object obj, final short s) throws IllegalArgumentException, IllegalAccessException {
        this.javaField.setShort(obj, s);
    }
    
    @Override
    void setInt(final Object obj, final int i) throws IllegalArgumentException, IllegalAccessException {
        this.javaField.setInt(obj, i);
    }
    
    @Override
    void setLong(final Object obj, final long l) throws IllegalArgumentException, IllegalAccessException {
        this.javaField.setLong(obj, l);
    }
    
    @Override
    void setFloat(final Object obj, final float f) throws IllegalArgumentException, IllegalAccessException {
        this.javaField.setFloat(obj, f);
    }
    
    @Override
    void setDouble(final Object obj, final double d) throws IllegalArgumentException, IllegalAccessException {
        this.javaField.setDouble(obj, d);
    }
    
    @Override
    final boolean isEnumConstant() {
        return this.javaField.isEnumConstant();
    }
    
    @Override
    final boolean isExecutable() {
        return true;
    }
    
    @Override
    final boolean isAlterable() {
        return false;
    }
    
    @Override
    final Field getByteCode() throws CannotExecuteException {
        return this.javaField;
    }
    
    @Override
    final FieldDeclaration getSourceCode() throws CannotAlterException {
        throw new CannotAlterException("getSourceCode()");
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
    final void setType(final OJClass ojClass) throws CannotAlterException {
        throw new CannotAlterException("setType()");
    }
}
