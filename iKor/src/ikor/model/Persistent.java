package ikor.model;

// Title:       Persistent
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.lang.annotation.*;


// Persistent
// --------------------------------

// v1.0 - 16/08/2006

/**
 * Persistent
 *
 * @author Fernando Berzal
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Persistent {
    
    String id() default "";
    
    DataType type() default DataType.Undefined;
    
    int size() default 0;
    
    int precision() default 0;
    
    boolean nullable() default true;

}
