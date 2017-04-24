package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasPlayerUI;
import es.ucm.fdi.tp.was.WasState;

public abstract class PlayerUI<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObserver<S, A>{

	FrameUI jf;
	
	public PlayerUI(GameTable<S, A> game, String name){
		
		game.start();
		
		ColorModel cm = new ColorTableUI().new ColorModel(game.getState().getPlayerCount());
		
		jf = createJFrame(game, name);
		RightPanel<S, A> rPanel = new RightPanel<S, A>(game.getState().getPlayerCount(), cm);
		NorthPanel<S, A> nPanel = new NorthPanel<S, A>(game);
		BoardUI<S, A> board = createBoard(cm, game);
		
		
		
		jf.add(board, BorderLayout.CENTER);
		jf.add(rPanel, BorderLayout.EAST);
		jf.add(nPanel, BorderLayout.NORTH);		
	}
	
	public abstract FrameUI createJFrame(GameTable<S, A> game, String name);
	public abstract BoardUI<S, A> createBoard(ColorModel cm, GameTable<S, A> game);
	
		
	
	public static void main(String ... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WasPlayerUI(new GameTable<WasState, WasAction>(new WasState()), "Pepe");
			}
		});
	}
}
