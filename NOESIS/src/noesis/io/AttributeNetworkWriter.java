package noesis.io;

// Title:       Attribute network writer
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.io.IOException;

import noesis.Network;

public abstract class AttributeNetworkWriter implements NetworkWriter
{
	@Override
	public abstract void write(Network net) throws IOException;
}
