package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasPlayerUI;
import es.ucm.fdi.tp.was.WasState;

public abstract class PlayerUI<S extends GameState<S, A>, A extends GameAction<S, A>>{
	
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
	private BoardUI<S, A> board;
	
	public PlayerUI(GameTable<S, A> game, String name, int id){

		S state = game.getState();
		ColorModel cm = new ColorTableUI().new ColorModel(state.getPlayerCount());
		
		this.id = id;
		this.game = game;
		this.rPlayer = new RandomPlayer(name);
		this.rPlayer.join(id);
		this.sPlayer = new SmartPlayer(name, 5);
		this.sPlayer.join(id);
		this.playerMode = PlayerMode.Manual;
		this.jf = createJFrame(game, name);
		this.rPanel = new RightPanel<S, A>(state.getPlayerCount(), cm, new RightPanelListener(){
			public void changeColor(){
				jf.repaint();
			}
		}, id);
		this.nPanel = new NorthPanel<S, A>(id, new NorthPanelListener(){
			public void makeRandomMove() {
				if (id == game.getState().getTurn()) {
					randomMove();
				}
			}
			public void makeSmartMove() {
				if (id == game.getState().getTurn()) {
					smartMove();
				}
			}
			public void restartGame() {
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
		jf.add(nPanel, BorderLayout.NORTH);	
		jf.setVisible(true);
		
		
		game.addObserver(rPanel);
		game.addObserver(nPanel);
		game.addObserver(board);
		game.addObserver((e) -> autoMove());
	}
	
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
	
	public abstract FrameUI createJFrame(GameTable<S, A> ctrl, String name);
	public abstract BoardUI<S, A> createBoard(int id, ColorModel cm, S s, BoardListener<S, A> listener);

	public void stopGame() {
		game.stop();
	}
	
	public PlayerMode getPlayerMode(){
		return playerMode;
	}
	
	public int getPlayerId(){
		return id;
	}
	
	public void randomMove(){
		A a = rPlayer.requestAction(game.getState());
		rPanel.addMessage("You have requested a random move.");
		board.nullSelected();
		SwingUtilities.invokeLater(()->{
			game.execute(a);
		});
		rPanel.addMessage("Turn of player " + (id + 1) % 2);
	}
	
	public void smartMove(){
		A a = sPlayer.requestAction(game.getState());
		rPanel.addMessage("You have requested a smart move.");
		board.nullSelected();
		SwingUtilities.invokeLater(()->{
			game.execute(a);
		});
		rPanel.addMessage("Turn of player " + (id + 1) % 2);
	}
	
	public static void main(String ... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WasState ws = new WasState();
				new WasPlayerUI(new GameTable<WasState, WasAction>(ws), "Pepe", 0);
			}
		});
	}
}
