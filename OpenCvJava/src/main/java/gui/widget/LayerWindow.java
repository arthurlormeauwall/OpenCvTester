package gui.widget;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import baseClasses.filter.FilterControlledByFloat;
import gui.GuiManager;
import renderingEngine.Layer;

public class LayerWindow  extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	public LayerWindow (Layer layer, GuiManager guiManager){
		super("Layer");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		
		int numberOfFiltersToAdd = layer.getNumberOfFilters()-1;
		for (int i=0;i<numberOfFiltersToAdd;i++) {
			add(new FilterWidget((FilterControlledByFloat)layer.get(i), guiManager), guiManager);
		}
		this.setVisible(true);
	}

}
