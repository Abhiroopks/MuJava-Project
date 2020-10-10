// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.Statement;
import openjava.ptree.ParseTree;
import openjava.mop.Environment;

public class StatementRule extends AbstractSyntaxRule
{
    private Environment env;
    
    public StatementRule(final Environment env) {
        this.env = env;
    }
    
    public StatementRule() {
        this(null);
    }
    
    @Override
    public final ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        return this.consumeStatement(tokenSource);
    }
    
    public Statement consumeStatement(final TokenSource tokenSource) throws SyntaxException {
        final Statement consumeStatement = JavaSyntaxRules.consumeStatement(tokenSource, this.env);
        if (consumeStatement == null) {
            throw JavaSyntaxRules.getLastException();
        }
        return consumeStatement;
    }
}
