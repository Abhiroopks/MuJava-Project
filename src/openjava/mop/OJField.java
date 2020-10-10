// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.FieldAccess;
import openjava.ptree.EnumConstant;
import openjava.ptree.VariableInitializer;
import openjava.ptree.FieldDeclaration;
import openjava.ptree.TypeName;
import openjava.ptree.ModifierList;
import java.lang.reflect.Field;
import java.util.Hashtable;

public class OJField implements OJMember, Cloneable
{
    private OJFieldImp substance;
    private static Hashtable table;
    
    OJField(final Field field) {
        this.substance = new OJFieldByteCode(field);
    }
    
    public OJField(final OJClass ojClass, final OJModifier ojModifier, final OJClass ojClass2, final String s) {
        final Environment environment = ojClass.getEnvironment();
        final ModifierList list = new ModifierList();
        final TypeName forOJClass = TypeName.forOJClass(ojClass2);
        list.add(ojModifier.toModifier());
        this.substance = new OJFieldSourceCode(environment, ojClass, new FieldDeclaration(list, forOJClass, s, null));
    }
    
    public OJField(final Environment environment, final OJClass ojClass, final FieldDeclaration fieldDeclaration) {
        this.substance = new OJFieldSourceCode(environment, ojClass, fieldDeclaration);
    }
    
    public OJField(final Environment environment, final OJClass ojClass, final EnumConstant enumConstant) {
        this.substance = new OJFieldSourceCode(environment, ojClass, enumConstant);
    }
    
    public OJField(final Environment environment, final OJClass ojClass, final FieldAccess fieldAccess) {
        this.substance = new OJFieldSourceCode(environment, ojClass, fieldAccess);
    }
    
    public static OJField forField(final Field field) {
        OJField value = (OJField) OJField.table.get(field);
        if (value == null) {
            value = new OJField(field);
            OJField.table.put(field, value);
        }
        return value;
    }
    
    public static OJField[] arrayForFields(final Field[] array) {
        final OJField[] array2 = new OJField[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = forField(array[i]);
        }
        return array2;
    }
    
    @Override
    public Signature signature() {
        return new Signature(this);
    }
    
    @Override
    public OJClass getDeclaringClass() {
        return this.substance.getDeclaringClass();
    }
    
    @Override
    public String getName() {
        return this.substance.getName();
    }
    
    public String getIdentifiableName() {
        return this.substance.getIdentifiableName();
    }
    
    @Override
    public OJModifier getModifiers() {
        return this.substance.getModifiers();
    }
    
