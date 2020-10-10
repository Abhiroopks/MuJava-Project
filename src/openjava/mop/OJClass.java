// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import openjava.syntax.SyntaxRule;
import openjava.ptree.CastExpression;
import openjava.ptree.VariableDeclaration;
import openjava.ptree.ArrayAccess;
import openjava.ptree.ArrayAllocationExpression;
import openjava.ptree.AllocationExpression;
import openjava.ptree.MethodCall;
import openjava.ptree.AssignmentExpression;
import openjava.ptree.FieldAccess;
import openjava.ptree.TypeName;
import openjava.ptree.ParseTreeException;
import openjava.ptree.util.ParseTreeVisitor;
import openjava.ptree.util.TypeNameQualifier;
import openjava.ptree.ParseTree;
import openjava.ptree.StatementList;
import openjava.ptree.Statement;
import openjava.ptree.util.PartialParser;
import openjava.ptree.Expression;
import java.lang.reflect.InvocationTargetException;
import openjava.tools.DebugOut;
import openjava.ptree.ClassDeclaration;

public class OJClass implements OJMember
{
    private OJClassImp substance;
    
    public OJClass(final Environment environment, final OJClass ojClass, final ClassDeclaration classDeclaration) {
        this.substance = new OJClassSourceCode(this, environment, ojClass, classDeclaration);
    }
    
    public OJClass(final Class clazz, final MetaInfo metaInfo) {
        this.substance = new OJClassByteCode(clazz, metaInfo);
    }
    
    private OJClass(final OJClass ojClass) {
        this.substance = new OJClassArray(ojClass);
    }
    
    OJClass() {
        this.substance = new OJClassNull();
    }
    
    public static OJClass forName(String nameForJavaClassName) throws OJClassNotFoundException {
        DebugOut.println("OJClass.forName(\"" + nameForJavaClassName.toString() + "\")");
        nameForJavaClassName = nameForJavaClassName(nameForJavaClassName);
        final OJClass lookupClass = OJSystem.env.lookupClass(nameForJavaClassName);
        if (lookupClass != null) {
            return lookupClass;
        }
        if (isArrayName(nameForJavaClassName)) {
            final OJClass ojClass = new OJClass(forName(stripBrackets(nameForJavaClassName)));
            OJSystem.env.record(nameForJavaClassName, ojClass);
            return ojClass;
        }
        final OJClass lookupFromByteCode = lookupFromByteCode(nameToJavaClassName(nameForJavaClassName));
        if (lookupFromByteCode != null) {
            OJSystem.env.record(nameForJavaClassName, lookupFromByteCode);
            return lookupFromByteCode;
        }
        throw new OJClassNotFoundException(nameForJavaClassName);
    }
    
    public static OJClass forName(String nameForJavaClassName, final Environment environment) throws OJClassNotFoundException {
        DebugOut.println("OJClass.forName(\"" + nameForJavaClassName.toString() + "\")");
        nameForJavaClassName = nameForJavaClassName(nameForJavaClassName);
        final OJClass lookupClass = environment.lookupClass(nameForJavaClassName);
        if (lookupClass != null) {
            return lookupClass;
        }
        if (isArrayName(nameForJavaClassName)) {
            final OJClass ojClass = new OJClass(forName(stripBrackets(nameForJavaClassName)));
            environment.record(nameForJavaClassName, ojClass);
            return ojClass;
        }
        final OJClass lookupFromByteCode = lookupFromByteCode(nameToJavaClassName(nameForJavaClassName));
        if (lookupFromByteCode != null) {
            environment.record(nameForJavaClassName, lookupFromByteCode);
            return lookupFromByteCode;
        }
        throw new OJClassNotFoundException(nameForJavaClassName);
    }
    
    private static final OJClass lookupFromByteCode(final String className) {
        OJClass ojClass;
        try {
            ojClass = forClass(Class.forName(className));
            if (ojClass != null) {
                OJSystem.env.record(className, ojClass);
                return ojClass;
            }
        }
        catch (ClassNotFoundException ex) {
            final int lastIndex = className.lastIndexOf(46);
            if (lastIndex == -1) {
                return null;
            }
            ojClass = lookupFromByteCode(replaceDotWithDoller(className, lastIndex));
        }
        return ojClass;
    }
    
    private static final String replaceDotWithDoller(final String s, final int endIndex) {
        return s.substring(0, endIndex) + '$' + s.substring(endIndex + 1);
    }
    
    private static final boolean isArrayName(final String s) {
        return s.startsWith("[") || s.endsWith("[]");
    }
    
    private static final String stripBrackets(final String s) {
        return s.substring(0, s.length() - 2);
    }
    
    private static final String nameForJavaClassName(final String s) {
        return Toolbox.nameForJavaClassName(s);
    }
    
    private static final String nameToJavaClassName(final String s) {
        return Toolbox.nameToJavaClassName(s);
    }
    
