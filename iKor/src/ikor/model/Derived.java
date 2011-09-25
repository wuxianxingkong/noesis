package ikor.model;

// Title:       Derived
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.lang.annotation.*;


// Derived
// --------------------------------

// v1.0 - 16/08/2006

/**
 * Derived
 *
 * @author Fernando Berzal
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Derived {
    
    String value();

}
