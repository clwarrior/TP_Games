package es.ucm.fdi.tp.mvc;

import java.util.ArrayList;
import java.util.Set;

import es.ucm.fdi.tp.base.model.*;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

    private S initState;
    private S actualState;
    private boolean finished;
    private boolean started;
    private ArrayList< GameObserver< S, A > > obs;

    public GameTable(S initState) {
        this.initState = initState;
        this.actualState = null;
        this.started = false;
        this.finished = false;
        this.obs = new ArrayList< GameObserver< S, A > >();
    }
    public void start() {
        actualState = initState;
        started = true;
        finished = false;
        GameEvent< S, A> start= new GameEvent< S, A >(EventType.Start, null, actualState, null, null);
        notifyObservers(start);
    }
    public void stop() {
    	if(!finished) {
    		finished = true;
    		GameEvent< S, A > stop = new GameEvent<S, A>(EventType.Stop, null, null, null, null);
    		notifyObservers(stop);
    	} else {
    		GameError error = new GameError("The game is already stopped");
    		GameEvent< S, A > event = 
    				new GameEvent< S, A >(EventType.Error, null, null, error, null);
    		notifyObservers(event);
    		throw error;
    	}
    }
    public void execute(A action) {
        if(finished || !started) {
        	GameError error = new GameError("The game is stopped or not started");
        	GameEvent< S, A > event = 
    				new GameEvent< S, A >(EventType.Error, null, null, error, null);
        	notifyObservers(event);
        } else {
        	try {
        		S newState = action.applyTo(actualState);
        		actualState = newState;
        		GameEvent< S, A > event = new GameEvent< S, A >(EventType.Change, 
        				action, actualState, null, "");
        		notifyObservers(event);
        	} catch (IllegalArgumentException e) {
        		GameError error = new GameError("The game is stopped or not started");
            	GameEvent< S, A > event = 
        				new GameEvent< S, A >(EventType.Error, null, null, error, null);
            	notifyObservers(event);
            	throw error;
        	}
        }
    }
    public S getState() {
		return actualState;
    }

    public void addObserver(GameObserver<S, A> o) {
        obs.add(o);
    }
    public void removeObserver(GameObserver<S, A> o) {
        obs.remove(o);
    }
    public void notifyObservers(GameEvent<S, A> e) {
    	for(GameObserver<S, A> a : obs){
    		a.notifyEvent(e);
    	}
    }
}
