package noesis;

import ikor.collection.CollectionFactory;
import ikor.collection.List;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import ikor.parallel.Task;

import java.lang.reflect.Field;

/**
 * NOESIS Task
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 * @param <T> Task result type
 */
public abstract class NoesisTask<T> extends Task<T>
{	
	// Callable interface
	
	@Override
	public final T call() 
	{
		compute();
		
		return getResult();
	}
	
	public abstract void compute ();
	
	
	// Task metadata
		
	public String getName ()
	{
		Class type = this.getClass();
		Label label = (Label) type.getAnnotation(Label.class);
				
		if (label!=null)
			return label.value();
		else
			return null;
	}
	
	public String getDescription()
	{
		Class type = this.getClass();
		Description description = (Description) type.getAnnotation(Description.class);
		
		if (description!=null)
			return description.value();
		else
			return null;
	}
	
	
	public ParameterMetadata[] getParameters ()
	{		
		Class type = this.getClass();
		List<ParameterMetadata> parameters = CollectionFactory.createList();
		
		getParameters(type, parameters);
		
		ParameterMetadata[] array = new ParameterMetadata[parameters.size()];
		
		for (int i=0; i<parameters.size(); i++)
			array[i] = parameters.get(i);
		
		return array;
	}

	private void getParameters (Class type, List<ParameterMetadata> parameters) 
	{
		for (Field field: type.getDeclaredFields()) {
			
			Parameter annotation = field.getAnnotation(Parameter.class);
			
			if (annotation!=null)
				parameters.add(new ParameterMetadata(field));
		}
		
		if (NoesisTask.class.isAssignableFrom(type.getSuperclass()))
			getParameters(type.getSuperclass(), parameters);
	}	
}
