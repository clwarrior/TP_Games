package es.ucm.fdi.tp.view;

import java.awt.Color;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.BoardExample;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;

public class GenericBoard<S extends GameState<?, ?>> extends JBoard {

	private static final long serialVersionUID = -7032704505942302353L;
	
	private int rows;
	private int cols;
	
	private Color[] playersColor;
	
	public GenericBoard(){
		initializeColors(playersColor);
	}
	public GenericBoard(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		initializeColors(playersColor);
	}

	protected void setState(S state){}
	protected void initializeColors(Color[] c){}
	
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
	@Override
	protected void mouseClicked(int row, int col, int clickCount,
			int mouseButton) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected Integer getPosition(int row, int col) {
		// TODO Auto-generated method stub
		return null;
	}
}
