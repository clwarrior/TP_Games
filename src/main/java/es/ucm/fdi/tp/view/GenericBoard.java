package es.ucm.fdi.tp.view;

import java.awt.Color;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.BoardExample;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;

public abstract class GenericBoard<S extends GameState<?, ?>> extends JBoard {

	private static final long serialVersionUID = -7032704505942302353L;
	
	private int rows;
	private int cols;
	
	private Color[] playersColor;
	
	protected class BoardActionListener implements GameObserver { // Que es esto??

		@Override
		public void notifyEvent(GameEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	public GenericBoard(){
		initializeColors(playersColor);
	}
	public GenericBoard(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		initializeColors(playersColor);
	}

	protected abstract void setState(S state);
	protected abstract void initializeColors(Color[] c);
	
	@Override
	protected void keyTyped(int keyCode) {}
	
	@Override
	protected Shape getShape(int player) {
		return Shape.CIRCLE;
	}
	
	@Override
	protected Color getColor(int player) {
		return playersColor[player];
	}
	
	@Override
	protected Color getBackground(int row, int col) {
		return (row+col) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY;
	}

	@Override
	protected int getNumRows() {
		return rows;
	}

	@Override
	protected int getNumCols() {
		return cols;
	}

}
