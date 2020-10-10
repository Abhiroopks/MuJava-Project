// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test.stringPlay;

public class StringTools
{
    private char[] value;
    private int length;
    
    public StringTools(final String input) {
        this.value = new char[1000];
        this.length = input.length();
        for (int i = 0; i < input.length(); ++i) {
            this.value[i] = input.charAt(i);
        }
    }
    
    public int length() {
        return this.length;
    }
    
    public char getCharAt(final int i) {
        return this.value[i];
    }
}