    static OJClass[] arrayForClasses(final Class[] array) {
        final OJClass[] array2 = new OJClass[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = forClass(array[i]);
        }
        return array2;
    }
    
    static Class[] toClasses(final OJClass[] array) {
        final Class[] array2 = new Class[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = array[i].getCompatibleJavaClass();
        }
        return array2;
    }
    
    public static OJClass forClass(final Class clazz) {
        if (clazz == null) {
            return null;
        }
        final String nameForJavaClassName = nameForJavaClassName(clazz.getName());
        final OJClass lookupClass = OJSystem.env.lookupClass(nameForJavaClassName);
        if (lookupClass != null) {
            return lookupClass;
        }
        if (isArrayName(nameForJavaClassName)) {
            try {
                final OJClass ojClass = new OJClass(forName(stripBrackets(nameForJavaClassName)));
                OJSystem.env.record(nameForJavaClassName, ojClass);
                return ojClass;
            }
            catch (Exception obj) {
                System.err.println("OJClass.forClass(" + nameForJavaClassName + ") : " + obj);
            }
        }
        final OJClass lookupFromMetaInfo = lookupFromMetaInfo(clazz);
        OJSystem.env.record(nameForJavaClassName, lookupFromMetaInfo);
        return lookupFromMetaInfo;
    }
    
    private static final OJClass lookupFromMetaInfo(final Class clazz) {
        final MetaInfo metaInfo = new MetaInfo(clazz);
        final String value = metaInfo.get("instantiates");
        DebugOut.println(clazz + " is an instance of " + value);
        try {
            return (OJClass)Class.forName(value).getConstructor(Class.class, MetaInfo.class).newInstance(clazz, metaInfo);
        }
        catch (ClassNotFoundException ex) {
            System.err.println("metaclass " + value + " for " + clazz + " not found." + " substituted by default metaclass.");
            return new OJClass(clazz, new MetaInfo(clazz.getName()));
        }
        catch (Exception obj) {
            System.err.println("metaclass " + value + " doesn't provide" + " proper constructor for bytecode." + " substituted by default metaclass. : " + obj);
            return new OJClass(clazz, new MetaInfo(clazz.getName()));
        }
    }
    
    public static OJClass forParseTree(final Environment environment, final OJClass ojClass, final ClassDeclaration classDeclaration) throws AmbiguousClassesException, ClassNotFoundException {
        String s;
        if (ojClass == null) {
            s = environment.toQualifiedName(classDeclaration.getName());
        }
        else {
            s = environment.currentClassName() + "." + classDeclaration.getName();
        }
        Class<?> clazz;
        if (classDeclaration.getMetaclass() != null) {
            clazz = Class.forName(environment.toQualifiedName(classDeclaration.getMetaclass()));
        }
        else {
            clazz = (Class<?>)OJSystem.getMetabind(s);
        }
        OJClass ojClass2;
        try {
            ojClass2 = (OJClass)clazz.getConstructor(Environment.class, OJClass.class, ClassDeclaration.class).newInstance(environment, ojClass, classDeclaration);
        }
        catch (NoSuchMethodException obj) {
            System.err.println("errors during gererating a metaobject for " + classDeclaration.getName() + " : " + obj);
            ojClass2 = new OJClass(environment, ojClass, classDeclaration);
        }
        catch (InvocationTargetException ex) {
            System.err.println("errors during gererating a metaobject for " + classDeclaration.getName() + " : " + ex.getTargetException());
            ex.printStackTrace();
            ojClass2 = new OJClass(environment, ojClass, classDeclaration);
        }
        catch (Exception obj2) {
            System.err.println("errors during gererating a metaobject for " + classDeclaration.getName() + " : " + obj2);
            ojClass2 = new OJClass(environment, ojClass, classDeclaration);
        }
        if (OJSystem.env.lookupClass(s) != null) {
            throw new AmbiguousClassesException(s);
        }
        OJSystem.env.record(s, ojClass2);
        return ojClass2;
    }
    
    protected static final Expression makeExpression(final Environment environment, final String s) throws MOPException {
        return PartialParser.makeExpression(environment, s);
    }
    
    protected final Expression makeExpression(final String s) throws MOPException {
        return makeExpression(this.getEnvironment(), s);
    }
    
    protected static final Statement makeStatement(final Environment environment, final String s) throws MOPException {
        return PartialParser.makeStatement(environment, s);
    }
    
    protected final Statement makeStatement(final String s) throws MOPException {
        return makeStatement(this.getEnvironment(), s);
    }
    
    protected static final StatementList makeStatementList(final Environment environment, final String s) throws MOPException {
        return PartialParser.makeStatementList(environment, s);
    }
    
    protected final StatementList makeStatementList(final String s) throws MOPException {
        return makeStatementList(this.getEnvironment(), s);
    }
    
    @Override
    public String toString() {
        return this.substance.toString();
    }
    
    @Override
    public Environment getEnvironment() {
        return this.substance.getEnvironment();
    }
    
