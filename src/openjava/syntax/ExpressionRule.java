// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.Expression;
import openjava.ptree.ParseTree;
import openjava.mop.Environment;

public class ExpressionRule extends AbstractSyntaxRule
{
    private Environment env;
    
    public ExpressionRule(final Environment env) {
        this.env = env;
    }
    
    public ExpressionRule() {
        this(null);
    }
    
    @Override
    public final ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        return this.consumeExpression(tokenSource);
    }
    
    public Expression consumeExpression(final TokenSource tokenSource) throws SyntaxException {
        final Expression consumeExpression = JavaSyntaxRules.consumeExpression(tokenSource, this.env);
        if (consumeExpression == null) {
            throw JavaSyntaxRules.getLastException();
        }
        return consumeExpression;
    }
}
