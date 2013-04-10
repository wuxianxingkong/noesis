package ikor.model.data.annotations;

// Title:       Reference
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.lang.annotation.*;


// Reference
// --------------------------------

// v1.0 - 16/08/2006

/**
 * Reference (for maintaining bidirectional associations).
 * 
 * Please, note that the referenced type does not have to be
 * specified in *-to-many associations since Java 5 supports 
 * generic types.
 * 
 * @author Fernando Berzal
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Reference {
    
    /**
     * Inverse association
     * @return Member name
     */
    
    String value();

}
