package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.*;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;

public class WasPlayerUI extends PlayerUI< WasState, WasAction >{

	public WasPlayerUI(GUIController<WasState, WasAction> ctrl, String name, WasState state, GameTable<WasState, WasAction> game) {
		super(ctrl, state, name, game);
	}

	@Override
	public FrameUI createJFrame(GUIController<WasState, WasAction> ctrl, String name){
		return new FrameUI("Wolf and Sheep - " + name);
	}

	@Override
	public BoardUI<WasState, WasAction> createBoard(GUIController<WasState, WasAction> ctrl, ColorModel cm,
			WasState s) {
		return new WasBoardUI(ctrl, cm, s);
	}
}
