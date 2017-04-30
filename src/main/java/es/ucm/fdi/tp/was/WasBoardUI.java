package es.ucm.fdi.tp.was;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

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
		if (state.getTurn() == state.WOLF && id == state.getTurn() && !state.isFinished()) {
			WasAction action = new WasAction(state.WOLF, click, state.getPieces()[state.WOLF]);
			if (state.isValid(action)) {
				listener.makeManualMove(action);
				listener.sendMessage("You have moved from " + action.getIniPos() + " to " + action.getEndPos() + '.');
				listener.sendMessage("Turn of player " + (action.getPlayerNumber() + 1) % 2 + '.');
				selected = null;
			}
		} else if (!state.isFinished() && id == state.getTurn()){
			if (state.at(row, col) == state.SHEEP) {
				selected = click;
				repaint();
				listener.sendMessage(
						"Selected " + selected + ". Click cell to move or another piece to change selection.");
			} else if (selected != null && state.at(selected.row, selected.col) == state.SHEEP) {
				WasAction action = new WasAction(state.SHEEP, click, selected);
				if (state.isValid(action)) {
					listener.makeManualMove(action);
					listener.sendMessage(
							"You have moved from " + action.getIniPos() + " to " + action.getEndPos() + '.');
					listener.sendMessage("Turn of player " + (action.getPlayerNumber() + 1) % 2 + '.');
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

	public void nullSelected() {
		selected = null;
	}

	@Override
	public void paintSelected(Graphics g) {
		if (state.getTurn() == state.SHEEP && id == state.SHEEP && selected != null) {
			g.setColor(inverseColor(cm.at(state.getTurn())));
			for (int i = 2; i <= 4; ++i) {
				g.drawOval(selected.col * _CELL_WIDTH + _SEPARATOR + i, selected.row * _CELL_HEIGHT + _SEPARATOR + i,
						_CELL_WIDTH - i * _SEPARATOR - 4, _CELL_HEIGHT - i * _SEPARATOR - 4);
			}

			List<WasAction> possible = state.validActions(state.getTurn(), selected);
			g.setColor(cm.at(state.getTurn()));
			for (int j = 0; j < possible.size(); ++j) {
				Coord cell = possible.get(j).getEndPos();
				for (int i = 2; i <= 4; ++i)
					g.drawRect(cell.col * _CELL_WIDTH + _SEPARATOR + i, cell.row * _CELL_HEIGHT + _SEPARATOR + i,
							_CELL_WIDTH - i * _SEPARATOR - 4, _CELL_HEIGHT - i * _SEPARATOR - 4);
			}
		}
		else if(state.getTurn() == state.WOLF && id == state.WOLF) {
			List<WasAction> possible = state.validActions(state.getTurn(), state.getPieces()[state.WOLF]);
			g.setColor(cm.at(state.getTurn()));
			for (int j = 0; j < possible.size(); ++j) {
				Coord cell = possible.get(j).getEndPos();
				for (int i = 2; i <= 4; ++i)
					g.drawRect(cell.col * _CELL_WIDTH + _SEPARATOR + i, cell.row * _CELL_HEIGHT + _SEPARATOR + i,
							_CELL_WIDTH - i * _SEPARATOR - 4, _CELL_HEIGHT - i * _SEPARATOR - 4);
			}
		}

	}

	private Color inverseColor(Color c) {
		int red = 255 - c.getRed();
		int green = 255 - c.getGreen();
		int blue = 255 - c.getBlue();
		return new Color(red, green, blue);
	}
}
