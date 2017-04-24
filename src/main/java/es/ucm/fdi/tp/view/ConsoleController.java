package es.ucm.fdi.tp.view;

import java.util.List;

import es.ucm.fdi.tp.base.model.*;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;
import es.ucm.fdi.tp.mvc.GameTable;

public class ConsoleController<S extends GameState<S, A>, A extends GameAction<S, A>>
		implements Runnable{
	
	private List<GamePlayer> players;
	private GameTable<S, A> game;

	public ConsoleController(List<GamePlayer> players, GameTable<S, A> game) {
		this.players = players;
		this.game = game;
	}

	@Override
	public void run() {
		int playerCount = 0;
		game.start();
		GameEvent<S, A> start = new GameEvent<S,A>(EventType.Start, null, null, null, "\nNew game started: ");
		game.notifyObservers(start);		
		for (GamePlayer p : players) {
			GameEvent<S, A> welcome = new GameEvent<S,A>(EventType.Start, null, null, null, "Welcome, " + p.getName() + "!!!" + 
					"\nYou are player number " + playerCount + "\n");
			game.notifyObservers(welcome);
			p.join(playerCount++); // welcome each player, and assign playerNumber
		}
		S currentState = game.getState();
		GameEvent<S, A> initBoard = new GameEvent<S,A>(EventType.Start, null, null, null, toString(currentState));
		
		while (!currentState.isFinished()) {
			// request move
			GameEvent<S, A> turn = new GameEvent<S,A>(EventType.Info, null, null, null, "It's " + 
					players.get(currentState.getTurn()).getName() + "'s turn");
			game.notifyObservers(turn);
			A action = players.get(currentState.getTurn()).requestAction(currentState);
			// apply move
			game.execute(action);
			currentState = game.getState();
			GameEvent<S, A> afterAct = new GameEvent<S,A>(EventType.Change, action, currentState,
					null, "After action:\n" + toString(currentState));

			if (currentState.isFinished()) {
				// game over
				GameEvent<S, A> end = new GameEvent<S,A>(EventType.Info, null, null, null, "The game ended: ");
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
				game.stop();
			}
		}
		return currentState.getWinner();
	}

	private String toString(S currentState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	void 

}
