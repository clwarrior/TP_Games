package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;
import es.ucm.fdi.tp.view.NorthPanel.NorthPanelListener;
import es.ucm.fdi.tp.view.RightPanel.RightPanelListener;
import es.ucm.fdi.tp.view.BoardUI.BoardListener;
import es.ucm.fdi.tp.view.SmartPanel.SmartPanelListener;

/**
 * Class in charge of the visual components on GUI mode for a given player.
 * @author Claudia Guerrero García-Heras and Rafael Herrera Troca
 * @version 2 (03/05/2017)
 */
public abstract class PlayerUI<S extends GameState<S, A>, A extends GameAction<S, A>>{
	
	/**
	 * Enumerates the different posible player modes
	 */
	public enum PlayerMode {
		Smart, Random, Manual;
	}
	
	private GameTable<S, A> game;
	private RandomPlayer rPlayer;
	private SmartPlayer sPlayer;
	protected int id;
	private PlayerMode playerMode;
	
	private FrameUI jf;
	private RightPanel<S, A> rPanel;
	private NorthPanel<S, A> nPanel;
	private SmartPanel sPanel;
	private BoardUI<S, A> board;
	
	/**
	 * The constructor with parameters creates a window and adds all the necessary components to play 
	 * by means of calling other methods and creating objects from other classes.
	 * @param game A given GameTable of the game to play
	 * @param name Name of the player
	 * @param id Identifier number for the player
	 * @param position 
	 */
	public PlayerUI(GameTable<S, A> game, String name, int id, int position){

		S state = game.getState();
		ColorModel cm = new ColorTableUI().new ColorModel(state.getPlayerCount());
		
		this.id = id;
		this.game = game;
		this.rPlayer = new RandomPlayer(name);
		this.rPlayer.join(id);
		this.sPlayer = new SmartPlayer(name, 5);
		this.sPlayer.join(id);
		this.playerMode = PlayerMode.Manual;
		this.jf = createJFrame(game, name, position);
		this.rPanel = new RightPanel<S, A>(state.getPlayerCount(), cm, new RightPanelListener(){
			public void changeColor(){
				jf.repaint();
			}
		}, id);
		this.nPanel = new NorthPanel<S, A>(id, new NorthPanelListener(){
			public void makeRandomMove() {
				if (id == game.getState().getTurn()) {
					rPanel.addMessage("You have requested a random move.");
					randomMove();
				}
			}
			public void makeSmartMove() {
				if (id == game.getState().getTurn()) {
					rPanel.addMessage("You have requested a smart move.");
					smartMove();
				}
			}
			public void restartGame() {
				if(!game.isStopped())
					game.stop();
				game.start();
			}
			public void closeGame(){
				int answer = JOptionPane.showOptionDialog
						(new JFrame(), "Do you really want to exit?", "Confirm Exit", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, new String[]{"Yes, I want to leave", "No, I'd rather stay"}, new String("Yes, I want to leave"));
				if(answer == JOptionPane.YES_OPTION)
					System.exit(0);
			}
			public void changePlayerMode(PlayerMode p) {
				playerMode = p;
				autoMove();
			}
			public void sendMessage(String message) {
				rPanel.addMessage(message);
			}
		});
		this.sPanel = new SmartPanel(id, new SmartPanelListener(){

			@Override
			public void changeNumThreads() {
				
			}

			@Override
			public void changeTime() {
				
			}

			@Override
			public void stopSearch() {
				
			}
			
		});
		this.board = createBoard(id, cm, state, new BoardListener<S, A>(){
			public void makeManualMove(A a) {
				if (id == game.getState().getTurn()){
					game.execute(a);
				}
			}

			@Override
			public void sendMessage(String message) {
				rPanel.addMessage(message);
			}
		});		
		
		jf.add(board, BorderLayout.CENTER);
		jf.add(rPanel, BorderLayout.EAST);
		JPanel auxNorth = new JPanel();
		auxNorth.add(nPanel, BorderLayout.WEST);	
		auxNorth.add(sPanel, BorderLayout.EAST);
		jf.add(auxNorth, BorderLayout.NORTH);
		jf.setVisible(true);
		
		
		game.addObserver(rPanel);
		game.addObserver(nPanel);
		game.addObserver(board);
		game.addObserver((e) -> autoMove());
	}
	
	/**
	 * Makes a random or a smart move if player mode is the proper one in each case and it's the turn of 
	 * the player
	 */
	private void autoMove(){
		if(game != null && game.getState().getTurn() == id && !game.getState().isFinished()){
			switch(playerMode){
			case Random:
				randomMove();
				break;
			case Smart:
				smartMove();
				break;
				default: break;
			}
		}
	}
	 /**
	  * Abstract method that given a gameTable with the parameters of the game which is going to be played and 
	  * a name, creates a JFrame to held it
	 * @param position 
	  */
	public abstract FrameUI createJFrame(GameTable<S, A> ctrl, String name, int position);
	public abstract BoardUI<S, A> createBoard(int id, ColorModel cm, S s, BoardListener<S, A> listener);
	
	public PlayerMode getPlayerMode(){
		return playerMode;
	}
	
	public int getPlayerId(){
		return id;
	}
	
	/**
	 * Method that makes a random move
	 */
	public void randomMove(){
		A a = rPlayer.requestAction(game.getState());
		board.nullSelected();
		SwingUtilities.invokeLater(()->{
			game.execute(a);
		});
		rPanel.addMessage("Turn of player " + (id + 1) % 2);
	}
	
	/**
	 * Method that makes a smart move
	 */
	public void smartMove(){
		A a = sPlayer.requestAction(game.getState());
		board.nullSelected();
		SwingUtilities.invokeLater(()->{
			game.execute(a);
		});
		rPanel.addMessage("Turn of player " + (id + 1) % 2);
	}
}
