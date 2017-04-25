package es.ucm.fdi.tp.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.*;
import es.ucm.fdi.tp.view.GUIController.PlayerMode;

public class NorthPanel<S extends GameState<S, A>, A extends GameAction<S, A>> extends JPanel{

	private static final long serialVersionUID = -8845489814772928993L;
	
	public NorthPanel(GUIController<S, A> ctrl){
	// Buttons
			JButton bRandom = createButton("dice", "Make random movement", ctrl);
			JButton bSmart = createButton("nerd", "Make smart movement", ctrl);
			JButton bRestart = createButton("restart", "Restart game", ctrl);
			JButton bExit = createButton("exit", "Close game", ctrl);
			
			JPanel buttons = new JPanel();
			buttons.add(bRandom);
			buttons.add(bSmart);
			buttons.add(bRestart);
			buttons.add(bExit);
	
	// Player Mode
			String modes[] = {"Manual", "Random", "Smart"};
			JComboBox< String > mode = new JComboBox<String>(modes);
			mode.setSelectedIndex(0);
			mode.addActionListener((e) -> {
				PlayerMode pMode = null;
				switch(mode.getSelectedIndex()){
				case 0:
					pMode = PlayerMode.Manual;
					break;
				case 1:
					pMode = PlayerMode.Random;
					break;
				case 2:
					pMode = PlayerMode.Smart;
					break;
				}
				ctrl.changePlayerMode(pMode);
			});
			
			JLabel modeText = new JLabel("Player Mode: ");
			
			JPanel plMode = new JPanel();
			plMode.add(modeText);
			plMode.add(mode);
	
	// North Panel
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			this.add(buttons);
			this.add(plMode);
	}
	
	public JButton createButton(String image, String message, GUIController<S, A> ctrl) {
		JButton b = new JButton();
		b.setIcon(new ImageIcon("src/main/resources/" + image + ".png"));
		b.addActionListener((e) -> {
			switch(image){
			case "dice":
				ctrl.makeRandomMove();
				break;
			case "nerd":
				ctrl.makeSmartMove();
				break;
			case "restart":
				ctrl.restartGame();
				break;
			case "exit":
				int answer = JOptionPane.showOptionDialog
				(new JFrame(), "Do you really want to exit?", "Confirm Exit", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, new String[]{"Yes, I want to leave", "No, I'd rather stay"}, new String("Yes, I want to leave"));
				if(answer == JOptionPane.YES_OPTION)
					System.exit(0);
				break;
			}
		});
		b.setToolTipText(message);
		b.setPreferredSize(new Dimension(45, 45));
		return b;
	}
}
