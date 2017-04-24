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

public class NorthPanel<S extends GameState<S, A>, A extends GameAction<S, A>> extends JPanel{

	private static final long serialVersionUID = -8845489814772928993L;

	public JButton createButton(String image, GameTable<S, A> game) {
		JButton b = new JButton();
		b.setIcon(new ImageIcon("src/main/resources/" + image + ".png"));
		b.addActionListener((e) -> {
			switch(image){
			case "dice":
				//hacer mov aleatorio
				break;
			case "nerd":
				//mov smart
				break;
			case "restart":
				game.stop();
				game.start();
				System.out.println("Claro que si, guapi :)"); //Mensaje debug
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
		b.setPreferredSize(new Dimension(45, 45));
		return b;
	}
	
	public NorthPanel(GameTable<S, A> game){
	// Buttons
			JButton bRandom = createButton("dice", game);
			JButton bSmart = createButton("nerd", game);
			JButton bRestart = createButton("restart", game);
			JButton bExit = createButton("exit", game);
			
			JPanel buttons = new JPanel();
			buttons.add(bRandom);
			buttons.add(bSmart);
			buttons.add(bRestart);
			buttons.add(bExit);
	
	// Player Mode
			String modes[] = {"Manual", "Random", "Smart"};
			JComboBox< String > mode = new JComboBox<String>(modes);
			mode.setSelectedIndex(0);
			
			JLabel modeText = new JLabel("Player Mode: ");
			
			JPanel plMode = new JPanel();
			plMode.add(modeText);
			plMode.add(mode);
	
	// North Panel
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			this.add(buttons);
			this.add(plMode);
	}
}
