package es.ucm.fdi.tp.was;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;

/**
 * A TickTackToe state.
 * Describes a board of TickTackToe that is either being
 * played or is already finished.
 */
public class WasState extends GameState<WasState, WasAction> {

	private static final long serialVersionUID = -6066312347935012935L;
	
	private final int turn;
    private final boolean finished;
    private final int winner;
    private final Point[] pieces;

    private final int dim = 8;
    private final int[][] moves = new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    
    final static int EMPTY = -1;

    public WasState() {    	
        super(2);

        this.pieces[0] = new Point();
        board[this.pieces[0][0]][this.pieces[0][1]] = 0;
        for(int i = 1; i < board.length; i += 2){
        	board[0][i] = 1;
        }
        this.turn = 0;
        this.winner = -1;
        this.finished = false;
    }
        
    public WasState(WasState prev, int[][] board, boolean finished, int winner) {
    	super(2);
        this.board = board;
        this.turn = (prev.turn + 1) % 2;
        this.finished = finished;
        this.winner = winner;
    }    

    public boolean isValid(WasAction action) {
        if (isFinished()) {
            return false;
        }
        return at(action.getRow(), action.getCol()) == EMPTY;
    }

    public List<WasAction> validActions(int playerNumber) {
        ArrayList<WasAction> valid = new ArrayList<>();
        int cont;
        if (finished) {
            return valid;
        }

        if (playerNumber == 0){
        	cont=4;
        }
        else{
        	cont=2;
        }
        int[] wNewPos = new int[2];
    	for(int i = 0; i < cont;++i){
    		wNewPos[0] = wpos[0] + moves[i][0];
    		wNewPos[1] = wpos[1] + moves[i][1];
    		WasAction action;
    		if (isValid(action)){
    		//añadir
    		}
    	}
    
        return valid;
    }

    public static boolean isDraw(int[][] board) {
        boolean empty = false;
        for (int i=0; ! empty && i<board.length; i++) {
            for (int j=0; ! empty && j<board.length; j++) {
                if (board[i][j] == EMPTY) {
                    empty = true;
                }
            }
        }
        return ! empty;
    }

    private static boolean isWinner(int[][] board, int playerNumber, 
    		int x0, int y0, int dx, int dy) {
        boolean won = true;
        for (int i=0, x=x0, y=y0; won && i<board.length; i++, x+=dx, y+=dy) {
            if (board[y][x] != playerNumber) won = false;
        }
        return won;
    }


    public static boolean isWinner(int[][] board, int playerNumber) {
        boolean won = false;
        for (int i=0; !won && i<board.length; i++) {
            if (isWinner(board, playerNumber, 0, i, 1, 0)) won = true;
            if (isWinner(board, playerNumber, i, 0, 0, 1)) won = true;
        }
        return won ||
                isWinner(board, playerNumber, 0, 0, 1, 1) ||
                isWinner(board, playerNumber, 2, 0, -1, 1);
    }    

    public int at(int row, int col) {
        return board[row][col];
    }

    public int getTurn() {
        return turn;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getWinner() {
        return winner;
    }

    /**
     * @return a copy of the board
     */
    public int[][] getBoard() {
        int[][] copy = new int[board.length][];
        for (int i=0; i<board.length; i++) copy[i] = board[i].clone();
        return copy;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<board.length; i++) {
            sb.append("|");
            for (int j=0; j<board.length; j++) {
                sb.append(board[i][j] == EMPTY ? "   |" : board[i][j] == 0 ? " O |" : " X |");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
