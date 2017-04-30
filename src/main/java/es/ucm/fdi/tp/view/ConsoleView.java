package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;

public class ConsoleView<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObserver<S, A> {

	public ConsoleView(GameObservable<S,A> game){
		game.addObserver(this);
	}
	
	public void notifyEvent(GameEvent<S, A> e) {
		if(e.toString() != null)
			System.out.println(e);
	}

}
