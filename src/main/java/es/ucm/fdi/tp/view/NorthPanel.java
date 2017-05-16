package es.ucm.fdi.tp.view;

import java.awt.FlowLayout;
import java.util.logging.*;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.view.PlayerUI.PlayerMode;

/**
 * North panel of the window. Contains four buttons and one comboBox to change the mode
 * @author Claudia Guerrero García-Heras and Rafael Herrera Troca
 * @version 1 (03/05/2017)
 * @param <S> GameState of the game played
 * @param <A> GameAction of the game played
 */
public class NorthPanel<S extends GameState<S, A>, A extends GameAction<S, A>> extends JPanel implements GameObserver<S, A>{

	private static final long serialVersionUID = -8845489814772928993L;
	
	/**
	 * Listener to the actions made in this panel
	 * @author Claudia Guerrero García-Heras and Rafael Herrera Troca
	 * @version 1 (03/05/2017)
	 */
	public interface NorthPanelListener{
		public void makeRandomMove();
		public void makeSmartMove();
		public void restartGame();
		public void closeGame();
		public void changePlayerMode(PlayerMode p);
		public void sendMessage(String s);
	}
	
	private Logger log;
	private ButtonUI bRandom;
	private ButtonUI bSmart;
	private ButtonUI bRestart;
	private ButtonUI bExit;
	private JComboBox<String> mode;
	private NorthPanelListener listener;
	private int id;

	public NorthPanel(int id, NorthPanelListener listener) {
		
		String modes[] = { "Manual", "Random", "Smart" };

		this.log = Logger.getLogger("log");
		this.listener = listener;
		this.id = id;
		this.bRandom = new ButtonUI();
		this.bSmart = new ButtonUI();
		this.bRestart = new ButtonUI();
		this.bExit = new ButtonUI();

		this.mode = new JComboBox<String>(modes);
		
		initGUI();

	}

	/**
	 * Initializes the north panel with the default values
	 */
	private void initGUI() {
		// Buttons
		bRandom = new ButtonUI("dice", "Make random movement", (e)->{
			log.info("Player " + id + " clicked random move");
			listener.makeRandomMove();
		});
		bSmart = new ButtonUI("nerd", "Make smart movement", (e)->{
			log.info("Player " + id + " clicked smart move");
			listener.makeSmartMove();
		});
		bRestart = new ButtonUI("restart", "Restart game", (e)->{
			log.info("Player " + id + " clicked restart");
			listener.restartGame();
		});
		bExit = new ButtonUI("exit", "Close game", (e)->{
			log.info("Player " + id + " clicked exit");
			listener.closeGame();
		});

		JPanel buttons = new JPanel();
		buttons.add(bRandom);
		buttons.add(bSmart);
		buttons.add(bRestart);
		buttons.add(bExit);

		// Player Mode
		mode.setSelectedIndex(0);
		mode.addActionListener((e) -> {
			PlayerUI.PlayerMode pMode = null;
			switch (mode.getSelectedIndex()) {
			case 0:
				pMode = PlayerUI.PlayerMode.Manual;
				break;
			case 1:
				pMode = PlayerUI.PlayerMode.Random;
				break;
			case 2:
				pMode = PlayerUI.PlayerMode.Smart;
				break;
			}
			log.info("Player " + id + " changed mode to " + mode.getSelectedItem() + '.');
			listener.changePlayerMode(pMode);
			listener.sendMessage("You have changed to mode " + mode.getSelectedItem() + '.');
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
	
	public void changeManual() {
		mode.setSelectedIndex(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		switch(e.getType()){
		case Start:
		case Change:
			bRandom.setEnabled(e.getState().getTurn() == id);
			bSmart.setEnabled(e.getState().getTurn() == id );
			break;
		case Stop:
			bRandom.setEnabled(false);
			bSmart.setEnabled(false);
			break;
		default:
			break;
		}
	}
}