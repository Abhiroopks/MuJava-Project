// 
// Decompiled by Procyon v0.5.36
// 

package openjava.syntax;

import openjava.tools.parser.Token;
import openjava.mop.Environment;

public class RestorableTokenSource implements TokenSource
{
    private TokenSource tokenSource;
    private Environment env;
    private int pointer;
    private int offset;
    
    public RestorableTokenSource(final TokenSource tokenSource, final Environment env) {
        this.pointer = 0;
        this.offset = 0;
        this.tokenSource = tokenSource;
        this.env = env;
    }
    
    public RestorableTokenSource(final TokenSource tokenSource) {
        this(tokenSource, null);
    }
    
    @Override
    public Environment getEnvironment() {
        return (this.env == null) ? this.tokenSource.getEnvironment() : this.env;
    }
    
    public void assume() {
        this.offset = this.pointer;
    }
    
    public void restore() {
        this.pointer = this.offset;
    }
    
    public void fix() {
        for (int i = this.offset; i < this.pointer; ++i) {
            this.tokenSource.getNextToken();
        }
        this.pointer = 0;
        this.offset = 0;
    }
    
    @Override
    public Token getNextToken() {
        return this.tokenSource.getToken(++this.pointer);
    }
    
    @Override
    public Token getToken(final int n) {
        return this.tokenSource.getToken(this.pointer + n);
    }
}
