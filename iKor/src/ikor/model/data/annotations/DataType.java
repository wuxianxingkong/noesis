package ikor.model.data.annotations;

// Title:       DataType
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

// DataType
// --------------------------------

// v1.0 - 16/08/2006

/**
 * Data type
 *
 * @author Fernando Berzal
 */

public enum DataType {
    Undefined,
    String,  // unspecified size = TEXT
    Integer,
    Float,
    Number,
    Date,
    Blob     // Binary Large OBject (e.g. File, Image...)    
}
