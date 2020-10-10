// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.tools.parser.Token;
import openjava.ptree.Variable;
import openjava.ptree.ParseTree;

public class IdentifierRule extends AbstractSyntaxRule
{
    private String specifying;
    
    public IdentifierRule(final String specifying) {
        this.specifying = specifying;
    }
    
    public IdentifierRule() {
        this(null);
    }
    
    @Override
    public final ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        return this.consumeIdentifier(tokenSource);
    }
    
    public Variable consumeIdentifier(final TokenSource tokenSource) throws SyntaxException {
        final Token nextToken = tokenSource.getNextToken();
        if (nextToken.kind != 76) {
            throw new SyntaxException("needs Identifier");
        }
        if (this.specifying != null && !this.specifying.equals(nextToken.image)) {
            throw new SyntaxException("needs " + this.specifying);
        }
        return new Variable(nextToken.image);
    }
}