    public boolean isAssignableFrom(final OJClass ojClass) {
        if (ojClass.toString() == "<type>null") {
            return true;
        }
        if (ojClass == this) {
            return true;
        }
        if (this.isPrimitive()) {
            if (!ojClass.isPrimitive()) {
                return false;
            }
            if (ojClass == OJSystem.CHAR) {
                return primitiveTypeWidth(this) > primitiveTypeWidth(OJSystem.SHORT);
            }
            return primitiveTypeWidth(this) > primitiveTypeWidth(OJSystem.VOID) && primitiveTypeWidth(this) > primitiveTypeWidth(ojClass);
        }
        else {
            if (ojClass.isPrimitive()) {
                return false;
            }
            if (this == OJSystem.OBJECT) {
                return true;
            }
            if (this.isArray()) {
                return ojClass.isArray() && this.getComponentType().isAssignableFrom(ojClass.getComponentType());
            }
            if (ojClass.isArray()) {
                return false;
            }
            if (this.isInterface()) {
                final OJClass[] interfaces = ojClass.getInterfaces();
                for (int i = 0; i < interfaces.length; ++i) {
                    if (this.isAssignableFrom(interfaces[i])) {
                        return true;
                    }
                }
            }
            if (ojClass.isInterface()) {
                return false;
            }
            final OJClass superclass = ojClass.getSuperclass();
            return superclass != null && this.isAssignableFrom(superclass);
        }
    }
    
    private static int primitiveTypeWidth(final OJClass ojClass) {
        if (ojClass == OJSystem.BYTE) {
            return 1;
        }
        if (ojClass == OJSystem.SHORT) {
            return 2;
        }
        if (ojClass == OJSystem.INT) {
            return 3;
        }
        if (ojClass == OJSystem.LONG) {
            return 4;
        }
        if (ojClass == OJSystem.FLOAT) {
            return 5;
        }
        if (ojClass == OJSystem.DOUBLE) {
            return 5;
        }
        return -1;
    }
    
    public boolean isInterface() {
        return this.substance.isInterface();
    }
    
    public boolean isEnumeration() {
        return this.substance.isEnumeration();
    }
    
    public boolean isArray() {
        return this.substance.isArray();
    }
    
    public boolean isPrimitive() {
        return this.substance.isPrimitive();
    }
    
    public boolean isPrimitiveWrapper() {
        return this != this.unwrappedPrimitive();
    }
    
    public OJClass primitiveWrapper() {
        if (this == OJSystem.VOID) {
            return null;
        }
        if (this == OJSystem.BOOLEAN) {
            return forClass(Boolean.class);
        }
        if (this == OJSystem.BYTE) {
            return forClass(Byte.class);
        }
        if (this == OJSystem.CHAR) {
            return forClass(Character.class);
        }
        if (this == OJSystem.SHORT) {
            return forClass(Short.class);
        }
        if (this == OJSystem.INT) {
            return forClass(Integer.class);
        }
        if (this == OJSystem.LONG) {
            return forClass(Long.class);
        }
        if (this == OJSystem.FLOAT) {
            return forClass(Float.class);
        }
        if (this == OJSystem.DOUBLE) {
            return forClass(Double.class);
        }
        return this;
    }
    
    public OJClass unwrappedPrimitive() {
        if (this == forClass(Boolean.class)) {
            return OJSystem.BOOLEAN;
        }
        if (this == forClass(Byte.class)) {
            return OJSystem.BYTE;
        }
        if (this == forClass(Character.class)) {
            return OJSystem.CHAR;
        }
        if (this == forClass(Short.class)) {
            return OJSystem.SHORT;
        }
        if (this == forClass(Integer.class)) {
            return OJSystem.INT;
        }
        if (this == forClass(Long.class)) {
            return OJSystem.LONG;
        }
        if (this == forClass(Float.class)) {
            return OJSystem.FLOAT;
        }
        if (this == forClass(Double.class)) {
            return OJSystem.DOUBLE;
        }
        return this;
    }
    
    @Override
    public String getName() {
        return this.substance.getName();
    }
    
    public String getSimpleName() {
        return Environment.toSimpleName(this.getName());
    }
    
    public String getPackage() {
        final int lastIndex = this.getName().lastIndexOf(46);
        if (lastIndex == -1) {
            return null;
        }
        return this.getName().substring(0, lastIndex);
    }
    
    public boolean isInSamePackage(final OJClass ojClass) {
        if (ojClass == null) {
            return false;
        }
        final String package1 = ojClass.getPackage();
        if (package1 == null) {
            return this.getPackage() == null;
        }
        return package1.equals(this.getPackage());
    }
    
    public OJClass getSuperclass() {
        return this.substance.getSuperclass();
    }
    
    public OJClass[] getInterfaces() {
        return this.substance.getInterfaces();
    }
    
    public OJClass getComponentType() {
        return this.substance.getComponentType();
    }
    
