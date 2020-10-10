// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.mop.OJClass;
import openjava.mop.Environment;
import openjava.ptree.util.ParseTreeVisitor;

public class UnaryExpression extends NonLeaf implements Expression
{
    public static final int POST_INCREMENT = 0;
    public static final int POST_DECREMENT = 1;
    public static final int PRE_INCREMENT = 2;
    public static final int PRE_DECREMENT = 3;
    public static final int BIT_NOT = 4;
    public static final int NOT = 5;
    public static final int PLUS = 6;
    public static final int MINUS = 7;
    private static final String[] opr_string;
    private int opr;
    
    public UnaryExpression(final int opr, final Expression p2) {
        this.opr = -1;
        this.set(p2);
        this.opr = opr;
    }
    
    public UnaryExpression(final Expression p2, final int opr) {
        this.opr = -1;
        this.set(p2);
        this.opr = opr;
    }
    
    UnaryExpression() {
        this.opr = -1;
    }
    
    @Override
    public ParseTree makeRecursiveCopy() {
        final UnaryExpression unaryExpression = (UnaryExpression)super.makeRecursiveCopy();
        unaryExpression.opr = this.opr;
        return unaryExpression;
    }
    
    @Override
    public ParseTree makeCopy() {
        final UnaryExpression unaryExpression = (UnaryExpression)super.makeCopy();
        unaryExpression.opr = this.opr;
        return unaryExpression;
    }
    
    public Expression getExpression() {
        return (Expression)this.elementAt(0);
    }
    
    public void setExpression(final Expression p) {
        this.setElementAt(p, 0);
    }
    
    public int getOperator() {
        return this.opr;
    }
    
    public void setOperator(final int opr) {
        this.opr = opr;
    }
    
    public boolean isPostfix() {
        return this.opr == 1 || this.opr == 0;
    }
    
    public boolean isPrefix() {
        return !this.isPostfix();
    }
    
    public String operatorString() {
        return UnaryExpression.opr_string[this.opr];
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        return this.getExpression().getType(environment);
    }
    
    static {
        opr_string = new String[] { "++", "--", "++", "--", "~", "!", "+", "-" };
    }
}
