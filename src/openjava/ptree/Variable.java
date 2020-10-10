// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import openjava.mop.OJClass;
import openjava.mop.Environment;

public class Variable extends Leaf implements Expression
{
    private static int variableID;
    
    public Variable(final String s) {
        super(s);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        return environment.lookupBind(this.toString());
    }
    
    public static Variable generateUniqueVariable() {
        final String string = "oj_var" + Variable.variableID;
        ++Variable.variableID;
        return new Variable(string);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    static {
        Variable.variableID = 0;
    }
}
