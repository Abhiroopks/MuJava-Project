// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

public final class Signature
{
    public static final int CLASS = 0;
    public static final int FIELD = 1;
    public static final int METHOD = 2;
    public static final int CONSTRUCTOR = 3;
    public static final int ENUMCONSTANT = 4;
    private int _kind;
    private OJClass _returnType;
    private String _name;
    private OJClass[] _parameters;
    private String strCache;
    private int hashCodeCache;
    
    public Signature(final String name) {
        this.strCache = null;
        this.hashCodeCache = -1;
        this._kind = 1;
        this._name = name;
        this._parameters = null;
    }
    
    public Signature(final String name, OJClass[] array) {
        this.strCache = null;
        this.hashCodeCache = -1;
        if (array == null) {
            array = new OJClass[0];
        }
        this._kind = 2;
        this._returnType = null;
        this._name = name;
        this._parameters = array.clone();
    }
    
    public Signature(OJClass[] array) {
        this.strCache = null;
        this.hashCodeCache = -1;
        if (array == null) {
            array = new OJClass[0];
        }
        this._kind = 3;
        this._returnType = null;
        this._name = null;
        this._parameters = array.clone();
    }
    
    public Signature(final OJClass ojClass) {
        this.strCache = null;
        this.hashCodeCache = -1;
        this._kind = 0;
        this._name = ojClass.getName();
        this._parameters = null;
    }
    
    public Signature(final OJField ojField) {
        this.strCache = null;
        this.hashCodeCache = -1;
        this._kind = 1;
        this._name = ojField.getName();
        this._parameters = null;
    }
    
    public Signature(final OJEnumConstant ojEnumConstant) {
        this.strCache = null;
        this.hashCodeCache = -1;
        this._kind = 4;
        this._name = ojEnumConstant.getName();
        this._parameters = null;
    }
    
    public Signature(final OJMethod ojMethod) {
        this.strCache = null;
        this.hashCodeCache = -1;
        this._kind = 2;
        this._name = ojMethod.getName();
        this._parameters = ojMethod.getParameterTypes().clone();
    }
    
    public Signature(final OJConstructor ojConstructor) {
        this.strCache = null;
        this.hashCodeCache = -1;
        this._kind = 3;
        this._name = null;
        this._parameters = ojConstructor.getParameterTypes().clone();
    }
    
    protected OJClass[] parameterTypes() {
        return this._parameters;
    }
    
    public int kind() {
        return this._kind;
    }
    
    @Override
    public String toString() {
        if (this.strCache == null) {
            this.strCache = this.getStringValue();
        }
        return this.strCache;
    }
    
    private String getStringValue() {
        final StringBuffer sb = new StringBuffer();
        switch (this.kind()) {
            case 0: {
                sb.append("class ");
                sb.append(this.getName());
                break;
            }
            case 1: {
                sb.append("field ");
                sb.append(this.getName());
                break;
            }
            case 2: {
                sb.append("method ");
                sb.append(this.getName());
                sb.append("(");
                if (this.parameterTypes().length != 0) {
                    sb.append(this.parameterTypes()[0]);
                }
                for (int i = 1; i < this.parameterTypes().length; ++i) {
                    sb.append(",");
                    sb.append(this.parameterTypes()[i].toString());
                }
                sb.append(")");
                break;
            }
            case 3: {
                sb.append("constructor ");
                sb.append("(");
                if (this.parameterTypes().length != 0) {
                    sb.append(this.parameterTypes()[0]);
                }
                for (int j = 1; j < this.parameterTypes().length; ++j) {
                    sb.append(",");
                    sb.append(this.parameterTypes()[j].toString());
                }
                sb.append(")");
                break;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        if (this.hashCodeCache == -1) {
            this.hashCodeCache = this.toString().hashCode();
        }
        return this.hashCodeCache;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof Signature && this.toString().equals(o.toString());
    }
    
    private OJClass getReturnType() {
        return this._returnType;
    }
    
    public String getName() {
        return this._name;
    }
    
    public OJClass[] getParameterTypes() {
        if (this.parameterTypes() == null) {
            return null;
        }
        return this.parameterTypes().clone();
    }
    
    public boolean equals(final Signature signature) {
        if (signature == null) {
            return false;
        }
        if (this.kind() != signature.kind()) {
            return false;
        }
        switch (this.kind()) {
            case 0: {
                return false;
            }
            case 1: {
                return false;
            }
            case 2: {
                return false;
            }
            case 3: {
                return this.compareParams(signature.parameterTypes());
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean strictlyEquals(final Signature signature) {
        return this == signature;
    }
    
    private final boolean compareParams(final OJClass[] array) {
        if (array == null) {
            return false;
        }
        if (this.parameterTypes().length != array.length) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (this.parameterTypes()[i] != array[i]) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isClass() {
        return this.kind() == 0;
    }
    
    public boolean isConstructor() {
        return this.kind() == 3;
    }
    
    public boolean isField() {
        return this.kind() == 1;
    }
    
    public boolean isMethod() {
        return this.kind() == 2;
    }
    
    public boolean isEnumConstant() {
        return this.kind() == 4;
    }
    
    public static OJClass commonBaseType(final OJClass ojClass, final OJClass ojClass2) {
        if (ojClass.isAssignableFrom(ojClass2)) {
            return ojClass;
        }
        if (ojClass2.isAssignableFrom(ojClass)) {
            return ojClass2;
        }
        return commonBaseType(ojClass.getSuperclass(), ojClass2.getSuperclass());
    }
    
    public static OJClass[] commonBaseTypes(final OJClass[] array, final OJClass[] array2) {
        final OJClass[] array3 = new OJClass[array.length];
        for (int i = 0; i < array.length; ++i) {
            array3[i] = commonBaseType(array[i], array2[i]);
        }
        return array3;
    }
}
