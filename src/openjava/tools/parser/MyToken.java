// 
// Decompiled by Procyon v0.5.36
// 

package openjava.tools.parser;

public class MyToken extends Token
{
    int realKind;
    
    public MyToken(final int kind, final String image) {
        this.realKind = 126;
        this.kind = kind;
        this.image = image;
    }
    
    public static final Token newToken(final int n, final String s) {
        return new MyToken(n, s);
    }
}
