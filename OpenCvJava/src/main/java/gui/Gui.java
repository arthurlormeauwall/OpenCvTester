package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import baseClasses.filter.Filter;

public class Gui extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	protected ChainOfLayerWidgets chainOfLayerWidgets;
	protected GuiManager guiManager;
	
	public Gui(GuiManager guiManager) {
		super("My first swing app");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(400,400));
		this.guiManager=guiManager;
		this.setVisible(true);
	
	}

	public void addFilterWidgetInLayerWidget(FilterWidget filterWidget) {
		
	}

	public void delFilterWidgetInLayerWidget(FilterWidget filterWidget) {
		// TODO Auto-generated method stub
		
	}

	public void addLayerWidget(LayerWidget layerWidget) {
		// TODO Auto-generated method stub
		
	}

	public void delLayerWidget(LayerWidget layerWidget) {
		// TODO Auto-generated method stub
		
	}

	public void updateOpacityValue(int layerIndex, int opacity) {
		// TODO Auto-generated method stub
		
	}

	public void updateParametersValues(Filter filter) {
		// TODO Auto-generated method stub
		
	}
}