    @Override
    public OJModifier getModifiers() {
        return this.substance.getModifiers();
    }
    
    public ParseTree getSuffix(final String s) {
        return this.substance.getSuffix(s);
    }
    
    @Override
    public OJClass getDeclaringClass() {
        return this.substance.getDeclaringClass();
    }
    
    public final OJClass[] getAllClasses() {
        return overridesOn(this.getDeclaredClasses(), this.getInheritedClasses());
    }
    
    public final OJField[] getAllFields() {
        return overridesOn(this.getDeclaredFields(), this.getInheritedFields());
    }
    
    public final OJMethod[] getAllMethods() {
        return overridesOn(this.getDeclaredMethods(), this.getInheritedMethods());
    }
    
    public OJClass[] getInheritedClasses() {
        final OJClass superclass = this.getSuperclass();
        if (superclass == null) {
            return new OJClass[0];
        }
        return superclass.getInheritableClasses(this);
    }
    
    public OJField[] getInheritedFields() {
        final OJClass superclass = this.getSuperclass();
        OJField[] inheritableFields;
        if (superclass == null) {
            inheritableFields = new OJField[0];
        }
        else {
            inheritableFields = superclass.getInheritableFields(this);
        }
        int length = inheritableFields.length;
        final OJClass[] interfaces = this.getInterfaces();
        final OJField[][] array = new OJField[interfaces.length][];
        for (int i = 0; i < interfaces.length; ++i) {
            array[i] = interfaces[i].getInheritableFields(this);
            length += array[i].length;
        }
        final OJField[] array2 = new OJField[length];
        int n = 0;
        for (int j = 0; j < interfaces.length; ++j) {
            System.arraycopy(array[j], 0, array2, n, array[j].length);
            n += array[j].length;
        }
        System.arraycopy(inheritableFields, 0, array2, n, inheritableFields.length);
        return array2;
    }
    
    public final OJMethod[] getInheritedMethods() {
        final OJClass superclass = this.getSuperclass();
        OJMethod[] inheritableMethods;
        if (superclass == null) {
            inheritableMethods = new OJMethod[0];
        }
        else {
            inheritableMethods = superclass.getInheritableMethods(this);
        }
        int length = inheritableMethods.length;
        final OJClass[] interfaces = this.getInterfaces();
        final OJMethod[][] array = new OJMethod[interfaces.length][];
        for (int i = 0; i < interfaces.length; ++i) {
            array[i] = interfaces[i].getInheritableMethods(this);
            length += array[i].length;
        }
        final OJMethod[] array2 = new OJMethod[length];
        int n = 0;
        for (int j = 0; j < interfaces.length; ++j) {
            System.arraycopy(array[j], 0, array2, n, array[j].length);
            n += array[j].length;
        }
        System.arraycopy(inheritableMethods, 0, array2, n, inheritableMethods.length);
        return array2;
    }
    
    @Deprecated
    public final OJClass[] getInheritableClasses() {
        return removeTheDefaults(removeThePrivates(this.getAllClasses()));
    }
    
    @Deprecated
    public final OJField[] getInheritableFields() {
        return removeTheDefaults(removeThePrivates(this.getAllFields()));
    }
    
    @Deprecated
    public final OJMethod[] getInheritableMethods() {
        return removeTheDefaults(removeThePrivates(this.getAllMethods()));
    }
    
    public OJClass[] getInheritableClasses(final OJClass ojClass) {
        final OJClass[] removeThePrivates = removeThePrivates(this.getAllClasses());
        if (this.isInSamePackage(ojClass)) {
            return removeThePrivates;
        }
        return removeTheDefaults(removeThePrivates);
    }
    
    public OJField[] getInheritableFields(final OJClass ojClass) {
        final OJField[] removeThePrivates = removeThePrivates(this.getAllFields());
        if (this.isInSamePackage(ojClass)) {
            return removeThePrivates;
        }
        return removeTheDefaults(removeThePrivates);
    }
    
    public OJMethod[] getInheritableMethods(final OJClass ojClass) {
        final OJMethod[] removeThePrivates = removeThePrivates(this.getAllMethods());
        if (this.isInSamePackage(ojClass)) {
            return removeThePrivates;
        }
        return removeTheDefaults(removeThePrivates);
    }
    
    public OJConstructor[] getInheritableConstructors(final OJClass ojClass) {
        final OJConstructor[] removeThePrivates = removeThePrivates(this.getDeclaredConstructors());
        if (this.isInSamePackage(ojClass)) {
            return removeThePrivates;
        }
        return removeTheDefaults(removeThePrivates);
    }
    
    private static final OJClass[] overridesOn(final OJClass[] array, final OJClass[] array2) {
        return Toolbox.overridesOn(array, array2);
    }
    
    private static final OJField[] overridesOn(final OJField[] array, final OJField[] array2) {
        return Toolbox.overridesOn(array, array2);
    }
    
