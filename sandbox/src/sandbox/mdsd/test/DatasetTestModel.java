package sandbox.mdsd.test;

import ikor.math.Decimal;
import ikor.model.data.ColorModel;
import ikor.model.data.DataModel;
import ikor.model.data.Dataset;
import ikor.model.data.DateModel;
import ikor.model.data.DecimalModel;
import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;
import ikor.model.data.TextModel;
import ikor.model.ui.Application;
import ikor.model.ui.DatasetComponent;
import ikor.model.ui.DatasetEditor;
import ikor.model.ui.DatasetViewer;
import ikor.model.ui.UIModel;

import java.awt.Color;
import java.util.Date;


public class DatasetTestModel extends UIModel
{
	public DatasetTestModel (Application app, boolean editable)
	{
		super(app, (editable? "Dataset editor...": "Dataset viewer..." ));
	
		SampleDataset dataset = new SampleDataset();

		setIcon( app.url("chart.png") );

		if (editable) { // Data editor
			
			DatasetEditor control = new DatasetEditor("Dataset editor", dataset.getModel());
			setupDatasetComponent(control,dataset);
			add(control);
			
		} else {  // Data viewer
			
			DatasetViewer control = new DatasetViewer("Data viewer", dataset.getModel());
			setupDatasetComponent(control,dataset);			
			add(control);
		}
		
	}
	
	public void setupDatasetComponent (DatasetComponent component, SampleDataset dataset)
	{
		component.setData(dataset);

		for (int i=0; i<dataset.getColumnCount(); i++)
			component.addHeader( dataset.getName(i));
	}

	
	public class SampleDataset extends Dataset
	{
		Object data[][] = new Object[][]{ 
			new Object[]{ 123, 321, 123.321, new Decimal("123.400"), new Date(), "X", new Color(192,0,0)},
			new Object[]{ 456,  65, 123.43,  new Decimal("123.450"), new Date(), "Y", new Color(0,192,0)}, 
			new Object[]{ 789,   9, 123.4,   new Decimal("123.456"), new Date(), "Z", new Color(0,0,192)} 
		};
		
		DataModel models[] = new DataModel[] {
		    new IntegerModel(),
		    new IntegerModel(),
		    new RealModel(),
		    new DecimalModel(),
		    new DateModel(),
		    new TextModel(),
		    new ColorModel()
		};
		
		String names[] = new String[] {
			"intField1",
			"intField2",
			"realField",
			"decimalField",
			"dateField",
			"textField",
			"colorField"
		};


		public String getName(int column) 
		{
			return names[column];
		}
		
		@Override
		public DataModel getModel(int column) 
		{
			return models[column];
		}

		@Override
		public Object get(int row, int column) 
		{
			return data[row][column];
		}

		@Override
		public void set(int row, int column, Object object) 
		{
			data[row][column] = object;
		}

		@Override
		public int getColumnCount() 
		{
			return models.length;
		}

		@Override
		public int getRowCount() 
		{
			return data.length;
		}

		
	}
	
	
}
