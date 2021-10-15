package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainWin extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public MainWin() {
		super("My first swing app");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(400,400));
	
	}

}
