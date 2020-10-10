// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.tools.parser.Token;
import openjava.ptree.Leaf;
import openjava.ptree.ParseTree;

public final class TokenRule extends AbstractSyntaxRule
{
    private int tokenID;
    
    public TokenRule(final int tokenID) {
        this.tokenID = tokenID;
    }
    
    @Override
    public final ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        final Token nextToken = tokenSource.getNextToken();
        if (nextToken.kind != this.tokenID) {
            throw new SyntaxException("un expected token");
        }
        return new Leaf(nextToken.kind, nextToken.image, nextToken.beginLine, nextToken.beginColumn);
    }
}
