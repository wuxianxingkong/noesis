package sandbox.mdsd.graphics.test;

import sandbox.mdsd.graphics.DrawingTooltipProvider;

public class TestTooltipProvider implements DrawingTooltipProvider
{
	@Override
	public String get(String id) 
	{
		return id;
	}
}
