package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.*;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;

public class WasPlayerUI extends PlayerUI< WasState, WasAction >{

	public WasPlayerUI(GameTable<WasState, WasAction> game, String name) {
		super(game, name);
	}

	@Override
	public FrameUI createJFrame(GameTable<WasState, WasAction> game, String name){
		return new FrameUI("Wolf and Sheep - " + name);
	}

	@Override
	public BoardUI<WasState, WasAction> createBoard(ColorModel cm, GameTable<WasState, WasAction> game) {
		return new WasBoardUI(cm, game);
	}
}
