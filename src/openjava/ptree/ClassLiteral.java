// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import openjava.mop.Environment;
import openjava.mop.OJClass;

public class ClassLiteral extends NonLeaf implements Expression
{
    public ClassLiteral(final TypeName p) {
        this.set(p);
    }
    
    public ClassLiteral(final OJClass ojClass) {
        this(TypeName.forOJClass(ojClass));
    }
    
    public TypeName getTypeName() {
        return (TypeName)this.elementAt(0);
    }
    
    public void setTypeName(final TypeName p) {
        this.set(p);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        return OJClass.forClass(Class.class);
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
}