    private static final OJMethod[] overridesOn(final OJMethod[] array, final OJMethod[] array2) {
        return Toolbox.overridesOn(array, array2);
    }
    
    private static final OJClass[] removeThePrivates(final OJClass[] array) {
        return Toolbox.removeThePrivates(array);
    }
    
    private static final OJField[] removeThePrivates(final OJField[] array) {
        return Toolbox.removeThePrivates(array);
    }
    
    private static final OJMethod[] removeThePrivates(final OJMethod[] array) {
        return Toolbox.removeThePrivates(array);
    }
    
    private static final OJConstructor[] removeThePrivates(final OJConstructor[] array) {
        return Toolbox.removeThePrivates(array);
    }
    
    private static final OJClass[] removeTheDefaults(final OJClass[] array) {
        return Toolbox.removeTheDefaults(array);
    }
    
    private static final OJField[] removeTheDefaults(final OJField[] array) {
        return Toolbox.removeTheDefaults(array);
    }
    
    private static final OJMethod[] removeTheDefaults(final OJMethod[] array) {
        return Toolbox.removeTheDefaults(array);
    }
    
    private static final OJConstructor[] removeTheDefaults(final OJConstructor[] array) {
        return Toolbox.removeTheDefaults(array);
    }
    
    private static final OJClass[] removeTheNonPublics(final OJClass[] array) {
        return Toolbox.removeTheNonPublics(array);
    }
    
    private static final OJField[] removeTheNonPublics(final OJField[] array) {
        return Toolbox.removeTheNonPublics(array);
    }
    
    private static final OJMethod[] removeTheNonPublics(final OJMethod[] array) {
        return Toolbox.removeTheNonPublics(array);
    }
    
    private static final OJConstructor[] removeTheNonPublics(final OJConstructor[] array) {
        return Toolbox.removeTheNonPublics(array);
    }
    
    private static final OJMethod[] pickupMethodsByName(final OJMethod[] array, final String s) {
        return Toolbox.pickupMethodsByName(array, s);
    }
    
    private static final OJField pickupField(final OJField[] array, final String s) {
        return Toolbox.pickupField(array, s);
    }
    
    private static final OJEnumConstant pickupEnumConstant(final OJEnumConstant[] array, final String s) {
        return Toolbox.pickupEnumConstant(array, s);
    }
    
    private static final OJMethod pickupMethod(final OJMethod[] array, final String s, final OJClass[] array2) {
        return Toolbox.pickupMethod(array, s, array2);
    }
    
    private static final OJConstructor pickupConstructor(final OJConstructor[] array, final OJClass[] array2) {
        return Toolbox.pickupConstructor(array, array2);
    }
    
    private static final OJMethod pickupAcceptableMethod(final OJMethod[] array, final String s, final OJClass[] array2) {
        return Toolbox.pickupAcceptableMethod(array, s, array2);
    }
    
    private static final OJConstructor pickupAcceptableConstructor(final OJConstructor[] array, final OJClass[] array2) {
        return Toolbox.pickupAcceptableConstructor(array, array2);
    }
    
    public OJClass[] getClasses() {
        return removeTheNonPublics(this.getAllClasses());
    }
    
    public OJField[] getFields() {
        return removeTheNonPublics(this.getAllFields());
    }
    
    public OJMethod[] getMethods() {
        return removeTheNonPublics(this.getAllMethods());
    }
    
    public OJConstructor[] getConstructors() {
        return removeTheNonPublics(this.getDeclaredConstructors());
    }
    
    public OJField getField(final String s) throws NoSuchMemberException {
        final OJField pickupField = pickupField(this.getFields(), s);
        if (pickupField != null) {
            return pickupField;
        }
        throw new NoSuchMemberException(s);
    }
    
    public OJMethod getMethod(final String s, final OJClass[] array) throws NoSuchMemberException {
        final OJMethod pickupMethod = pickupMethod(this.getMethods(), s, array);
        if (pickupMethod != null) {
            return pickupMethod;
        }
        throw new NoSuchMemberException(new Signature(s, array).toString());
    }
    
    public OJConstructor getConstructor(final OJClass[] array) throws NoSuchMemberException {
        final OJConstructor pickupConstructor = pickupConstructor(this.getConstructors(), array);
        if (pickupConstructor != null) {
            return pickupConstructor;
        }
        throw new NoSuchMemberException(new Signature(array).toString());
    }
    
    public final OJClass[] getClasses(final OJClass ojClass) {
        if (this == ojClass) {
            return this.getAllClasses();
        }
        if (this.isInSamePackage(ojClass)) {
            return removeThePrivates(this.getAllClasses());
        }
        if (this.isAssignableFrom(ojClass)) {
            return this.getInheritableClasses(ojClass);
        }
        return removeTheNonPublics(this.getAllClasses());
    }
    
