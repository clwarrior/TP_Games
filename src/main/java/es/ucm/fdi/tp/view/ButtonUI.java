package es.ucm.fdi.tp.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonUI extends JButton {

	private static final long serialVersionUID = -2055819189010323522L;
	
	public ButtonUI(){}

	public ButtonUI(String image, String message, ActionListener listener) {
		setIcon(new ImageIcon("src/main/resources/" + image + ".png"));
		addActionListener(listener);
		setToolTipText(message);
		setPreferredSize(new Dimension(45, 45));
	}
}
