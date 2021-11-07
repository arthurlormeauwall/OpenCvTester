package gui;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import baseClasses.filter.Filter;
import baseClasses.filter.FilterControlledByFloat;
import gui.widget.FilterWidget;
import gui.widget.LabelledSlider;
import userFilters.BlueGreenRedMultiplierFilter;

public class Gui extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	protected ChainOfLayerWidgets chainOfLayerWidgets;
	protected GuiManager guiManager;
	
	public Gui(GuiManager guiManager) {
		super("OpenCV tester");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.guiManager=guiManager;
		
		guiManager.addFilterInDatabase("BGRMult", new BlueGreenRedMultiplierFilter());
		
		FilterControlledByFloat testFilter=  new BlueGreenRedMultiplierFilter(); 

		FilterWidget test= new FilterWidget(testFilter);
		add(test);
        this.setVisible(true);
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
