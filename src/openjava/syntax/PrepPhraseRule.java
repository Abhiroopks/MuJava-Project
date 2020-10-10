// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.ptree.ParseTree;

public class PrepPhraseRule extends AbstractSyntaxRule
{
    private String prep;
    private SyntaxRule words;
    
    public PrepPhraseRule(final String prep, final SyntaxRule words) {
        this.prep = prep;
        this.words = words;
    }
    
    @Override
    public ParseTree consume(final TokenSource tokenSource) throws SyntaxException {
        new IdentifierRule(this.prep).consume(tokenSource);
        return this.words.consume(tokenSource);
    }
}
