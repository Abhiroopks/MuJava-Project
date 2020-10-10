// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.lang.reflect.InvocationTargetException;
import openjava.ptree.ParseTree;
import openjava.ptree.Expression;
import openjava.ptree.Variable;
import openjava.ptree.ExpressionList;
import openjava.ptree.MethodCall;
import openjava.ptree.MethodDeclaration;
import openjava.ptree.TypeParameterList;
import openjava.ptree.TypeName;
import openjava.ptree.ModifierList;
import openjava.ptree.ParameterList;
import openjava.ptree.StatementList;
import java.lang.reflect.Method;
import java.util.Hashtable;

public final class OJMethod implements OJMember
{
    private OJMethodImp substance;
    private static Hashtable table;
    
    OJMethod(final Method method) {
        this.substance = new OJMethodByteCode(method);
    }
    
    public OJMethod(final OJClass ojClass, final OJModifier ojModifier, final OJClass ojClass2, final String s, final OJClass[] array, final OJClass[] array2, final StatementList list) {
        this(ojClass, ojModifier, ojClass2, s, Toolbox.generateParameters(array), array2, list);
    }
    
    public OJMethod(final OJClass ojClass, final OJModifier ojModifier, final OJClass ojClass2, final String s, final OJClass[] array, final String[] array2, final OJClass[] array3, final StatementList list) {
        this(ojClass, ojModifier, ojClass2, s, Toolbox.generateParameters(array, array2), array3, list);
    }
    
    public OJMethod(final OJClass ojClass, final OJModifier ojModifier, final OJClass ojClass2, final String s, final ParameterList list, final OJClass[] array, final StatementList list2) {
        final Environment environment = ojClass.getEnvironment();
        final ModifierList list3 = new ModifierList();
        list3.add(ojModifier.toModifier());
        this.substance = new OJMethodSourceCode(environment, ojClass, new MethodDeclaration(list3, TypeName.forOJClass(ojClass2), s, list, Toolbox.TNsForOJClasses(array), list2, new TypeParameterList()));
    }
    
    public OJMethod(final Environment environment, final OJClass ojClass, final MethodDeclaration methodDeclaration) {
        this.substance = new OJMethodSourceCode(environment, ojClass, methodDeclaration);
    }
    
    public OJMethod(final Environment environment, final OJClass ojClass, final MethodCall methodCall) {
        this.substance = new OJMethodSourceCode(environment, ojClass, methodCall);
    }
    
    public static OJMethod makePrototype(final OJMethod ojMethod) {
        return new OJMethod(ojMethod.getDeclaringClass(), ojMethod.getModifiers(), ojMethod.getReturnType(), ojMethod.getName(), ojMethod.getParameterTypes(), ojMethod.getExceptionTypes(), null);
    }
    
    public static OJMethod forMethod(final Method method) {
        if (method == null) {
            return null;
        }
        OJMethod value = (OJMethod) OJMethod.table.get(method);
        if (value == null) {
            value = new OJMethod(method);
            OJMethod.table.put(method, value);
        }
        return value;
    }
    
    public static OJMethod[] arrayForMethods(final Method[] array) {
        final OJMethod[] array2 = new OJMethod[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = forMethod(array[i]);
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
    
    public OJClass getReturnType() {
        return this.substance.getReturnType();
    }
    
    public OJClass[] getParameterTypes() {
        return this.substance.getParameterTypes();
    }
    
    public OJClass[] getExceptionTypes() {
        return this.substance.getExceptionTypes();
    }
    
    public ExpressionList getParameterVariables() throws CannotAlterException {
        final ParameterList parameters = this.getSourceCode().getParameters();
        final ExpressionList list = new ExpressionList();
        for (int i = 0; i < parameters.size(); ++i) {
            list.add(new Variable(parameters.get(i).getVariable()));
        }
        return list;
    }
    
    public String[] getParameters() throws CannotAlterException {
        final ParameterList parameters = this.getSourceCode().getParameters();
        final String[] array = new String[parameters.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = parameters.get(i).getVariable().toString();
        }
        return array;
    }
    
    public ParseTree getSuffix(final String s) {
        return this.substance.getSuffix(s);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o != null && o instanceof OJMethod) {
            final OJMethod ojMethod = (OJMethod)o;
            return this.getDeclaringClass() == ojMethod.getDeclaringClass() && this.getName().equals(ojMethod.getName()) && this.compareParameters(ojMethod);
        }
        return false;
    }
    
    private boolean compareParameters(final OJMethod ojMethod) {
        return this.compareParameters(ojMethod.getParameterTypes());
    }
    
    private boolean compareParameters(final OJClass[] array) {
        final OJClass[] parameterTypes = this.getParameterTypes();
        if (parameterTypes.length != array.length) {
            return false;
        }
        for (int i = 0; i < parameterTypes.length; ++i) {
            if (parameterTypes[i] != array[i]) {
                return false;
            }
        }
        return true;
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
        return this.substance.getEnvironment();
    }
    
    public Object invoke(final Object o, final Object[] array) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, CannotExecuteException {
        return this.substance.invoke(o, array);
    }
    
    public final boolean isExecutable() {
        return this.substance.isExecutable();
    }
    
    public final boolean isAlterable() {
        return this.substance.isAlterable();
    }
    
    public final Method getByteCode() throws CannotExecuteException {
        return this.substance.getByteCode();
    }
    
    public final MethodDeclaration getSourceCode() throws CannotAlterException {
        return this.substance.getSourceCode();
    }
    
    public final StatementList getBody() throws CannotAlterException {
        return this.substance.getBody();
    }
    
    void setDeclaringClass(final OJClass declaringClass) throws CannotAlterException {
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
    
    public final void setReturnType(final OJClass returnType) throws CannotAlterException {
        this.substance.setReturnType(returnType);
    }
    
    public final void setExceptionTypes(final OJClass[] exceptionTypes) throws CannotAlterException {
        this.substance.setExceptionTypes(exceptionTypes);
    }
    
    public final void addExceptionType(final OJClass ojClass) throws CannotAlterException {
        final OJClass[] exceptionTypes = this.getExceptionTypes();
        final OJClass[] exceptionTypes2 = new OJClass[exceptionTypes.length + 1];
        System.arraycopy(exceptionTypes, 0, exceptionTypes2, 0, exceptionTypes.length);
        exceptionTypes2[exceptionTypes.length] = ojClass;
        this.setExceptionTypes(exceptionTypes2);
    }
    
    public final StatementList setBody(final StatementList body) throws CannotAlterException {
        return this.substance.setBody(body);
    }
    
    static {
        OJMethod.table = new Hashtable();
    }
}
