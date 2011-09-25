package test.ikor.model.mock;

// Title:       DocumentFormat
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.model.*;

// DocumentFormat
// --------------------------------

// v1.0 - 16/08/2006

@Persistent(id="FORMAT")
public class Format 
{
    @Persistent(id="id", type=DataType.Integer)
    @Key
    protected int    id;

    @Persistent(id="description", type=DataType.String, size=32)
    protected String description;
}
