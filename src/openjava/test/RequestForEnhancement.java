// 
// Decompiled by Procyon v0.5.36
// 

package openjava.test;

import java.lang.annotation.Annotation;

public @interface RequestForEnhancement {
    String synopsis();
    
    String engineer() default "[unassigned]";
    
    String date() default "[unimplemented]";
    
    int id();
}
