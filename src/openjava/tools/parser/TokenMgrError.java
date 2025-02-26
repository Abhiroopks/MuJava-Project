// 
// Decompiled by Procyon v0.5.36
// 

package openjava.tools.parser;

public class TokenMgrError extends Error
{
    private static final long serialVersionUID = 1L;
    static final int LEXICAL_ERROR = 0;
    static final int STATIC_LEXER_ERROR = 1;
    static final int INVALID_LEXICAL_STATE = 2;
    static final int LOOP_DETECTED = 3;
    int errorCode;
    
    protected static final String addEscapes(final String s) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); ++i) {
            switch (s.charAt(i)) {
                case '\0': {
                    break;
                }
                case '\b': {
                    sb.append("\\b");
                    break;
                }
                case '\t': {
                    sb.append("\\t");
                    break;
                }
                case '\n': {
                    sb.append("\\n");
                    break;
                }
                case '\f': {
                    sb.append("\\f");
                    break;
                }
                case '\r': {
                    sb.append("\\r");
                    break;
                }
                case '\"': {
                    sb.append("\\\"");
                    break;
                }
                case '\'': {
                    sb.append("\\'");
                    break;
                }
                case '\\': {
                    sb.append("\\\\");
                    break;
                }
                default: {
                    final char char1;
                    if ((char1 = s.charAt(i)) < ' ' || char1 > '~') {
                        final String string = "0000" + Integer.toString(char1, 16);
                        sb.append("\\u" + string.substring(string.length() - 4, string.length()));
                        break;
                    }
                    sb.append(char1);
                    break;
                }
            }
        }
        return sb.toString();
    }
    
    protected static String LexicalError(final boolean b, final int n, final int i, final int j, final String s, final char c) {
        return "Lexical error at line " + i + ", column " + j + ".  Encountered: " + (b ? "<EOF> " : ("\"" + addEscapes(String.valueOf(c)) + "\"" + " (" + (int)c + "), ")) + "after : \"" + addEscapes(s) + "\"";
    }
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    
    public TokenMgrError() {
    }
    
    public TokenMgrError(final String message, final int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public TokenMgrError(final boolean b, final int n, final int n2, final int n3, final String s, final char c, final int n4) {
        this(LexicalError(b, n, n2, n3, s, c), n4);
    }
}
