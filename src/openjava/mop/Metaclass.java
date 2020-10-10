// 
// Decompiled by Procyon v0.5.36
// 

package openjava.mop;

import openjava.ptree.Expression;
import openjava.ptree.ConstructorInvocation;
import openjava.ptree.StatementList;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ParseTreeException;
import openjava.ptree.util.ParseTreeVisitor;
import openjava.ptree.util.ClassLiteralReplacer;

public class Metaclass extends OJClass
{
    private static final String CONVENIENT = "convenient";
    
    @Override
    public void translateDefinition() throws MOPException {
        final OJConstructor srcConstr = this.makeSrcConstr();
        try {
            this.getConstructor(srcConstr.getParameterTypes());
        }
        catch (NoSuchMemberException ex) {
            this.addConstructor(srcConstr);
        }
        final OJConstructor byteConstr = this.makeByteConstr();
        try {
            this.getConstructor(byteConstr.getParameterTypes());
        }
        catch (NoSuchMemberException ex2) {
            this.addConstructor(byteConstr);
        }
        final OJMethod[] declaredMethods = this.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; ++i) {
            if (declaredMethods[i].getModifiers().has("convenient")) {
                try {
                    declaredMethods[i].getBody().accept(new ClassLiteralReplacer(declaredMethods[i].getEnvironment()));
                }
                catch (ParseTreeException x) {
                    System.err.println(x);
                }
            }
        }
    }
    
    public static final boolean isRegisteredModifier(final String s) {
        return s.equals("convenient") || OJClass.isRegisteredModifier(s);
    }
    
    private OJConstructor makeSrcConstr() throws MOPException {
        final OJConstructor ojConstructor = new OJConstructor(this, OJModifier.forModifier(1), new OJClass[] { OJClass.forClass(Environment.class), OJClass.forClass(OJClass.class), OJClass.forClass(ClassDeclaration.class) }, null, null, new StatementList());
        ojConstructor.setTransference(new ConstructorInvocation(ojConstructor.getParameterVariables(), null));
        return ojConstructor;
    }
    
    private OJConstructor makeByteConstr() throws MOPException {
        final OJConstructor ojConstructor = new OJConstructor(this, OJModifier.forModifier(1), new OJClass[] { OJClass.forClass(Class.class), OJClass.forClass(MetaInfo.class) }, null, null, new StatementList());
        ojConstructor.setTransference(new ConstructorInvocation(ojConstructor.getParameterVariables(), null));
        return ojConstructor;
    }
    
    public Metaclass(final Environment environment, final OJClass ojClass, final ClassDeclaration classDeclaration) {
        super(environment, ojClass, classDeclaration);
    }
    
    public Metaclass(final Class clazz, final MetaInfo metaInfo) {
        super(clazz, metaInfo);
    }
}
