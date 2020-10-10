// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Box1<T>
{
    private T t;
    List<? extends Number> ln;
    
    public void add(final T t) {
        this.t = t;
    }
    
    public T get() {
        return this.t;
    }
    
    public <U extends Number> void inspect(final U u) {
        System.out.println("T: " + this.t.getClass().getName());
        System.out.println("U: " + u.getClass().getName());
    }
    
    public static double sumOfList(final List<? extends Number> list) {
        double s = 0.0 + Math.abs(9);
        for (final Number n : list) {
            s += n.doubleValue();
        }
        return s;
    }
    
    public static void printList(final List<?> list) {
        for (final Object elem : list) {
            System.out.print(elem + " ");
        }
        System.out.println(3.141592653589793);
    }
    
    public static void addNumbers(final List<? super Integer> list) {
        for (int i = 1; i <= list.size(); ++i) {
            list.add(i);
        }
    }
    
    public static void printSpaced(@RequestForEnhancement(id = 2868724, synopsis = "Enable time-travel", engineer = "Mr. Peabody", date = "4/1/3007") final Character... objects) {
        for (final Object o : objects) {
            System.out.print(o + " ");
        }
    }
    
    public static void printSpaced1(final Character... objects) {
        for (final Object o : objects) {
            System.out.print(o + " ");
        }
    }
    
    public static void main(final String[] args) {
        final Box1<Integer> integerBox = new Box1<Integer>();
        integerBox.add(new Integer(10));
        integerBox.inspect(new Integer(10));
        final List<Integer> li = Arrays.asList(1, 2, 3);
        final List<String> ls = Arrays.asList("one", "two", "three");
        printList(li);
        printList(ls);
        printSpaced('t');
    }
    
    public class InnerBox<T extends Comparable<T>>
    {
        public int countGreaterThan(final T[] anArray, final T elem) {
            int count = 0;
            for (final T e : anArray) {
                if (e.compareTo(elem) > 0) {
                    ++count;
                }
            }
            return count;
        }
    }
}
