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
	
	protected ChainOfLayersManager chainOfLayerController;
	protected ActionHistoryManager guiManager;
	protected JPanel layerPanel;
	
	public MainWindow(ActionHistoryManager guiManager) {
		super("OpenCV tester");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.guiManager=guiManager;	
		chainOfLayerController= new ChainOfLayersManager(this);
		
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
		    	MainWindow.this.guiManager.createAndAddLayer(chainOfLayerController.getNumberOfLayer(), null);
		      }
		    });
		 delButton.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event)   {
		    	  MainWindow.this.guiManager.deleteLayerController(chainOfLayerController.getLayerController(chainOfLayerController.getNumberOfLayer()-1));	    	
		      }
		    });
		this.pack();
		this.setVisible(true);
	}

	public void addFilterWidgetInLayerWidget(FilterManager filterController) {
		chainOfLayerController.addFilterWigetInLayerWiget(filterController);
		updateGui();
	}

	

	public void delFilterWidgetInLayerWidget(FilterManager filterController) {
		chainOfLayerController.deFilterWidgetInLayerWidget(filterController);
		
	}

	public void addLayerController(LayerManager layerController) {
		chainOfLayerController.addLayerController(layerController);	
	}
	
	public void deleteLayerController(LayerManager layerController) {
		layerController.getLayerWindow().setVisible(false);
		chainOfLayerController.deleteLayerController(layerController);	
		
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
		chainOfLayerController.updateGui();		
		
		layerPanel.removeAll();
		
		int numberOfFiltersToAdd = chainOfLayerController.getNumberOfLayer();
		for (int i=0;i<numberOfFiltersToAdd;i++) {
			
			layerPanel.add(chainOfLayerController.getLayerController(i).getLayerWidget());
		}
		this.pack();
	}

	public ActionHistoryManager getGuiManager() {
		return guiManager;
	}
	

	
}


