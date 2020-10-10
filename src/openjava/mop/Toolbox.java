// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.Parameter;
import openjava.ptree.ParameterList;
import openjava.ptree.TypeName;
import java.util.Enumeration;
import java.util.Hashtable;

public abstract class Toolbox
{
    private static final String PARAMETER_NAME = "oj_param";
    
    public static final OJClass[] overridesOn(final OJClass[] array, final OJClass[] array2) {
        final Hashtable<Signature, OJClass> hashtable = new Hashtable<Signature, OJClass>();
        for (int i = 0; i < array2.length; ++i) {
            hashtable.put(array2[i].signature(), array2[i]);
        }
        for (int j = 0; j < array.length; ++j) {
            hashtable.put(array[j].signature(), array[j]);
        }
        final OJClass[] array3 = new OJClass[hashtable.size()];
        final Enumeration<OJClass> elements = hashtable.elements();
        int n = 0;
        while (elements.hasMoreElements()) {
            array3[n] = elements.nextElement();
            ++n;
        }
        return array3;
    }
    
    public static final OJField[] overridesOn(final OJField[] array, final OJField[] array2) {
        final Hashtable<Signature, OJField> hashtable = new Hashtable<Signature, OJField>();
        for (int i = 0; i < array2.length; ++i) {
            hashtable.put(array2[i].signature(), array2[i]);
        }
        for (int j = 0; j < array.length; ++j) {
            hashtable.put(array[j].signature(), array[j]);
        }
        final OJField[] array3 = new OJField[hashtable.size()];
        final Enumeration<OJField> elements = hashtable.elements();
        int n = 0;
        while (elements.hasMoreElements()) {
            array3[n] = elements.nextElement();
            ++n;
        }
        return array3;
    }
    
    public static final OJMethod[] overridesOn(final OJMethod[] array, final OJMethod[] array2) {
        final Hashtable<Signature, OJMethod> hashtable = new Hashtable<Signature, OJMethod>();
        for (int i = 0; i < array2.length; ++i) {
            hashtable.put(array2[i].signature(), array2[i]);
        }
        for (int j = 0; j < array.length; ++j) {
            hashtable.put(array[j].signature(), array[j]);
        }
        final OJMethod[] array3 = new OJMethod[hashtable.size()];
        final Enumeration<OJMethod> elements = hashtable.elements();
        int n = 0;
        while (elements.hasMoreElements()) {
            array3[n] = elements.nextElement();
            ++n;
        }
        return array3;
    }
    