    public final OJField[] getFields(final OJClass ojClass) {
        if (this == ojClass) {
            return this.getAllFields();
        }
        if (this.isInSamePackage(ojClass)) {
            return removeThePrivates(this.getAllFields());
        }
        if (this.isAssignableFrom(ojClass)) {
            return this.getInheritableFields(ojClass);
        }
        return removeTheNonPublics(this.getAllFields());
    }
    
    public final OJMethod[] getMethods(final OJClass ojClass) {
        if (this == ojClass) {
            return this.getAllMethods();
        }
        if (this.isInSamePackage(ojClass)) {
            return removeThePrivates(this.getAllMethods());
        }
        if (this.isAssignableFrom(ojClass)) {
            return this.getInheritableMethods(ojClass);
        }
        return removeTheNonPublics(this.getAllMethods());
    }
    
    public final OJConstructor[] getConstructors(final OJClass ojClass) {
        if (this == ojClass) {
            return this.getDeclaredConstructors();
        }
        if (this.isInSamePackage(ojClass)) {
            return removeThePrivates(this.getDeclaredConstructors());
        }
        if (this.isAssignableFrom(ojClass)) {
            return this.getInheritableConstructors(ojClass);
        }
        return removeTheNonPublics(this.getDeclaredConstructors());
    }
    
    public OJField getField(final String s, final OJClass ojClass) throws NoSuchMemberException {
        final OJField pickupField = pickupField(this.getFields(ojClass), s);
        if (pickupField != null) {
            return pickupField;
        }
        throw new NoSuchMemberException(s);
    }
    
    public OJMethod getMethod(final String s, final OJClass[] array, final OJClass ojClass) throws NoSuchMemberException {
        final OJMethod pickupMethod = pickupMethod(this.getMethods(ojClass), s, array);
        if (pickupMethod != null) {
            return pickupMethod;
        }
        throw new NoSuchMemberException(new Signature(s, array).toString());
    }
    
    public OJConstructor getConstructor(final OJClass[] array, final OJClass ojClass) throws NoSuchMemberException {
        final OJConstructor pickupConstructor = pickupConstructor(this.getConstructors(ojClass), array);
        if (pickupConstructor != null) {
            return pickupConstructor;
        }
        throw new NoSuchMemberException(new Signature(array).toString());
    }
    
    @Deprecated
    public final OJField getAllField(final String s) throws NoSuchMemberException {
        final OJField pickupField = pickupField(this.getAllFields(), s);
        if (pickupField != null) {
            return pickupField;
        }
        throw new NoSuchMemberException(s);
    }
    
    @Deprecated
    public final OJMethod[] getAllMethods(final String s) {
        return pickupMethodsByName(this.getAllMethods(), s);
    }
    
    @Deprecated
    public final OJMethod getAllMethod(final String s, final OJClass[] array) throws NoSuchMemberException {
        final OJMethod pickupMethod = pickupMethod(this.getAllMethods(), s, array);
        if (pickupMethod != null) {
            return pickupMethod;
        }
        throw new NoSuchMemberException(new Signature(s, array).toString());
    }
    
    public OJMethod getAcceptableMethod(final String s, final OJClass[] array, final OJClass ojClass) throws NoSuchMemberException {
        final OJMethod pickupAcceptableMethod = pickupAcceptableMethod(this.getMethods(ojClass), s, array);
        if (pickupAcceptableMethod != null) {
            return pickupAcceptableMethod;
        }
        throw new NoSuchMemberException(new Signature(s, array).toString());
    }
    
    public OJConstructor getAcceptableConstructor(final OJClass[] array, final OJClass ojClass) throws NoSuchMemberException {
        final OJConstructor pickupAcceptableConstructor = pickupAcceptableConstructor(this.getConstructors(ojClass), array);
        if (pickupAcceptableConstructor != null) {
            return pickupAcceptableConstructor;
        }
        throw new NoSuchMemberException(new Signature(array).toString());
    }
    
    public OJClass[] getDeclaredClasses() {
        return this.substance.getDeclaredClasses();
    }
    
    public OJField[] getDeclaredFields() {
        return this.substance.getDeclaredFields();
    }
    
    public OJMethod[] getDeclaredMethods() {
        return this.substance.getDeclaredMethods();
    }
    
    public OJConstructor[] getDeclaredConstructors() {
        return this.substance.getDeclaredConstructors();
    }
    
    public final OJField getDeclaredField(final String s) throws NoSuchMemberException {
        final OJField pickupField = pickupField(this.getDeclaredFields(), s);
        if (pickupField != null) {
            return pickupField;
        }
        throw new NoSuchMemberException(s);
    }
    
    public final OJMethod getDeclaredMethod(final String s, final OJClass[] array) throws NoSuchMemberException {
        final OJMethod pickupMethod = pickupMethod(this.getDeclaredMethods(), s, array);
        if (pickupMethod != null) {
            return pickupMethod;
        }
        throw new NoSuchMemberException(new Signature(s, array).toString());
    }
    
