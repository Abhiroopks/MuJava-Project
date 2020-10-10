// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.ConstructorInvocation;
import openjava.ptree.StatementList;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import openjava.ptree.TypeName;
import java.util.Hashtable;
import openjava.ptree.ParseTree;
import openjava.ptree.ParameterList;
import openjava.ptree.ConstructorDeclaration;

class OJConstructorSourceCode extends OJConstructorImp
{
    private static int idCounter;
    private int id;
    private OJClass declarer;
    private ConstructorDeclaration definition;
    private Environment env;
    
    OJConstructorSourceCode(final Environment env, final OJClass declarer, final ConstructorDeclaration definition) {
        this.declarer = declarer;
        this.definition = definition;
        this.env = env;
        this.id = OJConstructorSourceCode.idCounter++;
    }
    
    @Override
    public String toString() {
        final OJClass declaringClass = this.getDeclaringClass();
        if (declaringClass == null) {
            new StringBuilder().append("*").append(this.id).toString();
        }
        else {
            declaringClass.getName();
        }
        final StringBuffer sb = new StringBuffer();
        final String string = this.getModifiers().toString();
        if (!string.equals("")) {
            sb.append(string);
            sb.append(" ");
        }
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
    OJClass getDeclaringClass() {
        return this.declarer;
    }
    
    @Override
    String getName() {
        final OJClass declaringClass = this.getDeclaringClass();
        return (declaringClass == null) ? null : declaringClass.getName();
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
        return str + "()";
    }
    
    @Override
    OJModifier getModifiers() {
        return OJModifier.forParseTree(this.definition.getModifiers());
    }
    
    @Override
    OJClass[] getParameterTypes() {
        return this.arrayForParameters(this.definition.getParameters());
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
        final OJClass[] array = new OJClass[(list == null) ? 0 : list.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = Toolbox.forNameAnyway(this.env, list.get(i).getTypeSpecifier().toString());
        }
        return array;
    }
    
    private final OJClass[] arrayForTypeNames(final TypeName[] array) {
        final OJClass[] array2 = new OJClass[(array == null) ? 0 : array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = Toolbox.forNameAnyway(this.env, array[i].toString());
        }
        return array2;
    }
    
    @Override
    Object newInstance(final Object[] array) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CannotExecuteException {
        throw new CannotExecuteException("newInstance()");
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
    Constructor getByteCode() throws CannotExecuteException {
        throw new CannotExecuteException("getByteCode()");
    }
    
    @Override
    ConstructorDeclaration getSourceCode() throws CannotAlterException {
        return this.definition;
    }
    
    @Override
    StatementList getBody() throws CannotAlterException {
        return this.definition.getBody();
    }
    
    @Override
    ConstructorInvocation getTransference() throws CannotAlterException {
        return this.definition.getConstructorInvocation();
    }
    
    @Override
    void setDeclaringClass(final OJClass declarer) throws CannotAlterException {
        this.declarer = declarer;
    }
    
    @Override
    final void setModifiers(final int n) throws CannotAlterException {
        throw new CannotAlterException("setModifiers()");
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
    
    @Override
    ConstructorInvocation setTransference(final ConstructorInvocation constructorInvocation) throws CannotAlterException {
        final ConstructorInvocation constructorInvocation2 = this.definition.getConstructorInvocation();
        this.definition.setConstructorInvocation(constructorInvocation);
        return constructorInvocation2;
    }
    
    static {
        OJConstructorSourceCode.idCounter = 0;
    }
}
