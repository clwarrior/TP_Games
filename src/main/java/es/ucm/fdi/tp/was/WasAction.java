package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;

/**
 * An action for TickTackToe.
 */
public class WasAction implements GameAction<WasState, WasAction> {

	private static final long serialVersionUID = -8491198872908329925L;
	
	private int player;
    private int row;
    private int col;

    public WasAction(int player, int row, int col) {
        this.player = player;
        this.row = row;
        this.col = col;
    }

    public int getPlayerNumber() {
        return player;
    }

    public WasState applyTo(WasState state) {
        if (player != state.getTurn()) {
            throw new IllegalArgumentException("Not the turn of this player");
        }

        // make move
        int[][] board = state.getBoard();
        board[row][col] = player;

        // update state
        WasState next;
        if (WasState.isWinner(board, state.getTurn())) {
            next = new WasState(state, board, true, state.getTurn());
        } else if (WasState.isDraw(board)) {
            next = new WasState(state, board, true, -1);
        } else {
            next = new WasState(state, board, false, -1);
        }
        return next;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String toString() {
        return "place " + player + " at (" + row + ", " + col + ")";
    }

}
