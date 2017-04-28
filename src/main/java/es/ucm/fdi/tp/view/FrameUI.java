package es.ucm.fdi.tp.view;

import java.awt.Dimension;

import javax.swing.JFrame;

public class FrameUI extends JFrame {

	private static final long serialVersionUID = 347350631824174741L;

	public FrameUI(String title) {
		super(title);
		initGUI();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setMinimumSize(new Dimension(450, 350));
		setResizable(true);
		setVisible(true);
	}
	
	
	
}