    public final OJConstructor getDeclaredConstructor(final OJClass[] array) throws NoSuchMemberException {
        final OJConstructor pickupConstructor = pickupConstructor(this.getDeclaredConstructors(), array);
        if (pickupConstructor != null) {
            return pickupConstructor;
        }
        throw new NoSuchMemberException(new Signature(array).toString());
    }
    
    public OJClass makeCopy(final String str) throws MOPException {
        DebugOut.println("makeCopy() of " + this.getName() + " with a new name: " + str);
        try {
            final ClassDeclaration classDeclaration = (ClassDeclaration)this.getSourceCode().makeRecursiveCopy();
            final String packageName = Environment.toPackageName(str);
            final String simpleName = Environment.toSimpleName(str);
            classDeclaration.setName(simpleName);
            classDeclaration.accept(new TypeNameQualifier(this.getEnvironment(), simpleName));
            return new OJClass(new FileEnvironment(OJSystem.env, packageName, simpleName), null, classDeclaration);
        }
        catch (CannotAlterException ex2) {
            return this;
        }
        catch (ParseTreeException ex) {
            throw new MOPException(ex);
        }
    }
    
    public boolean isExecutable() {
        return this.substance.isExecutable();
    }
    
    public boolean isAlterable() {
        return this.substance.isAlterable();
    }
    
    public Class getByteCode() throws CannotExecuteException {
        return this.substance.getByteCode();
    }
    
    public ClassDeclaration getSourceCode() throws CannotAlterException {
        return this.substance.getSourceCode();
    }
    
    public Class getCompatibleJavaClass() {
        return this.substance.getCompatibleJavaClass();
    }
    
    @Override
    public Signature signature() {
        return new Signature(this);
    }
    
    void setDeclaringClass(final OJClass declaringClass) throws CannotAlterException {
        this.substance.setDeclaringClass(declaringClass);
    }
    
    public final void waitTranslation(final OJClass waited) throws MOPException {
        if (!OJSystem.underConstruction.containsKey(waited)) {
            return;
        }
        synchronized (OJSystem.waitingPool) {
            if (OJSystem.waitingPool.contains(waited)) {
                System.err.println("a dead lock detected between " + this.getName() + " and " + waited.getName());
                return;
            }
            OJSystem.waitingPool.add(this);
        }
        OJSystem.waited = waited;
        final Object orderingLock = OJSystem.orderingLock;
        try {
            synchronized (orderingLock) {
                synchronized (this) {
                    this.notifyAll();
                }
                orderingLock.wait();
            }
        }
        catch (InterruptedException ex) {
            throw new MOPException(ex.toString());
        }
        finally {
            OJSystem.waitingPool.remove(this);
        }
    }
    
    protected String setName(final String s) throws CannotAlterException {
        throw new CannotAlterException("not implemented");
    }
    
    protected void beInterface() throws CannotAlterException {
        if (this.isInterface()) {
            return;
        }
        this.getSourceCode().beInterface(true);
    }
    
    protected void beClass() throws CannotAlterException {
        if (!this.isInterface()) {
            return;
        }
        this.getSourceCode().beInterface(false);
    }
    
    protected OJClass setSuperclass(final OJClass ojClass) throws CannotAlterException {
        final ClassDeclaration sourceCode = this.getSourceCode();
        if (this.isInterface()) {
            throw new CannotAlterException("cannot set a superclass of interface");
        }
        final OJClass superclass = this.getSuperclass();
        sourceCode.setBaseclass(TypeName.forOJClass(ojClass));
        return superclass;
    }
    
    protected OJClass[] setInterfaces(final OJClass[] array) throws CannotAlterException {
        final ClassDeclaration sourceCode = this.getSourceCode();
        final OJClass[] interfaces = this.getInterfaces();
        if (this.isInterface()) {
            sourceCode.setBaseclasses(Toolbox.TNsForOJClasses(array));
        }
        else {
            sourceCode.setInterfaces(Toolbox.TNsForOJClasses(array));
        }
        return interfaces;
    }
    
    protected void addInterface(final OJClass ojClass) throws CannotAlterException {
        final OJClass[] interfaces = this.getInterfaces();
        final OJClass[] interfaces2 = new OJClass[interfaces.length + 1];
        System.arraycopy(interfaces, 0, interfaces2, 0, interfaces.length);
        interfaces2[interfaces.length] = ojClass;
        this.setInterfaces(interfaces2);
    }
    
    protected OJClass addClass(final OJClass ojClass) throws CannotAlterException {
        return this.substance.addClass(ojClass);
    }
    
    protected OJClass removeClass(final OJClass ojClass) throws CannotAlterException {
        return this.substance.removeClass(ojClass);
    }
    
    protected OJField addField(final OJField ojField) throws CannotAlterException {
        return this.substance.addField(ojField);
    }
    
    protected OJField removeField(final OJField ojField) throws CannotAlterException {
        return this.substance.removeField(ojField);
    }
    
