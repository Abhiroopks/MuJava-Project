// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.ModifierList;
import openjava.ptree.StatementList;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import openjava.ptree.ParameterList;
import openjava.ptree.ParseTree;
import openjava.ptree.TypeName;
import openjava.ptree.MethodCall;
import openjava.ptree.MethodDeclaration;

class OJMethodSourceCode extends OJMethodImp
{
    private static int idCounter;
    private int id;
    private OJClass declarer;
    private MethodDeclaration definition;
    private Environment env;
    private MethodCall definitionMethodCall;
    private OJClass[] ptypeCache;
    private TypeName[] paramtypes;
    
    OJMethodSourceCode(final Environment env, final OJClass declarer, final MethodDeclaration definition) {
        this.definition = null;
        this.definitionMethodCall = null;
        this.ptypeCache = null;
        this.paramtypes = null;
        this.declarer = declarer;
        this.definition = definition;
        this.env = env;
        this.id = OJMethodSourceCode.idCounter++;
    }
    
    OJMethodSourceCode(final Environment env, final OJClass declarer, final MethodCall definitionMethodCall) {
        this.definition = null;
        this.definitionMethodCall = null;
        this.ptypeCache = null;
        this.paramtypes = null;
        this.declarer = declarer;
        this.definitionMethodCall = definitionMethodCall;
        this.env = env;
        this.id = OJMethodSourceCode.idCounter++;
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
        sb.append(this.getReturnType().getName());
        sb.append(" ");
        sb.append(str);
        sb.append(".");
        sb.append(this.getName());
        sb.append("(");
        final OJClass[] parameterTypes = this.getParameterTypes();
        if (parameterTypes.length != 0) {
            sb.append(parameterTypes[0].getName());
        }
        for (int i = 1; i < parameterTypes.length; ++i) {
            sb.append(",");
            sb.append(parameterTypes[i].getName());
        }
        sb.append(")");
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
        return this.definitionMethodCall.getName();
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
        return str + "." + this.getName() + "()";
    }
    
    @Override
    OJModifier getModifiers() {
        return OJModifier.forParseTree(this.definition.getModifiers());
    }
    
    @Override
    OJClass getReturnType() {
        if (this.definition != null) {
            return Toolbox.forNameAnyway(this.env, this.definition.getReturnType().toString());
        }
        return Toolbox.forNameAnyway(this.env, this.definitionMethodCall.getReferenceType().toString());
    }
    
    private boolean isPtypeCacheDirty(final TypeName[] array) {
        if (this.ptypeCache == null) {
            return true;
        }
        if (array.length != this.paramtypes.length) {
            return true;
        }
        for (int i = 0; i < array.length; ++i) {
            if (!array[i].equals(this.paramtypes[i])) {
                return true;
            }
        }
        return false;
    }
    
    private void refleshPtypeCache() {
        final ParameterList parameters = this.definition.getParameters();
        final int size = parameters.size();
        final TypeName[] paramtypes = new TypeName[size];
        for (int i = 0; i < size; ++i) {
            paramtypes[i] = parameters.get(i).getTypeSpecifier();
        }
        if (this.isPtypeCacheDirty(paramtypes)) {
            this.ptypeCache = this.arrayForParameters(parameters);
            this.paramtypes = paramtypes;
        }
    }
    
    @Override
    OJClass[] getParameterTypes() {
        this.refleshPtypeCache();
        final OJClass[] array = new OJClass[this.ptypeCache.length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.ptypeCache[i];
        }
        return array;
    }
    
    String[] getParameters() {
        final ParameterList parameters = this.definition.getParameters();
        final String[] array = new String[parameters.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = parameters.get(i).getVariable().toString();
        }
        return array;
    }
    
    @Override
    OJClass[] getExceptionTypes() {
        return this.arrayForTypeNames(this.definition.getThrows());
    }
    
    @Override
    ParseTree getSuffix(final String key) {
        final Hashtable suffixes = this.definition.getSuffixes();
        if (suffixes == null) {
            return null;
        }
        return (ParseTree) suffixes.get(key);
    }
    
    private final OJClass[] arrayForParameters(final ParameterList list) {
        final OJClass[] array = new OJClass[list.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = Toolbox.forNameAnyway(this.env, list.get(i).getTypeSpecifier().toString());
        }
        return array;
    }
    
    private final OJClass[] arrayForTypeNames(final TypeName[] array) {
        final OJClass[] array2 = new OJClass[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = Toolbox.forNameAnyway(this.env, array[i].toString());
        }
        return array2;
    }
    
    @Override
    Environment getEnvironment() {
        final ClosedEnvironment closedEnvironment = new ClosedEnvironment(this.getDeclaringClass().getEnvironment());
        final OJClass[] parameterTypes = this.getParameterTypes();
        final String[] parameters = this.getParameters();
        for (int i = 0; i < parameterTypes.length; ++i) {
            closedEnvironment.bindVariable(parameters[i], parameterTypes[i]);
        }
        return closedEnvironment;
    }
    
    @Override
    Object invoke(final Object o, final Object[] array) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, CannotExecuteException {
        throw new CannotExecuteException("invoke()");
    }
    
    @Override
    boolean isAlterable() {
        return true;
    }
    
    @Override
    boolean isExecutable() {
        return false;
    }
    
    @Override
    Method getByteCode() throws CannotExecuteException {
        throw new CannotExecuteException("getByteCode()");
    }
    
    @Override
    MethodDeclaration getSourceCode() throws CannotAlterException {
        return this.definition;
    }
    
    @Override
    StatementList getBody() throws CannotAlterException {
        return this.definition.getBody();
    }
    
    @Override
    void setDeclaringClass(final OJClass declarer) throws CannotAlterException {
        this.declarer = declarer;
    }
    
    @Override
    final void setName(final String name) throws CannotAlterException {
        this.definition.setName(name);
    }
    
    @Override
    final void setModifiers(final int n) throws CannotAlterException {
        this.definition.setModifiers(new ModifierList(n));
    }
    
    @Override
    final void setReturnType(final OJClass ojClass) throws CannotAlterException {
        this.definition.setReturnType(TypeName.forOJClass(ojClass));
    }
    
    @Override
    final void setExceptionTypes(final OJClass[] array) throws CannotAlterException {
        this.definition.setThrows(Toolbox.TNsForOJClasses(array));
    }
    
    @Override
    StatementList setBody(final StatementList body) throws CannotAlterException {
        final StatementList body2 = this.definition.getBody();
        this.definition.setBody(body);
        return body2;
    }
    
    static {
        OJMethodSourceCode.idCounter = 0;
    }
}
