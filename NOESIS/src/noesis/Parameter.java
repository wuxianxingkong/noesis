package noesis;

// Title:       Parameter annotation
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.lang.annotation.*;

/**
 * Parameter annotation
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Parameter 
{    
    double min() default -Double.MAX_VALUE;
    
    double max() default Double.MAX_VALUE;
    
    double defaultValue() default 0; 
}
