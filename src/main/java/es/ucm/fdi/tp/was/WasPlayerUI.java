package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.*;
import es.ucm.fdi.tp.view.BoardUI.BoardListener;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;

/**
 * Implements the necessary methods of PlayerUI to the game Wolf and Sheep
 * @author Claudia Guerrero García-Heras and Rafael Herrera Troca
 * @version 1 (03/05/2017)
 */
public class WasPlayerUI extends PlayerUI<WasState, WasAction> {

	public WasPlayerUI(GameTable<WasState, WasAction> game, String name, int id) {
		super(game, name, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FrameUI createJFrame(GameTable<WasState, WasAction> game, String name) {
		return new FrameUI("Wolf and Sheep - Player " + id + ": " + name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BoardUI<WasState, WasAction> createBoard(int id, ColorModel cm,
			WasState s, BoardListener<WasState, WasAction> listener) {
		return new WasBoardUI(id, cm, s, listener);
	}
}
