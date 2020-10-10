// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

public final class Flower
{
    public static void main(final String[] arguments) {
        final int tulip = 2;
        assert tulip != 3 && tulip > 0 : "list variable is null or empty";
        final int days = 5;
        assert days % 7 == 6 : days;
        assert hasValidState() : "Construction failed - not valid state.";
        final int fLength = 2;
        final int oldLength = 3;
        assert fLength > oldLength;
        int originalState = 2;
        assert (originalState = 3) != 5;
        assert isValidSpecies(2) == isValidLength(fLength);
        final int result = 5;
        assert result > 0 : result;
    }
    
    private static boolean isValidSpecies(final int i) {
        return false;
    }
    
    private static boolean isValidLength(final int fLength) {
        return false;
    }
    
    private static boolean hasValidState() {
        return false;
    }
}
