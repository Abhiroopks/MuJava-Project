// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.lang.reflect.InvocationTargetException;
import openjava.ptree.Expression;
import openjava.ptree.Variable;
import openjava.ptree.ExpressionList;
import openjava.ptree.ParseTree;
import openjava.ptree.ConstructorDeclaration;
import openjava.ptree.ModifierList;
import openjava.ptree.ParameterList;
import openjava.ptree.StatementList;
import openjava.ptree.ConstructorInvocation;
import java.lang.reflect.Constructor;
import java.util.Hashtable;

public final class OJConstructor implements OJMember
{
    private OJConstructorImp substance;
    private static Hashtable table;
    
    OJConstructor(final Constructor constructor) {
        this.substance = new OJConstructorByteCode(constructor);
    }
    
    public OJConstructor(final OJClass ojClass, final OJModifier ojModifier, final OJClass[] array, final OJClass[] array2, final ConstructorInvocation constructorInvocation, final StatementList list) {
        this(ojClass, ojModifier, Toolbox.generateParameters(array), array2, constructorInvocation, list);
    }
    
    public OJConstructor(final OJClass ojClass, final OJModifier ojModifier, final OJClass[] array, final String[] array2, final OJClass[] array3, final ConstructorInvocation constructorInvocation, final StatementList list) {
        this(ojClass, ojModifier, Toolbox.generateParameters(array, array2), array3, constructorInvocation, list);
    }
    
    public OJConstructor(final OJClass ojClass, final OJModifier ojModifier, final ParameterList list, final OJClass[] array, final ConstructorInvocation constructorInvocation, final StatementList list2) {
        final Environment environment = ojClass.getEnvironment();
        final ModifierList list3 = new ModifierList();
        list3.add(ojModifier.toModifier());
        this.substance = new OJConstructorSourceCode(environment, ojClass, new ConstructorDeclaration(list3, Environment.toSimpleName(ojClass.getName()), list, Toolbox.TNsForOJClasses(array), constructorInvocation, list2));
    }
    
    public OJConstructor(final Environment environment, final OJClass ojClass, final ConstructorDeclaration constructorDeclaration) {
        this.substance = new OJConstructorSourceCode(environment, ojClass, constructorDeclaration);
    }
    
    public static OJConstructor forConstructor(final Constructor constructor) {
        if (constructor == null) {
            return null;
        }
        OJConstructor value = (OJConstructor) OJConstructor.table.get(constructor);
        if (value == null) {
            value = new OJConstructor(constructor);
            OJConstructor.table.put(constructor, value);
        }
        return value;
    }
    
    public static OJConstructor[] arrayForConstructors(final Constructor[] array) {
        final OJConstructor[] array2 = new OJConstructor[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = forConstructor(array[i]);
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
    
    public OJClass[] getParameterTypes() {
        return this.substance.getParameterTypes();
    }
    
    public OJClass[] getExceptionTypes() {
        return this.substance.getExceptionTypes();
    }
    
    public ParseTree getSuffix(final String s) {
        return this.substance.getSuffix(s);
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
    
    @Override
    public boolean equals(final Object o) {
        if (o != null && o instanceof OJConstructor) {
            final OJConstructor ojConstructor = (OJConstructor)o;
            return this.getDeclaringClass() == ojConstructor.getDeclaringClass() && this.compareParameters(ojConstructor);
        }
        return false;
    }
    
    private boolean compareParameters(final OJConstructor ojConstructor) {
        return this.compareParameters(ojConstructor.getParameterTypes());
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
    
    public Object newInstance(final Object[] array) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CannotExecuteException {
        return this.substance.newInstance(array);
    }
    
    public final boolean isExecutable() {
        return this.substance.isExecutable();
    }
    
    public final boolean isAlterable() {
        return this.substance.isAlterable();
    }
    
    public final Constructor getByteCode() throws CannotExecuteException {
        return this.substance.getByteCode();
    }
    
    public final ConstructorDeclaration getSourceCode() throws CannotAlterException {
        return this.substance.getSourceCode();
    }
    
    public final StatementList getBody() throws CannotAlterException {
        return this.substance.getBody();
    }
    
    public final ConstructorInvocation getTransference() throws CannotAlterException {
        return this.substance.getTransference();
    }
    
    void setDeclaringClass(final OJClass declaringClass) throws CannotAlterException {
        this.substance.setDeclaringClass(declaringClass);
    }
    
    public final void setModifiers(final int modifiers) throws CannotAlterException {
        this.substance.setModifiers(modifiers);
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
    
    public final ConstructorInvocation setTransference(final ConstructorInvocation transference) throws CannotAlterException {
        return this.substance.setTransference(transference);
    }
    
    public final StatementList setBody(final StatementList body) throws CannotAlterException {
        return this.substance.setBody(body);
    }
    
    static {
        OJConstructor.table = new Hashtable();
    }
}
