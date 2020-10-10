// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree.util;

import openjava.ptree.ParseTreeException;
import openjava.ptree.MethodCall;
import openjava.ptree.ExpressionList;
import openjava.mop.OJClass;
import openjava.ptree.TypeName;
import openjava.ptree.Expression;
import openjava.ptree.ClassLiteral;
import openjava.mop.Environment;

public class ClassLiteralReplacer extends EvaluationShuttle
{
    public static final String OLDCLASS_PREFIX = "oldjavaclass.";
    
    public ClassLiteralReplacer(final Environment environment) {
        super(environment);
    }
    
    @Override
    public Expression evaluateDown(final ClassLiteral classLiteral) throws ParseTreeException {
        final TypeName typeName = classLiteral.getTypeName();
        if (typeName.toString().startsWith("oldjavaclass.")) {
            return new ClassLiteral(new TypeName(typeName.getName().substring("oldjavaclass.".length()), typeName.getDimension()));
        }
        return new MethodCall(OJClass.forClass(OJClass.class), "forClass", new ExpressionList(new ClassLiteral(typeName)));
    }
}
