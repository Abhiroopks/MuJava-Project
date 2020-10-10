// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.ParseTree;

public abstract class AbstractSyntaxRule implements SyntaxRule
{
    private SyntaxException lastException;
    
    public AbstractSyntaxRule() {
        this.lastException = null;
    }
    
    @Override
    public abstract ParseTree consume(final TokenSource p0) throws SyntaxException;
    
    @Override
    public final boolean lookahead(final TokenSource tokenSource) {
        try {
            this.consume(new RestorableTokenSource(tokenSource));
            return true;
        }
        catch (SyntaxException syntaxException) {
            this.setSyntaxException(syntaxException);
            return false;
        }
    }
    
    @Override
    public final SyntaxException getSyntaxException() {
        return this.lastException;
    }
    
    private final void setSyntaxException(final SyntaxException lastException) {
        this.lastException = lastException;
    }
}
