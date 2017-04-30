package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
		public void stopGame();
	}
	
	protected ColorTableUI.ColorModel cm;
	protected int id;
	protected S state;
	protected BoardListener<S, A> listener;
	
	public BoardUI(int id, ColorTableUI.ColorModel cm, S state, BoardListener<S, A> listener) {
		this.cm = cm;
		this.state = state;
		this.listener = listener;
		this.id = id;
	}

	public abstract void paintSelected(Graphics2D g);
	
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
			state = e.getState();
			if(e.getState().getTurn() == id && !e.getState().isFinished()){
				setEnabled(true);
				listener.sendMessage("It's your turn.");
			}
			if(e.getState().isFinished())
				listener.stopGame();
			break;
		case Stop:
			nullSelected();
			setEnabled(false);
			break;
		default:
			break;
		}
		repaint();
	}

	public abstract void nullSelected();
}
