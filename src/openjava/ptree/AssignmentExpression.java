// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.mop.OJClass;
import openjava.mop.Environment;
import openjava.ptree.util.ParseTreeVisitor;

public class AssignmentExpression extends NonLeaf implements Expression
{
    public static final int EQUALS = 0;
    public static final int MULT = 1;
    public static final int DIVIDE = 2;
    public static final int MOD = 3;
    public static final int ADD = 4;
    public static final int SUB = 5;
    public static final int SHIFT_L = 6;
    public static final int SHIFT_R = 7;
    public static final int SHIFT_RR = 8;
    public static final int AND = 9;
    public static final int XOR = 10;
    public static final int OR = 11;
    static final String[] opr_string;
    private int opr;
    
    public AssignmentExpression(final Expression p3, final int opr, final Expression p4) {
        this.opr = -1;
        this.set(p3, p4);
        this.opr = opr;
    }
    
    public AssignmentExpression(final Expression expression, final String anObject, final Expression expression2) {
        this(expression, 0, expression2);
        for (int i = 0; i < AssignmentExpression.opr_string.length; ++i) {
            if (AssignmentExpression.opr_string[i].equals(anObject)) {
                this.opr = i;
            }
        }
    }
    
    public AssignmentExpression() {
        this.opr = -1;
    }
    
    @Override
    public ParseTree makeRecursiveCopy() {
        final AssignmentExpression assignmentExpression = (AssignmentExpression)super.makeRecursiveCopy();
        assignmentExpression.opr = this.opr;
        return assignmentExpression;
    }
    
    @Override
    public ParseTree makeCopy() {
        final AssignmentExpression assignmentExpression = (AssignmentExpression)super.makeCopy();
        assignmentExpression.opr = this.opr;
        return assignmentExpression;
    }
    
    public Expression getLeft() {
        return (Expression)this.elementAt(0);
    }
    
    public void setLeft(final Expression p) {
        this.setElementAt(p, 0);
    }
    
    public Expression getRight() {
        return (Expression)this.elementAt(1);
    }
    
    public void setRight(final Expression p) {
        this.setElementAt(p, 1);
    }
    
    public int getOperator() {
        return this.opr;
    }
    
    public void setOperator(final int opr) {
        this.opr = opr;
    }
    
    public String operatorString() {
        return AssignmentExpression.opr_string[this.getOperator()];
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        return this.getLeft().getType(environment);
    }
    
    static {
        opr_string = new String[] { "=", "*=", "/=", "%=", "+=", "-=", "<<=", ">>=", ">>>=", "&=", "^=", "|=" };
    }
}
