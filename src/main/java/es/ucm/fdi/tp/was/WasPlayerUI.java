package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.*;
import es.ucm.fdi.tp.view.BoardUI.BoardListener;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;

public class WasPlayerUI extends PlayerUI< WasState, WasAction >{

	public WasPlayerUI(GameTable<WasState, WasAction> game, String name, int id) {
		super(game, name, id);
	}

	@Override
	public FrameUI createJFrame(GameTable<WasState, WasAction> game, String name){
		return new FrameUI("Wolf and Sheep - " + name);
	}

	@Override
	public BoardUI<WasState, WasAction> createBoard(int id, ColorModel cm, WasState s, BoardListener<WasState, WasAction> listener) {
		return new WasBoardUI(id, cm, s, listener);
	}
}
