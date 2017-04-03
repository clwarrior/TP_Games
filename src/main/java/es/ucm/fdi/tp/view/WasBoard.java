package es.ucm.fdi.tp.view;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WasState;

public class WasBoard extends BoardUI {

	public WasBoard(WasState state) {
		super(state);
		this.board = new GenericBoard<GameState>(state.dim, state.dim);
	}

	protected Integer getPosition(int row, int col) {
		return ((WasState) state).at(row, col);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WasState state = new WasState();
				new WasBoard(state);
			}
		});
	}
}
