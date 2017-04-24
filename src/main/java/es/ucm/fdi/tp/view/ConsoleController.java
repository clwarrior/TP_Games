package es.ucm.fdi.tp.view;

import java.util.List;

import es.ucm.fdi.tp.base.model.*;
import es.ucm.fdi.tp.mvc.GameTable;

public class ConsoleController<S extends GameState<S, A>, A extends GameAction<S, A>>
		implements Runnable {
	
	private List<GamePlayer> players;
	private GameTable<S, A> game;

	public ConsoleController(List<GamePlayer> players, GameTable<S, A> game) {
		this.players = players;
		this.game = game;
	}

	@Override
	public void run() {
		int playerCount = 0;
		System.out.println();
		System.out.println("New game started: ");
		for (GamePlayer p : players) {
			System.out.println("Welcome, " + p.getName() + "!!!");
			System.out.println("You are player number " + playerCount + "\n");
			p.join(playerCount++); // welcome each player, and assign playerNumber
		}
		@SuppressWarnings("unchecked")
		S currentState = (S) initialState;

		System.out.println(currentState);
		
		while (!currentState.isFinished()) {
			// request move
			System.out.println("It's " + players.get(currentState.getTurn()).getName()
					+ "'s turn");
			A action = players.get(currentState.getTurn()).requestAction(currentState);
			// apply move
			currentState = action.applyTo(currentState);
			
			System.out.println("After action:\n" + currentState);

			if (currentState.isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}
		}
		return currentState.getWinner();
	}

}
