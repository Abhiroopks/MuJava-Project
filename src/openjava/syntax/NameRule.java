// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.tools.parser.Token;
import openjava.ptree.Variable;
import openjava.ptree.ParseTree;

public class NameRule extends AbstractSyntaxRule
{
    @Override
    public final ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        return this.consumeQualifiedName(tokenSource);
    }
    
    public Variable consumeQualifiedName(final TokenSource tokenSource) throws SyntaxException {
        final StringBuffer sb = new StringBuffer(new IdentifierRule().consumeIdentifier(tokenSource).toString());
        while (lookaheadRest(tokenSource)) {
            sb.append(tokenSource.getNextToken().image);
            sb.append(tokenSource.getNextToken().image);
        }
        return new Variable(sb.toString());
    }
    
    protected static final boolean lookaheadRest(final TokenSource tokenSource) {
        final Token token = tokenSource.getToken(1);
        final Token token2 = tokenSource.getToken(2);
        return token.kind == 87 && token2.kind == 76;
    }
}
