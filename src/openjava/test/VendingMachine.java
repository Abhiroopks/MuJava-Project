// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import java.util.Iterator;
import java.util.LinkedList;

@RequestForEnhancement(id = 2868724, synopsis = "Enable time-travel", engineer = "Mr. Peabody", date = "4/1/3007")
public class VendingMachine
{
    private int credit;
    private LinkedList<String> stockArray;
    private LinkedList<String> stock;
    private static final int MAX = 10;
    
    VendingMachine() {
        this.credit = 0;
        this.stock = new LinkedList<String>();
    }
    
    public void coin(final int coin) {
        if (coin != 10 && coin != 25 && coin != 100) {
            return;
        }
        if (this.credit >= 90) {
            return;
        }
        this.credit += coin;
    }
    
    public int getChoc(final StringBuffer choc) {
        if (this.credit < 90 || this.stock.size() <= 0) {
            final int change = 0;
            choc.replace(0, choc.length(), "");
            return change;
        }
        final int change = this.credit - 90;
        choc.replace(this.credit = 0, choc.length(), this.stock.removeFirst());
        return change;
    }
    
    public void addChoc(final String choc) {
        for (final String s : this.stockArray) {
            System.out.println(s.toString());
        }
        for (int i = 0; i < this.stock.size(); ++i) {
            System.out.println(this.stock.get(i).toString());
        }
        if (this.stock.size() >= 10) {
            return;
        }
        this.stock.add(choc);
    }
    
    public int getCredit() {
        return this.credit;
    }
    
    @Deprecated
    public LinkedList<?> getStock() {
        return this.stock;
    }
    
    public class haha
    {
    }
    
    public enum Season
    {
        Winter, 
        Spring, 
        SUMMER, 
        FALL;
    }
    
    public enum Planet
    {
        MERCURY(3.303E23, 2439700.0), 
        VENUS(4.869E24, 6051800.0), 
        EARTH(5.976E24, 6378140.0), 
        MARS(6.421E23, 3397200.0), 
        JUPITER(1.9E27, 7.1492E7), 
        SATURN(5.688E26, 6.0268E7), 
        URANUS(8.686E25, 2.5559E7), 
        NEPTUNE(1.024E26, 2.4746E7);
        
        private final double mass;
        private final double radius;
        public static final double G = 6.673E-11;
        
        private Planet(final double mass, final double radius) {
            this.mass = mass;
            this.radius = radius;
        }
        
        private double mass() {
            return this.mass;
        }
        
        private double radius() {
            return this.radius;
        }
        
        double surfaceGravity() {
            return 6.673E-11 * this.mass / (this.radius * this.radius);
        }
        
        double surfaceWeight(final double otherMass) {
            return otherMass * this.surfaceGravity();
        }
        
        public static void main(final String[] args) {
            if (args.length != 1) {
                System.err.println("Usage: java Planet <earth_weight>");
                System.exit(-1);
            }
            final double earthWeight = Double.parseDouble(args[0]);
            final double mass = earthWeight / Planet.EARTH.surfaceGravity();
            for (final Planet p : values()) {
                System.out.printf("Your weight on %s is %f%n", p, p.surfaceWeight(mass));
            }
        }
        
        public enum SecondSeason
        {
            Winter, 
            Spring, 
            SUMMER, 
            FALL;
        }
    }
}
