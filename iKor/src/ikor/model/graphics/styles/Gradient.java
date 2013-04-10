package ikor.model.graphics.styles;

import java.awt.Color;


import ikor.collection.DynamicList;
import ikor.collection.List;
import ikor.collection.ReadOnlyCollection;
import ikor.model.graphics.Style;


public abstract class Gradient extends Style 
{
	List<GradientKeyframe> keyframes;
	
	public Gradient ()
	{
		keyframes = new DynamicList<GradientKeyframe>();
	}
	
	
	public int getKeyframeCount ()
	{
		return keyframes.size();
	}
	
	public GradientKeyframe getKeyframe (int index)
	{
		return keyframes.get(index);
	}
	
	public ReadOnlyCollection<GradientKeyframe> getKeyframes ()
	{
		return keyframes;
	}
	
	public GradientKeyframe addKeyframe (float value, Color color)
	{
		GradientKeyframe keyframe = new GradientKeyframe (value,color);
		
		return addKeyframe(keyframe);
	}
	
	public GradientKeyframe addKeyframe (GradientKeyframe keyframe)
	{
		keyframes.add(keyframe);
		
		return keyframe;
	}
	
	public String toString ()
	{
		String key = "";
		
		for (GradientKeyframe keyframe: keyframes)
			key += "k" + keyframe.toString();
		
		return key;
	}
}
