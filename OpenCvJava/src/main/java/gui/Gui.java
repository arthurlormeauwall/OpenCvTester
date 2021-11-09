package gui;



import java.util.Stack;

import javax.swing.JFrame;

import baseClasses.filter.Filter;
import gui.widget.FilterWidget;
import gui.widget.LayerWidget;
import gui.widget.LayerWindow;
import renderingEngine.Layer;
import userFilters.BlueGreenRedMultiplierFilter;


public class Gui extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	protected ChainOfLayerControllers chainOfLayerWidgets;
	protected GuiManager guiManager;
	
	public Gui(GuiManager guiManager) {
		super("OpenCV tester");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.guiManager=guiManager;		
		
		Stack<String> names= new Stack<String>();
		names.push("BGR Multiplier");
		Layer layer= guiManager.createAndAddLayer(0, 0, names);
		
		LayerWindow test = new LayerWindow(layer, guiManager);
		test.setVisible(true);
	}

	public void addFilterWidgetInLayerWidget(FilterController filterWidget) {
		
	}

	public void delFilterWidgetInLayerWidget(FilterController filterWidget) {
		// TODO Auto-generated method stub
		
	}

	public void addLayerWidget(LayerController layerWidget) {
		// TODO Auto-generated method stub
		
	}

	public void delLayerWidget(LayerController layerWidget) {
		// TODO Auto-generated method stub
		
	}

	public void updateOpacityValue(int layerIndex, int opacity) {
		// TODO Auto-generated method stub
		
	}

	public void updateParametersValues(Filter filter) {
		// TODO Auto-generated method stub
		
	}
}
