package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;

/**
 * Class which shows the events of the console game mode through the console display
 * @author Claudia Guerrero García-Heras and Rafael Herrrera Troca
 * @version 1 (03/05/2017)
 * @param <S> GameState of the game played
 * @param <A> GameAction of the game played
 */
public class ConsoleView<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObserver<S, A> {

	public ConsoleView(GameObservable<S,A> game){
		game.addObserver(this);
	}
	
	/**
	 * Shows the content of the events received through the console
	 */
	public void notifyEvent(GameEvent<S, A> e) {
		if(e.toString() != null)
			System.out.println(e);
	}

}
