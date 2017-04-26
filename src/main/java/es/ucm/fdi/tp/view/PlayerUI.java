package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;
import es.ucm.fdi.tp.view.NorthPanel.NorthPanelListener;
import es.ucm.fdi.tp.view.RightPanel.RightPanelListener;
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasPlayerUI;
import es.ucm.fdi.tp.was.WasState;

public abstract class PlayerUI<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObserver<S, A>{
	
	public enum PlayerMode {
		Smart, Random, Manual;
	}
	
	private GameTable<S, A> game;
	private RandomPlayer rPlayer;
	private SmartPlayer sPlayer;
	private int id;
	private PlayerMode playerMode;
	
	private FrameUI jf;
	private RightPanel<S, A> rPanel;
	private NorthPanel<S, A> nPanel;
	private BoardUI<S, A> board;
	
	public PlayerUI(GameTable<S, A> game, String name){
		
		S state = game.getState();
		
		ColorModel cm = new ColorTableUI().new ColorModel(state.getPlayerCount());
		
		this.jf = createJFrame(game, name);
		this.rPanel = new RightPanel<S, A>(state.getPlayerCount(), cm, new RightPanelListener(){
			public void changeColor(){
				jf.repaint();
			}
		});
		this.nPanel = new NorthPanel<S, A>(new NorthPanelListener(){
			public void makeRandomMove() {
				if (id == game.getState().getTurn()) {
					A a = rPlayer.requestAction(game.getState());
					game.execute(a);
				}
			}
			public void makeSmartMove() {
				if (id == game.getState().getTurn()) {
					A a = sPlayer.requestAction(game.getState());
					game.execute(a);
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
			}
		});
		this.board = createBoard(cm, state);
		
		game.addObserver(this);
		game.addObserver(rPanel);
		
		
		jf.add(board, BorderLayout.CENTER);
		jf.add(rPanel, BorderLayout.EAST);
		jf.add(nPanel, BorderLayout.NORTH);		
	}
	
	public abstract FrameUI createJFrame(GameTable<S, A> ctrl, String name);
	public abstract BoardUI<S, A> createBoard(ColorModel cm, S s);
	
	public void notifyEvent(GameEvent<S, A> e){
		if(e.getType() == EventType.Start){
			ColorModel cm = new ColorTableUI().new ColorModel(e.getState().getPlayerCount());
			//this.rPanel = new RightPanel<S, A>(e.getState().getPlayerCount(), cm);
			this.nPanel = new NorthPanel<S, A>( new NorthPanelListener(){
				
				public void makeRandomMove() {
					if (id == game.getState().getTurn()) {
						A a = rPlayer.requestAction(game.getState());
						game.execute(a);
					}
				}

				public void makeSmartMove() {
					if (id == game.getState().getTurn()) {
						A a = sPlayer.requestAction(game.getState());
						game.execute(a);
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
				}
				
				
			});
			board = createBoard(cm, e.getState());
		}
	}

	public void makeManualMove(A a) {
		if (id == game.getState().getTurn())
			game.execute(a);
	}

	

	public void stopGame() {
		game.stop();
	}

	public PlayerMode getPlayerMode(){
		return playerMode;
	}
	
	public void handleEvent(GameEvent<S, A> e){
		
	}
	
	public int getPlayerId(){
		return id;
	}


		
	
	public static void main(String ... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WasState ws = new WasState();
				new WasPlayerUI(new GameTable<WasState, WasAction>(ws), "Pepe");
			}
		});
	}
}
