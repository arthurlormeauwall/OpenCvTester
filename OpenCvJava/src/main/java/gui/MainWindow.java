package gui;



import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import baseClasses.filter.Filter;
import guiManager.ChainOfLayersManager;
import guiManager.FilterManager;
import guiManager.ActionHistoryManager;
import guiManager.LayerManager;


public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	protected ChainOfLayersManager chainOfLayerManager;
	protected ActionHistoryManager actionHistoryManager;
	protected JPanel layerPanel;
	
	public MainWindow(ActionHistoryManager actionHistoryManager) {
		super("OpenCV tester");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.actionHistoryManager=actionHistoryManager;	
		chainOfLayerManager= new ChainOfLayersManager(this);
		
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		layerPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		layerPanel.setLayout(new BoxLayout(layerPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		add(buttonPanel);
		add(layerPanel);
		
		JButton addButton = new JButton("AddLayer");
		JButton delButton = new JButton("dellayer");
		JButton undoButton = new JButton("Undo");
		JButton redoButton = new JButton("Redo");
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);
		
		 undoButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	
		      }
		    });
		 
		 redoButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	
		      }
		    });
		 
		 addButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	MainWindow.this.actionHistoryManager.createAndAddLayer(chainOfLayerManager.getNumberOfLayer(), null);
		      }
		    });
		 
		 delButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	  MainWindow.this.actionHistoryManager.deleteLayerManager(chainOfLayerManager.getLayerController(chainOfLayerManager.getNumberOfLayer()-1));	    	
		      }
		    });
		 
		this.pack();
		this.setVisible(true);
	}

	public void addFilterWidgetInLayerWidget(FilterManager filterController) {
		chainOfLayerManager.addFilterWigetInLayerWiget(filterController);
		updateGui();
	}

	public void delFilterWidgetInLayerWidget(FilterManager filterController) {
		chainOfLayerManager.deFilterWidgetInLayerWidget(filterController);	
	}

	public void addLayerManager(LayerManager layerManager) {
		chainOfLayerManager.addLayerManager(layerManager);	
	}
	
	public void deleteLayerManager(LayerManager layerManager) {
		layerManager.getLayerWindow().setVisible(false);
		chainOfLayerManager.deleteLayerManager(layerManager);	
		
	}

	public void delLayerWidget(LayerManager layerWidget) {
		// TODO Auto-generated method stub
		
	}

	public void updateOpacityValue(int layerIndex, Float opacity) {
		// TODO Auto-generated method stub
		
	}

	public void updateParametersValues(Filter filter) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateGui() {
		chainOfLayerManager.updateGui();		
		
		layerPanel.removeAll();
		
		int numberOfFiltersToAdd = chainOfLayerManager.getNumberOfLayer();
		for (int i=0;i<numberOfFiltersToAdd;i++) {
			
			layerPanel.add(chainOfLayerManager.getLayerController(i).getLayerWidget());
		}
		this.pack();
	}

	public ActionHistoryManager getGuiManager() {
		return actionHistoryManager;
	}
	

	
}


