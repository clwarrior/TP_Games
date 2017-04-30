package es.ucm.fdi.tp.ttt;

import java.awt.Graphics;
import es.ucm.fdi.tp.view.BoardUI;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;

public class TttBoardUI extends BoardUI<TttState, TttAction> {

	private static final long serialVersionUID = -7980914351488645675L;

	public TttBoardUI(int id, ColorModel cm, TttState state, BoardListener<TttState, TttAction> listener) {
		super(id, cm, state, listener);
	}

	@Override
	public void paintSelected(Graphics g) {}

	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		if(state.getTurn() == id && !state.isFinished()) {
			TttAction action = new TttAction(state.getTurn(), row, col);
			if(state.isValid(action)) {
				listener.sendMessage("You have chosen the cell (" + row + ',' + col + ").");
				listener.sendMessage("Turn of player " + (action.getPlayerNumber() + 1) % 2 + '.');
				listener.makeManualMove(action);
			}
		}
	}

	@Override
	protected Integer getPosition(int row, int col) {
		if (state.at(row, col) != TttState.EMPTY)
			return state.at(row, col);
		else
			return null;
	}

	@Override
	public void nullSelected() {}

	@Override
	protected int getNumRows() {
		return state.dim;
	}

	@Override
	protected int getNumCols() {
		return state.dim;
	}

}