    public OJClass getType() {
        return this.substance.getType();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o != null && o instanceof OJField) {
            final OJField ojField = (OJField)o;
            return this.getDeclaringClass() == ojField.getDeclaringClass() && this.getName().equals(ojField.getName()) && this.getType() == ojField.getType();
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    @Override
    public String toString() {
        return this.substance.toString();
    }
    
    @Override
    public Environment getEnvironment() {
        return new ClosedEnvironment(this.getDeclaringClass().getEnvironment());
    }
    
    public Object get(final Object o) throws IllegalArgumentException, IllegalAccessException {
        return this.substance.get(o);
    }
    
    public boolean getBoolean(final Object o) throws IllegalArgumentException, IllegalAccessException {
        return this.substance.getBoolean(o);
    }
    
    public byte getByte(final Object o) throws IllegalArgumentException, IllegalAccessException {
        return this.substance.getByte(o);
    }
    
    public char getChar(final Object o) throws IllegalArgumentException, IllegalAccessException {
        return this.substance.getChar(o);
    }
    
    public short getShort(final Object o) throws IllegalArgumentException, IllegalAccessException {
        return this.substance.getShort(o);
    }
    
    public int getInt(final Object o) throws IllegalArgumentException, IllegalAccessException {
        return this.substance.getInt(o);
    }
    
    public long getLong(final Object o) throws IllegalArgumentException, IllegalAccessException {
        return this.substance.getLong(o);
    }
    
    public float getFloat(final Object o) throws IllegalArgumentException, IllegalAccessException {
        return this.substance.getFloat(o);
    }
    
    public double getDouble(final Object o) throws IllegalArgumentException, IllegalAccessException {
        return this.substance.getDouble(o);
    }
    
    public void set(final Object o, final Object o2) throws IllegalArgumentException, IllegalAccessException {
        this.substance.set(o, o2);
    }
    
    public void setBoolean(final Object o, final boolean b) throws IllegalArgumentException, IllegalAccessException {
        this.substance.setBoolean(o, b);
    }
    
    public void setByte(final Object o, final byte b) throws IllegalArgumentException, IllegalAccessException {
        this.substance.setByte(o, b);
    }
    
    public void setChar(final Object o, final char c) throws IllegalArgumentException, IllegalAccessException {
        this.substance.setChar(o, c);
    }
    
    public void setShort(final Object o, final short n) throws IllegalArgumentException, IllegalAccessException {
        this.substance.setShort(o, n);
    }
    
    public void setInt(final Object o, final int n) throws IllegalArgumentException, IllegalAccessException {
        this.substance.setInt(o, n);
    }
    
    public void setLong(final Object o, final long n) throws IllegalArgumentException, IllegalAccessException {
        this.substance.setLong(o, n);
    }
    
    public void setFloat(final Object o, final float n) throws IllegalArgumentException, IllegalAccessException {
        this.substance.setFloat(o, n);
    }
    
    public void setDouble(final Object o, final double n) throws IllegalArgumentException, IllegalAccessException {
        this.substance.setDouble(o, n);
    }
    
    public boolean isEnumConstant() {
        return this.substance.isEnumConstant();
    }
    
    public boolean isExecutable() {
        return this.substance.isExecutable();
    }
    
    public boolean isAlterable() {
        return this.substance.isAlterable();
    }
    
    public final Field getByteCode() throws CannotExecuteException {
        return this.substance.getByteCode();
    }
    
    public final FieldDeclaration getSourceCode() throws CannotAlterException {
        return this.substance.getSourceCode();
    }
    
    public OJField getCopy() {
        try {
            if (this.substance instanceof OJFieldByteCode) {
                final Field byteCode = ((OJFieldByteCode)this.substance).getByteCode();
                final OJField ojField = (OJField)this.clone();
                ojField.substance = new OJFieldSourceCode(this.substance.getDeclaringClass().getEnvironment(), this.substance.getDeclaringClass(), new FieldDeclaration(new ModifierList(byteCode.getModifiers()), TypeName.forOJClass(OJClass.forClass(byteCode.getDeclaringClass())), byteCode.getName(), null));
                return ojField;
            }
            if (this.substance instanceof OJFieldSourceCode) {
                final OJField ojField2 = (OJField)this.clone();
                ojField2.substance = new OJFieldSourceCode(((OJFieldSourceCode)this.substance).getEnvironment(), this.substance.getDeclaringClass(), (FieldDeclaration)this.substance.getSourceCode().makeRecursiveCopy());
                return ojField2;
            }
        }
        catch (Exception obj) {
            System.err.println("Failed to copy " + this + ": " + obj);
            obj.printStackTrace();
        }
        return null;
    }
    
    public void setDeclaringClass(final OJClass declaringClass) throws CannotAlterException {
        this.substance.setDeclaringClass(declaringClass);
    }
    
    public final void setName(final String name) throws CannotAlterException {
        this.substance.setName(name);
    }
    
    public final void setModifiers(final int modifiers) throws CannotAlterException {
        this.substance.setModifiers(modifiers);
    }
    
    public final void setModifiers(final OJModifier ojModifier) throws CannotAlterException {
        this.setModifiers(ojModifier.toModifier());
    }
    
    public final void setType(final OJClass type) throws CannotAlterException {
        this.substance.setType(type);
    }
    
    static {
        OJField.table = new Hashtable();
    }
}
