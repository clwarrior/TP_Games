package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.event.ActionListener;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.mvc.GameTable;

public abstract class BoardUI< S extends GameState< S, A >, A extends GameAction< S, A > > extends JBoard implements ActionListener{

	private static final long serialVersionUID = -2798902232928717390L;
	
	private ColorTableUI.ColorModel cm;
	protected GameTable<S, A> game;
	
	public BoardUI(ColorTableUI.ColorModel cm, GameTable<S, A> game) {
		this.cm = cm;
		this.game = game;
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
