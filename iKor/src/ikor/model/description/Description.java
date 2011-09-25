package ikor.model.description;

// Title:       Description
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.lang.annotation.*;

// Description
// --------------------------------

// v1.0 - 16/08/2006

/**
 * Description
 *
 * @author Fernando Berzal
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface Description {
    
    String value();

}
