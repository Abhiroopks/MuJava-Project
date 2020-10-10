// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.ParseTree;
import openjava.ptree.Expression;
import openjava.mop.Environment;
import openjava.ptree.ExpressionList;

public final class ExpressionListRule extends SeparatedListRule
{
    private ExpressionList exprList;
    
    public ExpressionListRule(final ExpressionRule expressionRule, final boolean b) {
        super(expressionRule, 86, b);
        this.exprList = null;
    }
    
    public ExpressionListRule(final ExpressionRule expressionRule) {
        this(expressionRule, false);
    }
    
    public ExpressionListRule(final Environment environment, final boolean b) {
        this(new ExpressionRule(environment), b);
    }
    
    public ExpressionListRule(final Environment environment) {
        this(environment, false);
    }
    
    @Override
    protected void initList() {
        this.exprList = new ExpressionList();
    }
    
    @Override
    protected void addListElement(final Object o) {
        this.exprList.add((Expression)o);
    }
    
    @Override
    protected ParseTree getList() {
        return this.exprList;
    }
}
