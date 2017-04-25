package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
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
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasPlayerUI;
import es.ucm.fdi.tp.was.WasState;

public abstract class PlayerUI<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObserver<S, A>{

	FrameUI jf;
	RightPanel<S, A> rPanel;
	NorthPanel<S, A> nPanel;
	GUIController<S, A> ctrl;
	BoardUI<S, A> board;
	
	public PlayerUI(GUIController<S, A> ctrl, S state, String name, GameTable<S, A> game){
		
		ctrl.restartGame();
		
		ColorModel cm = new ColorTableUI().new ColorModel(state.getPlayerCount());
		
		this.ctrl = ctrl;
		this.jf = createJFrame(ctrl, name);
		this.rPanel = new RightPanel<S, A>(state.getPlayerCount(), cm);
		this.nPanel = new NorthPanel<S, A>(ctrl);
		this.board = createBoard(ctrl, cm, state);
		
		game.addObserver(this);
		game.addObserver(rPanel);
		
		
		jf.add(board, BorderLayout.CENTER);
		jf.add(rPanel, BorderLayout.EAST);
		jf.add(nPanel, BorderLayout.NORTH);		
	}
	
	public abstract FrameUI createJFrame(GUIController<S, A> ctrl, String name);
	public abstract BoardUI<S, A> createBoard(GUIController<S, A> ctrl, ColorModel cm, S s);
	
	public void notifyEvent(GameEvent<S, A> e){
		if(e.getType() == EventType.Start){
			ColorModel cm = new ColorTableUI().new ColorModel(e.getState().getPlayerCount());
			this.rPanel = new RightPanel<S, A>(e.getState().getPlayerCount(), cm);
			this.nPanel = new NorthPanel<S, A>(ctrl);
			board = createBoard(ctrl, cm, e.getState());
		}
	}
		
	
	public static void main(String ... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WasState ws = new WasState();
				new WasPlayerUI(new GUIController<WasState, WasAction>(new GameTable<WasState, WasAction>
				(ws), new RandomPlayer("a"), new SmartPlayer("a", 5), 0), "Pepe", ws, new GameTable<WasState, WasAction>(ws));
			}
		});
	}
}
