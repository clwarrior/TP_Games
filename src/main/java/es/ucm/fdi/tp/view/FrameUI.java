package es.ucm.fdi.tp.view;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Class that creates a JFrame and initializes it in order to use it
 * @author Claudia Guerrero García-Heras and Rafael Herrera Troca
 * @version 2 (03/05/2017)
 */
public class FrameUI extends JFrame {

	private static final long serialVersionUID = 347350631824174741L;

	/**
	 * Constructor that given a title creates a JFrame with that name
	 * @param title Name
	 * @param position 
	 */
	public FrameUI(String title, int position) {
		super(title);
		initGUI(position);
	}
	
	/**
	 * Method that initializes the JFrame (give it a size, makes it resizable and visible...)
	 */
	private void initGUI(int position) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocation(position, 10);
		setMinimumSize(new Dimension(450, 350));
		setResizable(true);
		setVisible(true);
	}
	
	
	
}
