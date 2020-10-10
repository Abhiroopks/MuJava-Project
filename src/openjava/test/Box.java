// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

public class Box
{
    public static void main(final String[] array) {
    }
    
    public class InnerBox<T extends Comparable<T>>
    {
        public int countGreaterThan(final T[] array, final T t) {
            int n = 0;
            for (int length = array.length, i = 0; i < length; ++i) {
                if (array[i].compareTo(t) > 0) {
                    ++n;
                }
            }
            return n;
        }
    }
}
