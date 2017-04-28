package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;

public abstract class BoardUI< S extends GameState< S, A >, A extends GameAction< S, A > > extends JBoard implements GameObserver<S, A>{

	private static final long serialVersionUID = -2798902232928717390L;
	
	public interface BoardListener< S extends GameState<S, A>, A extends GameAction<S, A>>{
		public void makeManualMove(A a);
		public void sendMessage(String s);
	}
	
	private ColorTableUI.ColorModel cm;
	private int id;
	protected S state;
	protected BoardListener<S, A> listener;
	
	public BoardUI(int id, ColorTableUI.ColorModel cm, S state, BoardListener<S, A> listener) {
		this.cm = cm;
		this.state = state;
		this.listener = listener;
		this.id = id;
		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				int col = (e.getX() / _CELL_WIDTH);
				int row = (e.getY() / _CELL_HEIGHT);

				int mouseButton = 0;

				if (SwingUtilities.isLeftMouseButton(e))
					mouseButton = 1;
				else if (SwingUtilities.isMiddleMouseButton(e))
					mouseButton = 2;
				else if (SwingUtilities.isRightMouseButton(e))
					mouseButton = 3;

				if (mouseButton == 0)
					return;
				BoardUI.this.mouseClicked(row, col, e.getClickCount(), mouseButton);
			}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}});
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
	
	@Override
	public void notifyEvent(GameEvent<S, A> e){
		switch(e.getType()){
		case Start:
		case Change:
			setEnabled(e.getState().getTurn() == id);
			break;
		case Stop:
			setEnabled(false);
			break;
		default:
			break;
		}
		repaint();
	}
}
