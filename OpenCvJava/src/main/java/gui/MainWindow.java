package gui;



import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import baseClasses.filter.Filter;
import guiManager.ChainOfLayerManagers;
import guiManager.FilterManager;
import guiManager.ActionHistoryManager;
import guiManager.LayerManager;


public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private ChainOfLayerManagers chainOfLayerManager;
	private ActionHistoryManager actionHistoryManager;
	private JPanel layerPanel;
	private JButton addButton; 
	private JButton delButton; 
	private JButton undoButton;
	private JButton redoButton;  
	
	public MainWindow(ActionHistoryManager actionHistoryManager) {
		super("OpenCV tester");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.actionHistoryManager=actionHistoryManager;	
		chainOfLayerManager= new ChainOfLayerManagers(this);
		
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		layerPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		layerPanel.setLayout(new BoxLayout(layerPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		add(buttonPanel);
		add(layerPanel);
		
		addButton = new JButton("AddLayer");
		delButton = new JButton("dellayer");
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		buttonPanel.add(addButton);
		buttonPanel.add(delButton);
		buttonPanel.add(undoButton);
		buttonPanel.add(redoButton);
		
		addListeners();
		 
		this.pack();
		this.setVisible(true);
	}
	
	public void addListeners() {
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


	public void updateOpacityValue(int layerIndex, Float opacity) {
	}

	public void updateParametersValues(Filter filter) {	
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


