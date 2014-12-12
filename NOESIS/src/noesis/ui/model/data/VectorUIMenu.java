package noesis.ui.model.data;


import ikor.math.util.LinearScale;
import ikor.math.util.LogarithmicTransformation;
import ikor.model.ui.Action;
import ikor.model.ui.Application;
import ikor.model.ui.Menu;
import ikor.model.ui.Option;
import ikor.model.ui.Separator;

import java.awt.event.KeyEvent;

import noesis.ui.model.HelpMenu;
import noesis.ui.model.actions.ExitAction;

/**
 * UI menu for the display of histograms
 * 
 * @author Fernando Berzal (berzal@acm.org) & Victor Martinez (victormg@acm.org)
 */

public class VectorUIMenu extends Menu 
{
	private Menu data;
	private Menu view;
	private Menu fit;
	private Menu help;
	

	public VectorUIMenu (VectorUIModel ui)
	{
		super("NOESIS Data Menu");
		
		Application app = ui.getApplication();
		
		data = createDataMenu(app, ui);
		view = createViewMenu(app, ui);
		fit = createFitMenu(app, ui);
		help = new HelpMenu(app);
		
		this.add(data);
		this.add(view);
		this.add(fit);
		this.add(help);
	}


	// Data menu
	// ---------
	
	public Menu createDataMenu (Application app, VectorUIModel ui)
	{
		Menu data = new Menu("Data");

		data.setIcon( app.url("icons/download.png") );

		Option saveTXT =new Option("Save data...", new VectorUISaveAction(ui, "txt") );
		saveTXT.setIcon( app.url("icons/download.png") );
		data.add(saveTXT);
				
		data.add( new Separator() );
		
		Option saveSVG =new Option("Export SVG image", new VectorUISaveAction(ui, "svg") );
		saveSVG.setIcon( app.url("icons/chart.png") );
		data.add(saveSVG);

		Option savePNG=new Option("Export PNG image", new VectorUISaveAction(ui, "png") );
		savePNG.setIcon( app.url("icons/chart.png") );
		data.add(savePNG);
	    
		Option saveJPG=new Option("Export JPG image", new VectorUISaveAction(ui, "jpg") );
		saveJPG.setIcon( app.url("icons/chart.png") );
		data.add(saveJPG);
		
		data.add(new Separator());

		Option exit = new Option("Exit", new ExitAction(ui) );
		exit.setIcon( app.url("icons/exit.png") );
		data.add(exit);

		return data;
	}

	
	// View menu
	// ---------

	public Menu createViewMenu (Application app, VectorUIModel ui)
	{
		Menu view = new Menu("View");
		
		view.setIcon( app.url("icons/chart.png") );
		
		Option binIncrease = new Option ("Increase number of bins", new HistogramBinsAction(ui,+1), KeyEvent.VK_F8 );
		binIncrease.setIcon( app.url("icons/chart.png") );
		view.add(binIncrease);

		Option binDecrease = new Option ("Decrease number of bins", new HistogramBinsAction(ui,-1), KeyEvent.VK_F7 );
		binDecrease.setIcon( app.url("icons/chart.png") );
		view.add(binDecrease);

		view.add(new Separator());

		Option linearScale = new Option ("Linear scale", new HistogramScaleAction(ui,LinearScale.class) );
		linearScale.setIcon( app.url("icons/chart.png") );
		view.add(linearScale);

		Option logScale = new Option ("Logarithmic scale", new HistogramScaleAction(ui,LogarithmicTransformation.class) );
		logScale.setIcon( app.url("icons/chart.png") );
		view.add(logScale);
		
		return view;
	}
		
	
	// Fit menu
	// ---------

	public Menu createFitMenu (Application app, VectorUIModel ui)
	{
		Menu fit = new Menu("Fit");
		
		fit.setIcon( app.url("icons/calculator.png") );
		
		Option gaussian = new Option ("Fit a Gaussian distribution");
		gaussian.setIcon( app.url("icons/calculator.png") );
		gaussian.setAction( new FitNormalAction(app, ui) );
		fit.add(gaussian);
		
		Option poisson = new Option ("Fit a Poisson distribution");
		poisson.setIcon( app.url("icons/calculator.png") );
		poisson.setAction( new FitPoissonAction(app, ui) );
		fit.add(poisson);

		Option pareto = new Option ("Fit a Pareto distribution (i.e. power law)");
		pareto.setIcon( app.url("icons/calculator.png") );
		pareto.setAction( new FitParetoAction(app, ui) );
		fit.add(pareto);

		fit.add(new Separator());

		Option clear = new Option ("Clear distribution fit");
		clear.setIcon( app.url("icons/chart.png") );
		clear.setAction(new Action() {
			@Override
			public void run() {
				ui.getFigure().clearDistributions();
			}
		});
		fit.add(clear);
			
		return fit;
	}
}
