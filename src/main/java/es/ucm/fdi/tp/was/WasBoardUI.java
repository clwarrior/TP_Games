package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.view.BoardUI;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;
import es.ucm.fdi.tp.was.WasState.Coord;

public class WasBoardUI extends BoardUI<WasState, WasAction> {

	private static final long serialVersionUID = -6806220148484079355L;

	private Coord selected;

	public WasBoardUI(int id, ColorModel cm, WasState state, BoardListener<WasState, WasAction> listener) {
		super(id, cm, state, listener);
		this.selected = null;
	}

	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		Coord click = new Coord(row, col);
		if (state.getTurn() == state.WOLF) {
			WasAction action = new WasAction(state.WOLF, state.getPieces()[state.WOLF], click);
			if (state.isValid(action)) {
				listener.makeManualMove(action);
				listener.sendMessage("You have moved from " + action.getIniPos() + " to " + action.getEndPos() + '.');
				listener.sendMessage("Turn of player " + (action.getPlayerNumber() + 1) % 2 + '.');
			}
		} else {
			if (state.at(row, col) == state.SHEEP) {
				selected = click;
				listener.sendMessage("Selected " + selected + ". Click cell to move or another piece to change selection.");
			} else if (selected != null && state.at(selected.row, selected.col) == state.SHEEP) {
				WasAction action = new WasAction(state.SHEEP, selected, click);
				if (state.isValid(action)) {
					listener.makeManualMove(action);
					listener.sendMessage("You have moved from " + action.getIniPos() + " to " + action.getEndPos() + '.');
					listener.sendMessage("Turn of player " + (action.getPlayerNumber() + 1) % 2 + '.');
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
