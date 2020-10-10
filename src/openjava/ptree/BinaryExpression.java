// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.mop.OJSystem;
import openjava.mop.OJClass;
import openjava.mop.Environment;
import openjava.ptree.util.ParseTreeVisitor;

public class BinaryExpression extends NonLeaf implements Expression
{
    public static final int TIMES = 0;
    public static final int DIVIDE = 1;
    public static final int MOD = 2;
    public static final int PLUS = 3;
    public static final int MINUS = 4;
    public static final int SHIFT_L = 5;
    public static final int SHIFT_R = 6;
    public static final int SHIFT_RR = 7;
    public static final int LESS = 8;
    public static final int GREATER = 9;
    public static final int LESSEQUAL = 10;
    public static final int GREATEREQUAL = 11;
    public static final int INSTANCEOF = 12;
    public static final int EQUAL = 13;
    public static final int NOTEQUAL = 14;
    public static final int BITAND = 15;
    public static final int XOR = 16;
    public static final int BITOR = 17;
    public static final int LOGICAL_AND = 18;
    public static final int LOGICAL_OR = 19;
    static final String[] opr_string;
    private int opr;
    private static final int STRING = 30;
    private static final int OTHER = 4;
    
    public BinaryExpression(final Expression p3, final int opr, final Expression p4) {
        this.opr = -1;
        this.set(p3, p4);
        this.opr = opr;
    }
    
    public BinaryExpression(final Expression expression, final String anObject, final Expression expression2) {
        this(expression, 0, expression2);
        for (int i = 0; i < 20; ++i) {
            if (BinaryExpression.opr_string[i].equals(anObject)) {
                this.opr = i;
            }
        }
    }
    
    BinaryExpression() {
        this.opr = -1;
    }
    
    @Override
    public ParseTree makeRecursiveCopy() {
        final BinaryExpression binaryExpression = (BinaryExpression)super.makeRecursiveCopy();
        binaryExpression.opr = this.opr;
        return binaryExpression;
    }
    
    @Override
    public ParseTree makeCopy() {
        final BinaryExpression binaryExpression = (BinaryExpression)super.makeCopy();
        binaryExpression.opr = this.opr;
        return binaryExpression;
    }
    
    private final boolean needsLeftPar(final Expression expression) {
        if (expression instanceof AssignmentExpression || expression instanceof ConditionalExpression) {
            return true;
        }
        final int strength = strength(this.getOperator());
        if (expression instanceof InstanceofExpression) {
            return strength > strength(12);
        }
        return expression instanceof BinaryExpression && strength > strength(((BinaryExpression)expression).getOperator());
    }
    
    private final boolean needsRightPar(final Expression expression) {
        if (expression instanceof AssignmentExpression || expression instanceof ConditionalExpression) {
            return true;
        }
        final int strength = strength(this.getOperator());
        if (expression instanceof InstanceofExpression) {
            return strength >= strength(12);
        }
        return expression instanceof BinaryExpression && strength >= strength(((BinaryExpression)expression).getOperator());
    }
    
    protected static final int strength(final int n) {
        switch (n) {
            case 0:
            case 1:
            case 2: {
                return 40;
            }
            case 3:
            case 4: {
                return 35;
            }
            case 5:
            case 6:
            case 7: {
                return 30;
            }
            case 8:
            case 9:
            case 10:
            case 11:
            case 12: {
                return 25;
            }
            case 13:
            case 14: {
                return 20;
            }
            case 15: {
                return 16;
            }
            case 16: {
                return 14;
            }
            case 17: {
                return 12;
            }
            case 18: {
                return 10;
            }
            case 19: {
                return 8;
            }
            default: {
                return 100;
            }
        }
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
        return BinaryExpression.opr_string[this.getOperator()];
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        switch (this.opr) {
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14: {
                return OJSystem.BOOLEAN;
            }
            default: {
                return chooseType(this.getLeft().getType(environment), this.getRight().getType(environment));
            }
        }
    }
    
    static OJClass chooseType(final OJClass ojClass, final OJClass ojClass2) {
        final int strength = strength(ojClass);
        final int strength2 = strength(ojClass2);
        if (strength != 4 || strength2 != 4) {
            return (strength > strength2) ? ojClass : ojClass2;
        }
        if (ojClass.isAssignableFrom(ojClass2)) {
            return ojClass;
        }
        if (ojClass2.isAssignableFrom(ojClass)) {
            return ojClass2;
        }
        return ojClass2;
    }
    
    private static int strength(final OJClass ojClass) {
        if (ojClass == OJSystem.STRING) {
            return 30;
        }
        if (ojClass == OJSystem.DOUBLE) {
            return 20;
        }
        if (ojClass == OJSystem.FLOAT) {
            return 18;
        }
        if (ojClass == OJSystem.LONG) {
            return 16;
        }
        if (ojClass == OJSystem.INT) {
            return 14;
        }
        if (ojClass == OJSystem.CHAR) {
            return 12;
        }
        if (ojClass == OJSystem.BYTE) {
            return 10;
        }
        if (ojClass == OJSystem.NULLTYPE) {
            return 0;
        }
        return 4;
    }
    
    static {
        opr_string = new String[] { "*", "/", "%", "+", "-", "<<", ">>", ">>>", "<", ">", "<=", ">=", "instanceof", "==", "!=", "&", "^", "|", "&&", "||" };
    }
}
