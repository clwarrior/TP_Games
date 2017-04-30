package es.ucm.fdi.tp.ttt;

import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.BoardUI;
import es.ucm.fdi.tp.view.BoardUI.BoardListener;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;
import es.ucm.fdi.tp.view.FrameUI;
import es.ucm.fdi.tp.view.PlayerUI;

/**
 * Implements the necessary methods of PlayerUI to the game Tick Tac Toe
 * @author Claudia Guerrero García-Heras and Rafael Herrera Troca
 * @version 1 (03/05/2017)
 */
public class TttPlayerUI extends PlayerUI<TttState, TttAction> {

	public TttPlayerUI(GameTable<TttState, TttAction> game, String name, int id) {
		super(game, name, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FrameUI createJFrame(GameTable<TttState, TttAction> ctrl, String name) {
		return new FrameUI("Tick Tac Toe - Player " + id + ": " + name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BoardUI<TttState, TttAction> createBoard(int id, ColorModel cm, TttState s,
			BoardListener<TttState, TttAction> listener) {
		return new TttBoardUI(id, cm, s, listener);
	}
}
