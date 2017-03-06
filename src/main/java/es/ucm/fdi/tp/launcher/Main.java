package es.ucm.fdi.tp.launcher;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.exceptions.ParameterException;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WasState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Demo main, used to test game functionality. You can use it as an inspiration,
 * but we expect you to build your own main-class.
 */
public class Main {

	private static String[] gente = new String[] {"Ana", "Berto", "Carlos", "Daniela", "Evaristo", "Fátima"};
	
	private static List< String > nombresNoRepes(int n){
		ArrayList< String > elegidos = new ArrayList<>(Arrays.asList(gente));
		Collections.shuffle(elegidos);
		while(elegidos.size() > n) elegidos.remove(elegidos.size() - 1);
		return elegidos;
	}
	
	public static <S extends GameState<S, A>, A extends GameAction<S, A>> int playGame(GameState<S, A> initialState,
			List<GamePlayer> players) {
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		@SuppressWarnings("unchecked")
		S currentState = (S) initialState;

		while (!currentState.isFinished()) {
			// request move
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

	/**
	 * Repeatedly plays a game-state with a vs b
	 * 
	 * @param initialState
	 * @param a
	 *            player
	 * @param b
	 *            player
	 * @param times
	 *            to play
	 */
	public static void match(GameState<?, ?> initialState, GamePlayer a, GamePlayer b, int times) {
		int va = 0, vb = 0;

		List<GamePlayer> players = new ArrayList<GamePlayer>();
		players.add(a);
		players.add(b);

		for (int i = 0; i < times; i++) {
			switch (playGame(initialState, players)) {
			case 0:
				va++;
				break;
			case 1:
				vb++;
				break;
			}
		}
		System.out.println("Result: " + va + " for " + a.getName() + " vs " + vb + " for " + b.getName());
	}

	/**
	 * Plays tick-tack-toe with a console player against a smart player. The
	 * smart player should never lose.
	 */
	public static void testTtt() {
		try (Scanner s = new Scanner(System.in)) {
			List<GamePlayer> players = new ArrayList<GamePlayer>();
			GameState<?, ?> game = new TttState(3);
			players.add(new ConsolePlayer("Alice", s));
			players.add(new SmartPlayer("AiBob", 5));
			playGame(game, players);
		} // <-- closes the scanner when the try-block ends
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String... args) {				
		try (Scanner s = new Scanner(System.in)) {
			List<GamePlayer> players = new ArrayList<GamePlayer>();
			GameState< ?, ? > game;
			
			int numJugadores = 0;
			if (args[0].startsWith("ttt")){
				game = new TttState(3);
				numJugadores = 2;
			} else if (args[0].startsWith("was")){
				game = new WasState();
				numJugadores = 2;
			} else
				throw new ParameterException("juego no válido");
			
			List<String> names = nombresNoRepes(numJugadores);
			if(args.length - 1 != numJugadores)
				throw new ParameterException("numero de jugadores incompatible");
			
			for(int i = 0; i < numJugadores; ++i){
				if (args[i + 1].startsWith("console")) {
					players.add(new ConsolePlayer(names.get(i), s));
				} else if (args[i + 1].startsWith("rand")){
					players.add(new RandomPlayer("Random" + names.get(i)));
				} else if (args[i + 1].startsWith("smart")){
					players.add(new SmartPlayer("AI" + names.get(i), 5));
				} else {
					throw new ParameterException("jugador \"" + args[i + 1] + "\" no definido");
				}
			}
			playGame(game, players);
		} catch (ParameterException e) {
			System.err.println(e);
		}
	}
}
