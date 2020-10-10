// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

public enum Planet
{
    MERCURY(3.303E23, 2439700.0) {
        public String haha;
        
        {
            this.haha = "";
        }
        
        public double mass() {
            return 0.0;
        }
        
        final class class1
        {
            class haha
            {
            }
        }
    }, 
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
}
