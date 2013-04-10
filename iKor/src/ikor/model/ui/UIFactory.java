package ikor.model.ui;

public interface UIFactory<U extends UI, T extends Component> 
{
	public void build (U ui, T component);

}
