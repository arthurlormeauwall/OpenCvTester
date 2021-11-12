package gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import actionsHistory.ActionsHistory;
import baseClasses.Id;
import baseClasses.filter.FilterControlledByFloat;
import renderingEngine.ChainOfLayers;
import renderingEngine.GroupsId;
import renderingEngine.Layer;



public class GuiManager 
{
	private ActionsHistory history;
	private ChainOfLayers chainOfLayers;
	private App app;
	private Gui gui;
	private JFrame frameWindow;
	
	public GuiManager(ChainOfLayers chainOfLayers, App app){
		this.chainOfLayers=chainOfLayers;
		this.app=app;
		history=new ActionsHistory();
		gui=app.getGui();
		
		frameWindow= new JFrame("Image");
		frameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//Encoding the image
		  MatOfByte matOfByte = new MatOfByte();
		  Imgcodecs.imencode(".jpg", chainOfLayers.getDest().getMat(), matOfByte);
		  //Storing the encoded Mat in a byte array
		  byte[] byteArray = matOfByte.toArray();
		  //Preparing the Buffered Image
		  InputStream in = new ByteArrayInputStream(byteArray);
		  BufferedImage bufImage = null;
			try {
				bufImage = ImageIO.read(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  //Instantiate JFrame
		 
		  //Set Content to the JFrame
		   frameWindow.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
		   frameWindow.pack();
		   frameWindow.setVisible(true);
	}
	
	public void setGui(Gui gui) {
		this.gui=gui;
	}
	private Id createLayerId(int layerIndex) {	
		Id id = new Id();
		id.set(layerIndex, 0, GroupsId.LAYER.ordinal());
		return id;
	}	
	
	private Id createFilterId(int layerIndex, int filterIndex) {
		Id id = new Id();
		id.set(layerIndex, filterIndex, GroupsId.FILTER.ordinal());
		return id;
	}
	
	public void addFilterInDatabase(String name, FilterControlledByFloat filter) {
		chainOfLayers.getFiltersDataBase().addFilter(name, filter);
	}
	
	public Layer createAndAddLayer (int layerIndex, Stack<String> filterNames) {	
		Stack<Id> id= new Stack<Id>();
		id.push(createLayerId(layerIndex));
		
		if (filterNames!=null) {
			for (int i=0; i< filterNames.size(); i++) {
				id.push(createFilterId(layerIndex, i));
			}
		}
		
		Layer newLayer= chainOfLayers.addLayer(id, filterNames);
		gui.addLayerController(new LayerController(newLayer, this));
		refresh();
		return newLayer;
	}
	
	public void createAndAddFilterInLayer(int layerIndex, int filterIndex, String filterName) {	
	}
	
	public void delFilterInLayer(FilterController filterWidgetToDel)  {		
	}
	
	public void deleteLayerController(LayerController layerController) {
		
		if (layerController!=null) {
			chainOfLayers.delLayer(layerController.getId());
			gui.deleteLayerController(layerController);	
			refresh();
		}

	}

	public void setOpacity(int layerIndex, Float opacity) {	
		chainOfLayers.setOpacity(layerIndex, opacity);
		refresh();
	}	
	
	public void setParameters(int layerIndex, int filterIndex, HashMap<String,Float> parametersValues){
	}	
	public void setParameters(Id id, String name, Float value) throws IOException {
		if (id.getGroupId()==GroupsId.FILTER_CHAIN.ordinal()) {
			setOpacity(id.get()[0], value);
		}
		else if (id.getGroupId()==GroupsId.FILTER.ordinal()) {
			chainOfLayers.setParameters(id, name, value);	
			refresh();
		}
		
	}
	
	private void refresh() {
		//Encoding the image
		  MatOfByte matOfByte = new MatOfByte();
		  Imgcodecs.imencode(".jpg", chainOfLayers.getDest().getMat(), matOfByte);
		  //Storing the encoded Mat in a byte array
		  byte[] byteArray = matOfByte.toArray();
		  //Preparing the Buffered Image
		  InputStream in = new ByteArrayInputStream(byteArray);
		  BufferedImage bufImage = null;
			try {
				bufImage = ImageIO.read(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  //Instantiate JFrame
		 
		  //Set Content to the JFrame
		   frameWindow.getContentPane().removeAll();
		   frameWindow.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
		   frameWindow.pack();
		   frameWindow.setVisible(true);
		
	}
	public void setBypass(int layerIndex, int filterIndex, Boolean bypass) {
	}
	
	public void undo() {
		history.undo();	
	}
	
	public void redo() {
		history.redo();
	}
	
	public void store() {	
	}
	
	public void play() {
		chainOfLayers.getDest().play();
		 /* */
	}
	
}
