// 
// Decompiled by Procyon v0.5.36
// 

package openjava.ptree;

import openjava.ptree.util.ParseTreeVisitor;

public class ExpressionList extends List
{
    private static final String LNLN;
    
    public ExpressionList() {
        super(ExpressionList.LNLN);
    }
    
    public ExpressionList(final Expression expression) {
        super(ExpressionList.LNLN, expression);
    }
    
    public ExpressionList(final Expression expression, final Expression expression2) {
        this(expression);
        this.add(expression2);
    }
    
    public ExpressionList(final Expression expression, final Expression expression2, final Expression expression3) {
        this(expression, expression2);
        this.add(expression3);
    }
    
    public Expression get(final int n) {
        return (Expression)this.contents_elementAt(n);
    }
    
    public void add(final Expression expression) {
        this.contents_addElement(expression);
    }
    
    public void set(final int n, final Expression expression) {
        this.contents_setElementAt(expression, n);
    }
    
    public Expression remove(final int n) {
        final Expression expression = (Expression)this.contents_elementAt(n);
        this.contents_removeElementAt(n);
        return expression;
    }
    
    public void insertElementAt(final Expression expression, final int n) {
        this.contents_insertElementAt(expression, n);
    }
    
    public void addAll(final ExpressionList list) {
        for (int i = 0; i < list.size(); ++i) {
            this.contents_addElement(list.get(i));
        }
    }
    
    public ExpressionList subList(final int n, final int n2) {
        final ExpressionList list = new ExpressionList();
        for (int i = n; i < n2; ++i) {
            list.add(this.get(i));
        }
        return list;
    }
    
    @Override
    public void accept(final ParseTreeVisitor parseTreeVisitor) throws ParseTreeException {
        parseTreeVisitor.visit(this);
    }
    
    static {
        LNLN = ParseTreeObject.LN + ParseTreeObject.LN;
    }
}
