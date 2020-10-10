// 
// Decompiled by Procyon v0.5.36
// 

package openjava.tools.parser;

import openjava.syntax.TokenSource;

public final class TokenSourceAdapter extends ParserTokenManager
{
    private TokenSource token_src;
    
    public TokenSourceAdapter(final TokenSource token_src) {
        super(null);
        this.token_src = token_src;
    }
    
    @Override
    public Token getNextToken() {
        return this.token_src.getNextToken();
    }
    
    public Token getToken(final int n) {
        return this.token_src.getToken(n);
    }
}