    public static final OJClass[] removeThePrivates(final OJClass[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (!array[i].getModifiers().isPrivate()) {
                ++n;
            }
        }
        final OJClass[] array2 = new OJClass[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            if (!array[j].getModifiers().isPrivate()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJField[] removeThePrivates(final OJField[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (!array[i].getModifiers().isPrivate()) {
                ++n;
            }
        }
        final OJField[] array2 = new OJField[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            if (!array[j].getModifiers().isPrivate()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJMethod[] removeThePrivates(final OJMethod[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (!array[i].getModifiers().isPrivate()) {
                ++n;
            }
        }
        final OJMethod[] array2 = new OJMethod[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            if (!array[j].getModifiers().isPrivate()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJConstructor[] removeThePrivates(final OJConstructor[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (!array[i].getModifiers().isPrivate()) {
                ++n;
            }
        }
        final OJConstructor[] array2 = new OJConstructor[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            if (!array[j].getModifiers().isPrivate()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJClass[] removeTheDefaults(final OJClass[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            final OJModifier modifiers = array[i].getModifiers();
            if (modifiers.isPrivate() || modifiers.isProtected() || modifiers.isPublic()) {
                ++n;
            }
        }
        final OJClass[] array2 = new OJClass[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            final OJModifier modifiers2 = array[j].getModifiers();
            if (modifiers2.isPrivate() || modifiers2.isProtected() || modifiers2.isPublic()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJField[] removeTheDefaults(final OJField[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            final OJModifier modifiers = array[i].getModifiers();
            if (modifiers.isPrivate() || modifiers.isProtected() || modifiers.isPublic()) {
                ++n;
            }
        }
        final OJField[] array2 = new OJField[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            final OJModifier modifiers2 = array[j].getModifiers();
            if (modifiers2.isPrivate() || modifiers2.isProtected() || modifiers2.isPublic()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJMethod[] removeTheDefaults(final OJMethod[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            final OJModifier modifiers = array[i].getModifiers();
            if (modifiers.isPrivate() || modifiers.isProtected() || modifiers.isPublic()) {
                ++n;
            }
        }
        final OJMethod[] array2 = new OJMethod[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            final OJModifier modifiers2 = array[j].getModifiers();
            if (modifiers2.isPrivate() || modifiers2.isProtected() || modifiers2.isPublic()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJConstructor[] removeTheDefaults(final OJConstructor[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            final OJModifier modifiers = array[i].getModifiers();
            if (modifiers.isPrivate() || modifiers.isProtected() || modifiers.isPublic()) {
                ++n;
            }
        }
        final OJConstructor[] array2 = new OJConstructor[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            final OJModifier modifiers2 = array[j].getModifiers();
            if (modifiers2.isPrivate() || modifiers2.isProtected() || modifiers2.isPublic()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJClass[] removeTheNonPublics(final OJClass[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i].getModifiers().isPublic()) {
                ++n;
            }
        }
        final OJClass[] array2 = new OJClass[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            if (array[j].getModifiers().isPublic()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJField[] removeTheNonPublics(final OJField[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i].getModifiers().isPublic()) {
                ++n;
            }
        }
        final OJField[] array2 = new OJField[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            if (array[j].getModifiers().isPublic()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJMethod[] removeTheNonPublics(final OJMethod[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i].getModifiers().isPublic()) {
                ++n;
            }
        }
        final OJMethod[] array2 = new OJMethod[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            if (array[j].getModifiers().isPublic()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJConstructor[] removeTheNonPublics(final OJConstructor[] array) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i].getModifiers().isPublic()) {
                ++n;
            }
        }
        final OJConstructor[] array2 = new OJConstructor[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            if (array[j].getModifiers().isPublic()) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJField pickupField(final OJField[] array, final String s) {
        for (int i = 0; i < array.length; ++i) {
            if (s.equals(array[i].getName())) {
                return array[i];
            }
        }
        return null;
    }
    
    public static final OJEnumConstant pickupEnumConstant(final OJEnumConstant[] array, final String s) {
        for (int i = 0; i < array.length; ++i) {
            if (s.equals(array[i].getName())) {
                return array[i];
            }
        }
        return null;
    }
    
    public static final OJMethod pickupMethod(OJMethod[] pickupMethodsByName, final String s, final OJClass[] array) {
        pickupMethodsByName = pickupMethodsByName(pickupMethodsByName, s);
        return pickupMethodByParameterTypes(pickupMethodsByName, array);
    }
    
    public static final OJMethod pickupAcceptableMethod(OJMethod[] pickupAcceptableMethods, final String s, final OJClass[] array) {
        pickupAcceptableMethods = pickupAcceptableMethods(pickupAcceptableMethods, s, array);
        return pickupMostSpecified(pickupAcceptableMethods);
    }
    
    public static final OJMethod[] pickupAcceptableMethods(OJMethod[] pickupMethodsByName, final String s, final OJClass[] array) {
        pickupMethodsByName = pickupMethodsByName(pickupMethodsByName, s);
        return pickupAcceptableMethodsByParameterTypes(pickupMethodsByName, array);
    }
    
    public static final OJConstructor pickupConstructor(final OJConstructor[] array, OJClass[] array2) {
        if (array2 == null) {
            array2 = new OJClass[0];
        }
        for (int i = 0; i < array.length; ++i) {
            if (isSame(array[i].getParameterTypes(), array2)) {
                return array[i];
            }
        }
        return null;
    }
    
    public static final OJConstructor pickupAcceptableConstructor(OJConstructor[] pickupAcceptableConstructors, final OJClass[] array) {
        pickupAcceptableConstructors = pickupAcceptableConstructors(pickupAcceptableConstructors, array);
        return pickupMostSpecified(pickupAcceptableConstructors);
    }
    
    public static final OJConstructor[] pickupAcceptableConstructors(final OJConstructor[] array, OJClass[] array2) {
        array2 = ((array2 == null) ? new OJClass[0] : array2);
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (isAcceptable(array[i].getParameterTypes(), array2)) {
                ++n;
            }
        }
        final OJConstructor[] array3 = new OJConstructor[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            if (isAcceptable(array[j].getParameterTypes(), array2)) {
                array3[n2++] = array[j];
            }
            ++j;
        }
        return array3;
    }
    
    public static final OJMethod[] pickupMethodsByName(final OJMethod[] array, final String s) {
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (s.equals(array[i].getName())) {
                ++n;
            }
        }
        final OJMethod[] array2 = new OJMethod[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            if (s.equals(array[j].getName())) {
                array2[n2++] = array[j];
            }
            ++j;
        }
        return array2;
    }
    
    public static final OJMethod pickupMethodByParameterTypes(final OJMethod[] array, OJClass[] array2) {
        if (array2 == null) {
            array2 = new OJClass[0];
        }
        for (int i = 0; i < array.length; ++i) {
            if (isSame(array[i].getParameterTypes(), array2)) {
                return array[i];
            }
        }
        return null;
    }
    
    public static final OJMethod[] pickupAcceptableMethodsByParameterTypes(final OJMethod[] array, OJClass[] array2) {
        if (array2 == null) {
            array2 = new OJClass[0];
        }
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            if (isAcceptable(array[i].getParameterTypes(), array2)) {
                ++n;
            }
        }
        final OJMethod[] array3 = new OJMethod[n];
        int j = 0;
        int n2 = 0;
        while (j < array.length) {
            if (isAcceptable(array[j].getParameterTypes(), array2)) {
                array3[n2++] = array[j];
            }
            ++j;
        }
        return array3;
    }
    
    public static final boolean isSame(final OJClass[] array, final OJClass[] array2) {
        if (array.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array2.length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static final boolean isAcceptable(final OJClass[] array, final OJClass[] array2) {
        if (array.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array2.length; ++i) {
            if (!array[i].isAssignableFrom(array2[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static final boolean isAdaptableTo(final OJClass[] array, final OJClass[] array2) {
        if (array.length != array2.length) {
            return false;
        }
        for (int i = 0; i < array2.length; ++i) {
            if (!array2[i].isAssignableFrom(array[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static final OJConstructor pickupMostSpecified(final OJConstructor[] array) {
        if (array.length == 0) {
            return null;
        }
        OJConstructor ojConstructor = array[0];
        for (int i = 0; i < array.length; ++i) {
            if (!isAdaptableTo(ojConstructor.getParameterTypes(), array[i].getParameterTypes())) {
                ojConstructor = array[i];
            }
        }
        return ojConstructor;
    }
    
    public static final OJMethod pickupMostSpecified(final OJMethod[] array) {
        if (array.length == 0) {
            return null;
        }
        OJMethod ojMethod = array[0];
        for (int i = 0; i < array.length; ++i) {
            if (!isAdaptableTo(ojMethod.getParameterTypes(), array[i].getParameterTypes())) {
                ojMethod = array[i];
            }
        }
        return ojMethod;
    }
    
    public static final OJClass[] append(final OJClass[] array, final OJClass[] array2) {
        final OJClass[] array3 = new OJClass[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static final OJField[] append(final OJField[] array, final OJField[] array2) {
        final OJField[] array3 = new OJField[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static final OJMethod[] append(final OJMethod[] array, final OJMethod[] array2) {
        final OJMethod[] array3 = new OJMethod[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static final OJConstructor[] append(final OJConstructor[] array, final OJConstructor[] array2) {
        final OJConstructor[] array3 = new OJConstructor[array.length + array2.length];
        System.arraycopy(array, 0, array3, 0, array.length);
        System.arraycopy(array2, 0, array3, array.length, array2.length);
        return array3;
    }
    
    public static final String nameForJavaClassName(final String s) {
        if (!s.startsWith("[")) {
            return s;
        }
        final String stripHeadBracket = stripHeadBracket(s);
        String s2 = null;
        if (stripHeadBracket.startsWith("[")) {
            s2 = nameForJavaClassName(stripHeadBracket) + "[]";
        }
        else if (stripHeadBracket.endsWith(";")) {
            s2 = stripHeadBracket.substring(1, stripHeadBracket.length() - 1) + "[]";
        }
        else {
            switch (stripHeadBracket.charAt(stripHeadBracket.length() - 1)) {
                case 'Z': {
                    return "boolean[]";
                }
                case 'B': {
                    return "byte[]";
                }
                case 'C': {
                    return "char[]";
                }
                case 'D': {
                    return "double[]";
                }
                case 'F': {
                    return "float[]";
                }
                case 'I': {
                    return "int[]";
                }
                case 'J': {
                    return "long[]";
                }
                case 'S': {
                    return "short[]";
                }
                default: {
                    return "<unknown primitive type>";
                }
            }
        }
        return s2.replace('$', '.');
    }
    
    public static final String nameToJavaClassName(String string) {
        if (!string.endsWith("[]") && string.indexOf("<") <= 0 && string.indexOf(">") <= 0) {
            return string;
        }
        if (string.indexOf("<") > 0 && string.indexOf(">") > 0 && !string.endsWith("[]")) {
            return string.substring(0, string.indexOf("<"));
        }
        if (string.indexOf("<") > 0 && string.indexOf(">") > 0 && string.endsWith("[]")) {
            string = string.substring(0, string.indexOf("<")) + string.substring(string.lastIndexOf(">") + 1, string.length());
        }
        final String stripBrackets = stripBrackets(string);
        if (stripBrackets.endsWith("[]")) {
            return "[" + nameToJavaClassName(stripBrackets);
        }
        if (stripBrackets.equals("boolean")) {
            return "[Z";
        }
        if (stripBrackets.equals("byte")) {
            return "[B";
        }
        if (stripBrackets.equals("char")) {
            return "[C";
        }
        if (stripBrackets.equals("double")) {
            return "[D";
        }
        if (stripBrackets.equals("float")) {
            return "[F";
        }
        if (stripBrackets.equals("int")) {
            return "[I";
        }
        if (stripBrackets.equals("long")) {
            return "[J";
        }
        if (stripBrackets.equals("short")) {
            return "[S";
        }
        return "[L" + stripBrackets(string) + ";";
    }
    
    private static final String stripHeadBracket(final String s) {
        return s.substring(1);
    }
    
    private static final String stripBrackets(final String s) {
        return s.substring(0, s.length() - 2);
    }
    
    public static final OJClass forNameAnyway(final Environment obj, String qualifiedName) {
        qualifiedName = obj.toQualifiedName(qualifiedName);
        final OJClass lookupClass = obj.lookupClass(qualifiedName);
        if (lookupClass != null) {
            return lookupClass;
        }
        try {
            return OJClass.forName(qualifiedName);
        }
        catch (OJClassNotFoundException obj2) {
            System.err.println("OJClass.forNameAnyway() failed for : " + qualifiedName + " " + obj2);
            System.err.print(obj);
            return OJClass.forClass(Object.class);
        }
    }
    
    public static final OJClass[] arrayForNames(final Environment environment, final String[] array) {
        final OJClass[] array2 = new OJClass[array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = forNameAnyway(environment, array[i]);
        }
        return array2;
    }
    
    public static final TypeName[] TNsForOJClasses(final OJClass[] array) {
        final TypeName[] array2 = new TypeName[(array == null) ? 0 : array.length];
        for (int i = 0; i < array2.length; ++i) {
            array2[i] = TypeName.forOJClass(array[i]);
        }
        return array2;
    }
    
    public static final ParameterList generateParameters(final OJClass[] array) {
        final ParameterList list = new ParameterList();
        if (array == null) {
            return list;
        }
        for (int i = 0; i < array.length; ++i) {
            list.add(new Parameter(TypeName.forOJClass(array[i]), "oj_param" + i));
        }
        return list;
    }
    
    public static final ParameterList generateParameters(final OJClass[] array, final String[] array2) {
        final ParameterList list = new ParameterList();
        if (array == null) {
            return list;
        }
        for (int i = 0; i < array.length; ++i) {
            list.add(new Parameter(TypeName.forOJClass(array[i]), array2[i]));
        }
        return list;
    }
}
