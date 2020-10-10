// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;
import openjava.mop.OJClass;
import openjava.mop.Environment;

public class Literal extends Leaf implements Expression
{
    public static final int BOOLEAN = 0;
    public static final int INTEGER = 1;
    public static final int LONG = 2;
    public static final int FLOAT = 3;
    public static final int DOUBLE = 4;
    public static final int CHARACTER = 5;
    public static final int STRING = 6;
    public static final int NULL = 7;
    protected int id;
    private static Literal constantTrue_;
    private static Literal constantFalse_;
    private static Literal constantNull_;
    private static Literal constantEmptyString_;
    private static Literal constantZero_;
    private static Literal constantOne_;
    
    public Literal(final int id, final String s) {
        super(s);
        this.id = -1;
        this.id = id;
    }
    
    public static Literal makeLiteral(final String s) {
        if ("".equals(s)) {
            return constantEmptyString();
        }
        return new Literal(6, "\"" + s + "\"");
    }
    
    public static Literal makeLiteral(final boolean b) {
        return b ? constantTrue() : constantFalse();
    }
    
    public static Literal makeLiteral(final Boolean b) {
        return makeLiteral((boolean)b);
    }
    
    public static Literal makeLiteral(final char c) {
        return new Literal(5, "'" + c + "'");
    }
    
    public static Literal makeLiteral(final Character c) {
        return makeLiteral((char)c);
    }
    
    public static Literal makeLiteral(final int i) {
        if (i == 0) {
            return constantZero();
        }
        if (i == 1) {
            return constantOne();
        }
        return new Literal(1, String.valueOf(i));
    }
    
    public static Literal makeLiteral(final Integer n) {
        return makeLiteral((int)n);
    }
    
    public static Literal makeLiteral(final long l) {
        return new Literal(2, String.valueOf(l) + "l");
    }
    
    public static Literal makeLiteral(final Long n) {
        return makeLiteral((long)n);
    }
    
    public static Literal makeLiteral(final float f) {
        return new Literal(3, String.valueOf(f) + "f");
    }
    
    public static Literal makeLiteral(final Float n) {
        return makeLiteral((float)n);
    }
    
    public static Literal makeLiteral(final double d) {
        return new Literal(4, String.valueOf(d) + "d");
    }
    
    public static Literal makeLiteral(final Double n) {
        return makeLiteral((double)n);
    }
    
    public int getLiteralType() {
        return this.id;
    }
    
    public static Literal constantTrue() {
        if (Literal.constantTrue_ == null) {
            Literal.constantTrue_ = new Literal(0, "true");
        }
        return Literal.constantTrue_;
    }
    
    public static Literal constantFalse() {
        if (Literal.constantFalse_ == null) {
            Literal.constantFalse_ = new Literal(0, "false");
        }
        return Literal.constantFalse_;
    }
    
    public static Literal constantNull() {
        if (Literal.constantNull_ == null) {
            Literal.constantNull_ = new Literal(7, "null");
        }
        return Literal.constantNull_;
    }
    
    public static Literal constantEmptyString() {
        if (Literal.constantEmptyString_ == null) {
            Literal.constantEmptyString_ = new Literal(6, "\"\"");
        }
        return Literal.constantEmptyString_;
    }
    
    public static Literal constantZero() {
        if (Literal.constantZero_ == null) {
            Literal.constantZero_ = new Literal(1, "0");
        }
        return Literal.constantZero_;
    }
    
    public static Literal constantOne() {
        if (Literal.constantOne_ == null) {
            Literal.constantOne_ = new Literal(1, "1");
        }
        return Literal.constantOne_;
    }
    
    @Override
    public OJClass getType(final Environment environment) throws Exception {
        switch (this.getLiteralType()) {
            case 0: {
                return OJClass.forClass(Boolean.TYPE);
            }
            case 1: {
                return OJClass.forClass(Integer.TYPE);
            }
            case 2: {
                return OJClass.forClass(Long.TYPE);
            }
            case 3: {
                return OJClass.forClass(Float.TYPE);
            }
            case 4: {
                return OJClass.forClass(Double.TYPE);
            }
            case 5: {
                return OJClass.forClass(Character.TYPE);
            }
            case 6: {
                return OJClass.forClass(String.class);
            }
            case 7: {
                return OJClass.forName("<type>null");
            }
            default: {
                System.err.println("unknown literal : " + this.toString());
                return null;
            }
        }
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    static {
        Literal.constantTrue_ = null;
        Literal.constantFalse_ = null;
        Literal.constantNull_ = null;
        Literal.constantEmptyString_ = null;
        Literal.constantZero_ = null;
        Literal.constantOne_ = null;
    }
}
