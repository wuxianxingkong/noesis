package sandbox.mdsd.data;

import ikor.collection.Dictionary;
import ikor.collection.DynamicDictionary;

import ikor.math.Decimal;

import java.util.Date;

public class DataModelFactory 
{
	Dictionary<Class,Class> models;
	
	// Constructor
	
	public DataModelFactory()
	{
		models = new DynamicDictionary<Class,Class>();
		
		models.set(String.class, TextModel.class);
		models.set(Integer.class, IntegerModel.class);
		models.set(Long.class, IntegerModel.class);
		models.set(Short.class, IntegerModel.class);
		models.set(Byte.class, IntegerModel.class);
		models.set(Float.class, RealModel.class);
		models.set(Double.class, RealModel.class);
		models.set(Decimal.class, DecimalModel.class);
		models.set(Boolean.class, TextModel.class);
		models.set(Date.class, DateModel.class);
		models.set(Quantity.class, QuantityModel.class);
		models.set(Dataset.class, DatasetModel.class);
	}
	
	public DataModel createDataModel (Class type)
	{
		Class     modelClass = getDataModelClass(type);
		DataModel model = null;
		
		if (type!=null) {
			
			try {
				model = (DataModel) modelClass.newInstance();
			} catch (Exception error) {
			}
		} 

		return model;
	}
	
	/**
	 * Get data model class for a given Javaclass. 
	 * If no specific data model is defined for this specific class, 
	 * then the class hierarchy is explored to find a suitable model.
	 * 
	 * @param type UI component class
	 * @return UI component factory
	 */
	
	public Class getDataModelClass (Class type)
	{
		Class model = models.get(type);
		
		if (model!=null)
			return model;
		else if (type.getSuperclass()!=null)
			return getDataModelClass(type.getSuperclass());
		else
			return null;	
	}
	
	// Static singleton interface
	
	private static DataModelFactory singleton;
	
	public static DataModelFactory getInstance ()
	{
		if (singleton==null)
			singleton = new DataModelFactory();
		
		return singleton;
	}
	
	public static DataModel create (Class type)
	{
		return getInstance().createDataModel(type);
	}

	
}
