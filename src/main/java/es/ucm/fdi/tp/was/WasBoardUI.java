package es.ucm.fdi.tp.was;

import java.awt.Color;
import java.awt.Graphics;

import es.ucm.fdi.tp.view.BoardUI;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;
import es.ucm.fdi.tp.was.WasState.Coord;

public class WasBoardUI extends BoardUI<WasState, WasAction> {

	private static final long serialVersionUID = -6806220148484079355L;

	private Coord selected;

	public WasBoardUI(int id, ColorModel cm, WasState state,
			BoardListener<WasState, WasAction> listener) {
		super(id, cm, state, listener);
		this.selected = null;
	}

	@Override
	protected void mouseClicked(int row, int col, int clickCount,
			int mouseButton) {
		Coord click = new Coord(row, col);
		if (state.getTurn() == state.WOLF) {
			WasAction action = new WasAction(state.WOLF, click,
					state.getPieces()[state.WOLF]);
			if (state.isValid(action) && id == state.getTurn()) {
				listener.makeManualMove(action);
				listener.sendMessage("You have moved from "
						+ action.getIniPos() + " to " + action.getEndPos()
						+ '.');
				listener.sendMessage("Turn of player "
						+ (action.getPlayerNumber() + 1) % 2 + '.');
				selected = null;
			}
		} else {
			if (state.at(row, col) == state.SHEEP) {
				selected = click;
				repaint();
				listener.sendMessage("Selected "
						+ selected
						+ ". Click cell to move or another piece to change selection.");
			} else if (selected != null
					&& state.at(selected.row, selected.col) == state.SHEEP) {
				WasAction action = new WasAction(state.SHEEP, click, selected);
				if (state.isValid(action) && id == state.getTurn()) {
					listener.makeManualMove(action);
					listener.sendMessage("You have moved from "
							+ action.getIniPos() + " to " + action.getEndPos()
							+ '.');
					listener.sendMessage("Turn of player "
							+ (action.getPlayerNumber() + 1) % 2 + '.');
					selected = null;
				}
			}
		}
	}

	@Override
	protected Integer getPosition(int row, int col) {
		if (state.at(row, col) != state.EMPTY)
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
	
	public void nullSelected(){
		selected = null;
	}

	@Override
	public void paintSelected(Graphics g) {
		if (selected != null) {
			g.setColor(Color.RED);
			g.drawOval(selected.col * _CELL_WIDTH + _SEPARATOR + 2, selected.row * _CELL_HEIGHT + _SEPARATOR
					+ 2, _CELL_WIDTH - 2 * _SEPARATOR - 4, _CELL_HEIGHT - 2
					* _SEPARATOR - 4);
			
			
			
			g.setColor(cm.at(state.getTurn()));
			g.drawRect(selected.row + _SEPARATOR + 2, selected.col + _SEPARATOR
					+ 2, _CELL_WIDTH - 2 * _SEPARATOR - 4, _CELL_HEIGHT - 2
					* _SEPARATOR - 4);
		}

	}
}
