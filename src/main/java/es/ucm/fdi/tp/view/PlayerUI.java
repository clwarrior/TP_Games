package es.ucm.fdi.tp.view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;

public abstract class PlayerUI<S extends GameState<S, A>, A extends GameAction<S, A>> {

	public PlayerUI() {}
	
	public PlayerUI(GameState<S, A> state) {
		
		
	}
}
