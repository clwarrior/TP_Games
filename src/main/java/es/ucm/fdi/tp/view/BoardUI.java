package es.ucm.fdi.tp.view;

import java.awt.Color;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;

public abstract class BoardUI< S extends GameState< S, ? > > extends JBoard {

	private static final long serialVersionUID = -2798902232928717390L;
	
	private ColorTableUI.ColorModel cm;
	protected S state;
	
	public BoardUI(ColorTableUI.ColorModel cm, S state) {
		this.cm = cm;
		this.state = state;
	}

	@Override
	protected void keyTyped(int keyCode) {}

	@Override
	protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);

	@Override
	protected Shape getShape(int player) {
		return Shape.CIRCLE;
	}

	@Override
	protected Color getColor(int player) {
		return cm.at(player);
	}

	@Override
	protected abstract Integer getPosition(int row, int col);

	
	@Override
	protected Color getBackground(int row, int col) {
		return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
	}
}
