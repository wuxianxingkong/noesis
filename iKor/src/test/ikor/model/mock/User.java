package test.ikor.model.mock;

// Title:       User
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.model.*;

// User
// --------------------------------

// v1.0 - 16/08/2006

@Persistent(id="ACCESS")
public class User 
{
    @Key
    @Persistent(id="id", type=DataType.Integer)
    protected int    id;

    @Persistent(id="login", type=DataType.String, size=16)
    protected String login;

    @Persistent(id="passwd", type=DataType.String, size=64)
    protected String password;

    @Persistent(id="access", type=DataType.String, size=16)
    protected String priviledges;
}
