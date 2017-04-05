package es.ucm.fdi.tp.launcher;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WasState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/**
 * Main class of the application. It's the launcher of the game.
 * @author Claudia Guerrero García-Heras and Rafael Herrera Troca
 * @version 1 (13/03/2017)
 */
public class Main {

	final static String NotArguments = "Error: not arguments given";
	final static String WrongGame = "Error: game not available";
	final static String WrongPlayersNumber = "Error: number of players not suitable";
	final static String WrongMatches = "Error: number of matches not suitable";
	
	/**
	 * Array with names to be chosen for the not automatic players.
	 */
	private static String[] people = new String[] {"Ana", "Berto", "Carlos", 
			"Daniela", "Evaristo", "Fátima", "Gloria", "Hilario", "Isabel", "Juan",
			"Katia", "Luis", "María", "Norberto", "Olga", "Pedro", "Raúl", "Sara",
			"Teresa", "Úrsula", "Víctor", "William"};
	
	/**
	 * Method that returns an array of size n full with different names from the array "people".
	 * @param n Size of the array returned
	 * @return List< String > which contains n different names from the array "people"
	 */
	private static List< String > notRepNames(int n){
		ArrayList< String > elegidos = new ArrayList<>(Arrays.asList(people));
		Collections.shuffle(elegidos);
		while(elegidos.size() > n) elegidos.remove(elegidos.size() - 1);
		return elegidos;
	}
	
	/**
	 * Method that guides one game and returns its result.
	 * @param initialState Initial state of the game
	 * @param players List of players which are going to play
	 * @return int with the number of the winner
	 */
	public static <S extends GameState<S, A>, A extends GameAction<S, A>> int playGame(GameState<S, A> initialState,
			List<GamePlayer> players) {
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

	/**
	 * Repeatedly plays a game with a vs b
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
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String... args) {				
		try (Scanner s = new Scanner(System.in)) {
			List<GamePlayer> players = new ArrayList<GamePlayer>();
			GameState< ?, ? > game;
			
			if(args.length == 0)
				throw new IllegalArgumentException(NotArguments);
			
			int numJugadores = 0;
			if (args[0].startsWith("ttt")){
				game = new TttState(3);
				numJugadores = 2;
			} else if (args[0].startsWith("was")){
				game = new WasState();
				numJugadores = 2;
			} else
				throw new IllegalArgumentException(WrongGame);
			
			List<String> names = notRepNames(numJugadores);
			if(args.length - 1 != numJugadores)
				throw new IllegalArgumentException(WrongPlayersNumber);
			
			for(int i = 0; i < numJugadores; ++i){
				if (args[i + 1].startsWith("console")) {
					players.add(new ConsolePlayer(ConsolePlayer.askName(s), s));
				} else if (args[i + 1].startsWith("rand")){
					players.add(new RandomPlayer("Random" + names.get(i)));
				} else if (args[i + 1].startsWith("smart")){
					players.add(new SmartPlayer("AI" + names.get(i), 20));
				} else {
					throw new IllegalArgumentException("player \"" + args[i + 1] + "\" not defined");
				}
			}
			
			System.out.print("How many times do you want to play? ");
			int num = s.nextInt();
			if(num <= 0) {
				throw new IllegalArgumentException(WrongMatches);
			} else
				match(game, players.get(0), players.get(1), num);
			
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}
}
