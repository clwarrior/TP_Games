package es.ucm.fdi.tp.ttt;

import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.BoardUI;
import es.ucm.fdi.tp.view.BoardUI.BoardListener;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasBoardUI;
import es.ucm.fdi.tp.was.WasState;
import es.ucm.fdi.tp.view.FrameUI;
import es.ucm.fdi.tp.view.PlayerUI;

public class TttPlayerUI extends PlayerUI<TttState, TttAction> {

	public TttPlayerUI(GameTable<TttState, TttAction> game, String name, int id) {
		super(game, name, id);
	}

	@Override
	public FrameUI createJFrame(GameTable<TttState, TttAction> ctrl, String name) {
		return new FrameUI("Tick Tac Toe - Player " + id + ": " + name);
	}

	@Override
	public BoardUI<TttState, TttAction> createBoard(int id, ColorModel cm, TttState s,
			BoardListener<TttState, TttAction> listener) {
		return new TttBoardUI(id, cm, s, listener);
	}
}
