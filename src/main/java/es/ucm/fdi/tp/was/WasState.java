package es.ucm.fdi.tp.was;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;

/**
 * A TickTackToe state.
 * Describes a board of TickTackToe that is either being
 * played or is already finished.
 */
public class WasState extends GameState<WasState, WasAction> {

	static class Coord {
		int row;
		int col;
		public Coord(int row, int col) { this.row = row; this.col = col; }
		public Coord add(Coord o) {
			return new Coord(this.row + o.row, this.col + o.col);
		}
	}
	
	private static final long serialVersionUID = -6066312347935012935L;
	
	private final int turn;
    private final boolean finished;
    private final int winner;
    private final Coord[] pieces;
    private final int dim = 8;    
    
    /**
     * initial positions for wolf (1st) and sheep (rest)
     */
    private final Coord[] initialPositions = {
    		new Coord(dim-1, 0), 
    		new Coord(0, 1), new Coord(0, 3), 
    		new Coord(0, 5), new Coord(0, 7)
    };  
    
    private final Coord[] moves = {
    		new Coord(-1, -1), new Coord(-1, 1), 
    		new Coord(1, -1), new Coord(1, 1)
    };
    
    final static int EMPTY = -1;

    public WasState() {    	
        super(2);
        this.pieces = initialPositions.clone();
        this.turn = 0;
        this.winner = -1;
        this.finished = false;
    }
        
    public WasState(WasState prev, Coord[] pieces, boolean finished, int winner) {
    	super(2);
        this.pieces=pieces;
        this.turn = (prev.turn + 1) % 2;
        this.finished = finished;
        this.winner = winner;
    }    

    public boolean isValid(WasAction action) {
    	boolean valid=false;
        if (isFinished()) {
            return false;
        }
        if (action.getRow()>=0 &&action.getCol()>=0 && action.getRow()<dim && action.getCol()<dim){
        	valid = at(action.getRow(), action.getCol()) == EMPTY;
        }
        return  valid;
    }

    public List<WasAction> validActions(int playerNumber) {
        ArrayList<WasAction> valid = new ArrayList<>();
        if (finished) {
            return valid;
        }
        Coord newPos;
        if (playerNumber == 0){
        	for(int i = 0; i < 4;++i){
        		newPos = pieces[0].add(this.moves[i]);
        		WasAction action = new WasAction(playerNumber, newPos.row, newPos.col, pieces[0]);
        		if (isValid(action)){
        			valid.add(action);
        		}
        	}
        }
        else{
        	for(int sheep=1;sheep<5;++sheep){
        		for(int i = 0; i < 2;++i){
        			newPos = pieces[sheep].add(this.moves[i]);
        			WasAction action = new WasAction(playerNumber, newPos.row, newPos.col, pieces[sheep]);
        			if (isValid(action)){
        				valid.add(action);
        			}
        		}
        	}
        }
        return valid;
    }



    private static boolean isWinner(Coord[] pieces, int playerNumber, 
    		int x0, int y0, int dx, int dy) {
        boolean won = true;
        for (int i=0, x=x0, y=y0; won && i<board.length; i++, x+=dx, y+=dy) {
            if (board[y][x] != playerNumber) won = false;
        }
        return won;
    }

    // Lo ponemos no estático o creamos un nuevo objeto WasState para llamar validActions sobre el o creamos otro validActions
    // dado playerNumber y pieces
    
    public static boolean isWinner(Coord[] pieces, int playerNumber) {
        boolean won = false;
        if(playerNumber==0 && pieces[0].row==0)
        	won=true;
        else{
        	List<WasState> validAux = validActions(0);
        	
        }
        
        
        for (int i=0; !won && i<board.length; i++) {
            if (isWinner(board, playerNumber, 0, i, 1, 0)) won = true;
            if (isWinner(board, playerNumber, i, 0, 0, 1)) won = true;
        }
        return won ||
                isWinner(board, playerNumber, 0, 0, 1, 1) ||
                isWinner(board, playerNumber, 2, 0, -1, 1);
    }    

    public int at(int row, int col) {
    	int sol=-1;
    	for(int i=0;i<this.pieces.length; ++i){
    		if(row==pieces[i].row && col==pieces[i].col){
    			if (i==0)
    				sol=i;
    			else
    				sol=1;
    		}		
    	}
        return sol;
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<dim; i++) {
            sb.append("|");
            for (int j=0; j<dim; j++) {
                sb.append(at(i, j) == EMPTY ? "   |" : at(i, j) == 0 ? " O |" : " X |");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
