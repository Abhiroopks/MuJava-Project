// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.EnumConstant;
import java.lang.reflect.Field;

abstract class OJEnumConstantImp
{
    @Override
    public abstract String toString();
    
    abstract OJClass getDeclaringClass();
    
    abstract String getName();
    
    abstract String getIdentifiableName();
    
    abstract OJModifier getModifiers();
    
    abstract OJClass getType();
    
    abstract Object get(final Object p0) throws IllegalArgumentException, IllegalAccessException;
    
    abstract boolean getBoolean(final Object p0) throws IllegalArgumentException, IllegalAccessException;
    
    abstract byte getByte(final Object p0) throws IllegalArgumentException, IllegalAccessException;
    
    abstract char getChar(final Object p0) throws IllegalArgumentException, IllegalAccessException;
    
    abstract short getShort(final Object p0) throws IllegalArgumentException, IllegalAccessException;
    
    abstract int getInt(final Object p0) throws IllegalArgumentException, IllegalAccessException;
    
    abstract long getLong(final Object p0) throws IllegalArgumentException, IllegalAccessException;
    
    abstract float getFloat(final Object p0) throws IllegalArgumentException, IllegalAccessException;
    
    abstract double getDouble(final Object p0) throws IllegalArgumentException, IllegalAccessException;
    
    abstract void set(final Object p0, final Object p1) throws IllegalArgumentException, IllegalAccessException;
    
    abstract void setBoolean(final Object p0, final boolean p1) throws IllegalArgumentException, IllegalAccessException;
    
    abstract void setByte(final Object p0, final byte p1) throws IllegalArgumentException, IllegalAccessException;
    
    abstract void setChar(final Object p0, final char p1) throws IllegalArgumentException, IllegalAccessException;
    
    abstract void setShort(final Object p0, final short p1) throws IllegalArgumentException, IllegalAccessException;
    
    abstract void setInt(final Object p0, final int p1) throws IllegalArgumentException, IllegalAccessException;
    
    abstract void setLong(final Object p0, final long p1) throws IllegalArgumentException, IllegalAccessException;
    
    abstract void setFloat(final Object p0, final float p1) throws IllegalArgumentException, IllegalAccessException;
    
    abstract void setDouble(final Object p0, final double p1) throws IllegalArgumentException, IllegalAccessException;
    
    abstract boolean isExecutable();
    
    abstract boolean isAlterable();
    
    abstract Field getByteCode() throws CannotExecuteException;
    
    abstract EnumConstant getSourceCode() throws CannotAlterException;
    
    abstract void setDeclaringClass(final OJClass p0) throws CannotAlterException;
    
    abstract void setName(final String p0) throws CannotAlterException;
    
    abstract void setModifiers(final int p0) throws CannotAlterException;
    
    abstract void setType(final OJClass p0) throws CannotAlterException;
}
