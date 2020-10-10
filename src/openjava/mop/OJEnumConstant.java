// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.MemberDeclarationList;
import openjava.ptree.ExpressionList;
import openjava.ptree.TypeName;
import openjava.ptree.ModifierList;
import java.lang.reflect.Field;
import openjava.ptree.EnumConstant;
import java.util.Hashtable;

public class OJEnumConstant implements OJMember, Cloneable
{
    private OJEnumConstantImp substance;
    private static Hashtable table;
    
    public OJEnumConstant(final Environment environment, final OJClass ojClass, final EnumConstant enumConstant) {
        this.substance = new OJEnumConstantSourceCode(environment, ojClass, enumConstant);
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
        if (o != null && o instanceof OJEnumConstant) {
            final OJEnumConstant ojEnumConstant = (OJEnumConstant)o;
            return this.getDeclaringClass() == ojEnumConstant.getDeclaringClass() && this.getName().equals(ojEnumConstant.getName()) && this.getType() == ojEnumConstant.getType();
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
    
    public boolean isExecutable() {
        return this.substance.isExecutable();
    }
    
    public boolean isAlterable() {
        return this.substance.isAlterable();
    }
    
    public final Field getByteCode() throws CannotExecuteException {
        return this.substance.getByteCode();
    }
    
    public final EnumConstant getSourceCode() throws CannotAlterException {
        return this.substance.getSourceCode();
    }
    
    public OJEnumConstant getCopy() {
        try {
            if (this.substance instanceof OJEnumConstantByteCode) {
                final Field byteCode = ((OJEnumConstantByteCode)this.substance).getByteCode();
                final OJEnumConstant ojEnumConstant = (OJEnumConstant)this.clone();
                ojEnumConstant.substance = new OJEnumConstantSourceCode(this.substance.getDeclaringClass().getEnvironment(), this.substance.getDeclaringClass(), new EnumConstant(new ModifierList(byteCode.getModifiers()), byteCode.getName(), null, null, TypeName.forOJClass(OJClass.forClass(byteCode.getDeclaringClass())).getName()));
                return ojEnumConstant;
            }
            if (this.substance instanceof OJEnumConstantSourceCode) {
                final OJEnumConstant ojEnumConstant2 = (OJEnumConstant)this.clone();
                ojEnumConstant2.substance = new OJEnumConstantSourceCode(((OJEnumConstantSourceCode)this.substance).getEnvironment(), this.substance.getDeclaringClass(), (EnumConstant)this.substance.getSourceCode().makeRecursiveCopy());
                return ojEnumConstant2;
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
        OJEnumConstant.table = new Hashtable();
    }
}
