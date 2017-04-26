package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.BoardUI;
import es.ucm.fdi.tp.view.ColorTableUI;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;
import es.ucm.fdi.tp.view.GUIController;
import es.ucm.fdi.tp.was.WasState.Coord;

public class WasBoardUI extends BoardUI<WasState, WasAction> {

	private static final long serialVersionUID = -6806220148484079355L;

	private Coord selected;

	public WasBoardUI(ColorModel cm, WasState state) {
		super(cm, state);
		this.selected = null;
	}

	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		Coord click = new Coord(row, col);
		if (state.getTurn() == state.WOLF) {
			WasAction action = new WasAction(state.WOLF, state.getPieces()[state.WOLF], click);
			if (state.isValid(action)) {
				ctrl.makeManualMove(action);
			}
		} else {
			if (state.at(row, col) == state.SHEEP) {
				selected = click;
			} else if (selected != null && state.at(selected.row, selected.col) == state.SHEEP) {
				WasAction action = new WasAction(state.SHEEP, selected, click);
				if (state.isValid(action)) {
					ctrl.makeManualMove(action);
				}
			}
		}
	}

	@Override
	protected Integer getPosition(int row, int col) {
		if(state.at(row, col) != state.EMPTY)
			return state.at(row, col);
		else
			return null;
	}

	@Override
	protected int getNumRows() {
		return WasState.dim;
	}

	@Override
	protected int getNumCols() {
		return WasState.dim;
	}
}