    protected OJMethod addMethod(final OJMethod ojMethod) throws CannotAlterException {
        return this.substance.addMethod(ojMethod);
    }
    
    protected OJMethod removeMethod(final OJMethod ojMethod) throws CannotAlterException {
        return this.substance.removeMethod(ojMethod);
    }
    
    protected OJConstructor addConstructor(final OJConstructor ojConstructor) throws CannotAlterException {
        return this.substance.addConstructor(ojConstructor);
    }
    
    protected OJConstructor removeConstructor(final OJConstructor ojConstructor) throws CannotAlterException {
        return this.substance.removeConstructor(ojConstructor);
    }
    
    public void translateDefinition() throws MOPException {
    }
    
    public ClassDeclaration translateDefinition(final Environment environment, final ClassDeclaration classDeclaration) throws MOPException {
        final OJClass superclass = this.getSuperclass();
        if (superclass != null) {
            this.waitTranslation(superclass);
        }
        final OJClass[] interfaces = this.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            this.waitTranslation(interfaces[i]);
        }
        this.translateDefinition();
        return classDeclaration;
    }
    
    public Expression expandFieldRead(final Environment environment, final FieldAccess fieldAccess) {
        return fieldAccess;
    }
    
    public Expression expandFieldWrite(final Environment environment, final AssignmentExpression assignmentExpression) {
        return assignmentExpression;
    }
    
    public Expression expandMethodCall(final Environment environment, final MethodCall methodCall) {
        return methodCall;
    }
    
    public TypeName expandTypeName(final Environment environment, final TypeName typeName) {
        if (this.isArray()) {
            return this.getComponentType().expandTypeName(environment, typeName);
        }
        return typeName;
    }
    
    public Expression expandAllocation(final Environment environment, final AllocationExpression allocationExpression) {
        return allocationExpression;
    }
    
    public Expression expandArrayAllocation(final Environment environment, final ArrayAllocationExpression arrayAllocationExpression) {
        if (this.isArray()) {
            return this.getComponentType().expandArrayAllocation(environment, arrayAllocationExpression);
        }
        return arrayAllocationExpression;
    }
    
    public Expression expandArrayAccess(final Environment environment, final ArrayAccess arrayAccess) {
        if (this.isArray()) {
            return this.getComponentType().expandArrayAccess(environment, arrayAccess);
        }
        return arrayAccess;
    }
    
    public Expression expandAssignmentExpression(final Environment environment, final AssignmentExpression assignmentExpression) {
        if (this.isArray()) {
            return this.getComponentType().expandAssignmentExpression(environment, assignmentExpression);
        }
        return assignmentExpression;
    }
    
    public Expression expandExpression(final Environment environment, final Expression expression) {
        if (this.isArray()) {
            return this.getComponentType().expandExpression(environment, expression);
        }
        return expression;
    }
    
    public Statement expandVariableDeclaration(final Environment environment, final VariableDeclaration variableDeclaration) {
        if (this.isArray()) {
            return this.getComponentType().expandVariableDeclaration(environment, variableDeclaration);
        }
        return variableDeclaration;
    }
    
    public Expression expandCastExpression(final Environment environment, final CastExpression castExpression) {
        if (this.isArray()) {
            return this.getComponentType().expandCastExpression(environment, castExpression);
        }
        return castExpression;
    }
    
    public Expression expandCastedExpression(final Environment environment, final CastExpression castExpression) {
        if (this.isArray()) {
            return this.getComponentType().expandCastedExpression(environment, castExpression);
        }
        return castExpression;
    }
    
    public OJField resolveException(final NoSuchMemberException ex, final String s) throws NoSuchMemberException {
        System.err.println("no such " + new Signature(s) + " in " + this.toString());
        throw ex;
    }
    
    public OJMethod resolveException(final NoSuchMemberException ex, final String s, final OJClass[] array) throws NoSuchMemberException {
        System.err.println("no such " + new Signature(s, array) + " in " + this.toString());
        throw ex;
    }
    
    public static boolean isRegisteredKeyword(final String s) {
        return false;
    }
    
    public static SyntaxRule getDeclSuffixRule(final String s) {
        return null;
    }
    
    public static SyntaxRule getTypeSuffixRule(final String s) {
        return null;
    }
    
    public static boolean isRegisteredModifier(final String s) {
        return false;
    }
    
    public final String getMetaInfo(final String s) {
        return this.substance.getMetaInfo(s);
    }
    
    public final Enumeration getMetaInfoKeys() {
        return this.substance.getMetaInfoKeys();
    }
    
    public final Enumeration getMetaInfoElements() {
        return this.substance.getMetaInfoElements();
    }
    
    protected final String putMetaInfo(final String s, final String s2) throws CannotAlterException {
        return this.substance.putMetaInfo(s, s2);
    }
    
    public final void writeMetaInfo(final Writer writer) throws IOException {
        this.substance.writeMetaInfo(writer);
    }
}
