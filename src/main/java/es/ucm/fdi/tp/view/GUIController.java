package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameTable;

public class GUIController<S extends GameState<S, A>, A extends GameAction<S, A>> {

	public enum PlayerMode {
		Smart, Random, Manual;
	}

	private GameTable<S, A> game;
	private RandomPlayer rPlayer;
	private SmartPlayer sPlayer;
	private int id;
	private PlayerMode playerMode;

	public GUIController(GameTable<S, A> game, RandomPlayer rPlayer, SmartPlayer sPlayer, int id) {
		this.game = game;
		this.rPlayer = rPlayer;
		this.sPlayer = sPlayer;
		this.id = id;
		this.playerMode = PlayerMode.Manual;
	}

	public void makeManualMove(A a) {
		if (id == game.getState().getTurn())
			game.execute(a);
	}

	public void makeRandomMove() {
		if (id == game.getState().getTurn()) {
			A a = rPlayer.requestAction(game.getState());
			game.execute(a);
		}
	}

	public void makeSmartMove() {
		if (id == game.getState().getTurn()) {
			A a = sPlayer.requestAction(game.getState());
			game.execute(a);
		}
	}

	public void restartGame() {
		game.stop();
		game.start();
	}

	public void stopGame() {
		game.stop();
	}

	public void changePlayerMode(PlayerMode p) {
		playerMode = p;
	}
	
	public void handleEvent(GameEvent<S, A> e){
		
	}
	
	public PlayerMode getPlayerMode(){
		return playerMode;
	}
	
	public int getPlayerId(){
		return id;
	}
}
