package test.ikor.model.graphics;

import ikor.model.graphics.DrawingTooltipProvider;

public class TestTooltipProvider implements DrawingTooltipProvider
{
	@Override
	public String get(String id) 
	{
		return id;
	}
}
