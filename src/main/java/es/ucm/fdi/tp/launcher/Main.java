package es.ucm.fdi.tp.launcher;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttPlayerUI;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.ConsoleController;
import es.ucm.fdi.tp.view.ConsoleView;
import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasPlayerUI;
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

	final static String NotArguments = "Error: number of arguments not suitable";
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
	private static GameTable<?, ?> createGame(String gType){
		switch(gType){
		case "ttt":
			return new GameTable<TttState, TttAction>(new TttState(3)); //dimension
		case "was":
			return new GameTable<WasState, WasAction>(new WasState());
		}
		return null;
	}
	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startConsoleMode(GameTable<S, A> game, String playerModes[]){
		List<GamePlayer> players = new ArrayList<GamePlayer>();
		List<String> names = notRepNames(playerModes.length);
		for(int i = 0; i < playerModes.length; ++i){
			if (playerModes[i].startsWith("manual")) {
				players.add(new ConsolePlayer(names.get(i), new Scanner(System.in)));
			} else if (playerModes[i].startsWith("rand")){
				players.add(new RandomPlayer("Random" + names.get(i)));
			} else if (playerModes[i].startsWith("smart")){
				players.add(new SmartPlayer("AI" + names.get(i), 20));
			} else {
				throw new IllegalArgumentException("player \"" + playerModes[i] + "\" not defined");
			}
		}
		new ConsoleView<S, A>(game);
		new ConsoleController<S, A>(players, game);
	}
	@SuppressWarnings("unchecked")
	private static <S extends GameState<S, A>, A extends GameAction<S, A>> 
	void startGUIMode(String gType, GameTable<S, A> game, String playerModes[]) {
		List<GamePlayer> players = new ArrayList<GamePlayer>();
 		List<String> names = notRepNames(playerModes.length);
		if(gType == "ttt"){
			List<TttPlayerUI> windows = new ArrayList<TttPlayerUI>();
			for(int i = 0; i < playerModes.length; ++i){
			players.add(new ConsolePlayer(names.get(i), new Scanner(System.in)));
				windows.add(new TttPlayerUI((GameTable<TttState, TttAction>)game, players.get(i).getName(), i));
			}
		} else{
			List<WasPlayerUI> windows = new ArrayList<WasPlayerUI>();
			for(int i = 0; i < playerModes.length; ++i){
			players.add(new ConsolePlayer(names.get(i), new Scanner(System.in)));
				windows.add(new WasPlayerUI((GameTable<WasState, WasAction>)game, players.get(i).getName(), i));
		}
		}
	}
	
	
	public static void usage(){
		System.out.println(NotArguments);
	}
	
	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String ... args) {
		if (args.length < 2) {
			usage();
			System.exit(1);
		}
		GameTable<?, ?> game = createGame(args[0]);
		if (game == null) {
			System.err.println("Invalid game");
			System.exit(1);
		}
		String[] playerModes = Arrays.copyOfRange(args, 2, args.length);
		if (game.getState().getPlayerCount() != playerModes.length) {
			System.err.println("Invalid number of players");
			System.exit(1);
		}
		switch (args[1]) {
		case "console":
			startConsoleMode(game, playerModes);
			break;
		case "gui":
			startGUIMode(args[0], game, playerModes);
			break;
		default:
			System.err.println("Invalid view mode: " + args[1]);
			usage();
			System.exit(1);
		}
	}
}
