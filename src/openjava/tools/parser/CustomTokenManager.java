// 
// Decompiled by Procyon v0.5.36
// 

package openjava.tools.parser;

import openjava.mop.Environment;
import openjava.syntax.TokenSource;

public final class CustomTokenManager extends ParserTokenManager implements TokenSource
{
    private Parser parser;
    private Environment env;
    int pointer;
    int offset;
    
    public CustomTokenManager(final Parser parser, final Environment env) {
        super(null);
        this.pointer = 0;
        this.offset = 0;
        this.parser = parser;
        this.env = env;
    }
    
    @Override
    public Environment getEnvironment() {
        return this.env;
    }
    
    public void assume() {
        this.offset = this.pointer;
    }
    
    public void restore() {
        this.pointer = this.offset;
    }
    
    public void fix() {
        for (int i = this.offset; i < this.pointer; ++i) {
            this.parser.getNextToken();
        }
        this.pointer = 0;
        this.offset = 0;
    }
    
    @Override
    public Token getNextToken() {
        return this.parser.getToken(++this.pointer);
    }
    
    @Override
    public Token getToken(final int n) {
        return this.parser.getToken(this.pointer + n);
    }
}
