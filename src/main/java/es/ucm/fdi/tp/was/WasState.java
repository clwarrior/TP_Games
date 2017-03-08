package es.ucm.fdi.tp.was;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;

public class WasState extends GameState<WasState, WasAction> {

	static class Coord {
		int row;
		int col;
		public Coord(int row, int col) { this.row = row; this.col = col; }
		public Coord add(Coord o) {
			return new Coord(this.row + o.row, this.col + o.col);
		}
		public boolean isAt(int row, int col) { return this.row == row && this.col == col; }
		public String toString() {
			return "(" + row + ", " + col + ")";
		}
		public Coord clone() {
			return new Coord(row, col);
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
    		new Coord(1, -1), new Coord(1, 1), 
    		new Coord(-1, -1), new Coord(-1, 1)
    };
    
    public final int WOLF = 0; 
    public final int SHEEP = 1; 
    public final int EMPTY = -1; 
    public final int OUTSIDE = -2; 
    
    
    public WasState() {    	
        super(2);
        this.pieces = initialPositions.clone();
        this.turn = 0;
        this.winner = -1;
        this.finished = false;
    }
        
    public WasState(WasState prev, Coord[] pieces, boolean finished, int winner) {
    	super(2);
        this.pieces=pieces.clone();
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
    	List<WasAction> valid = new ArrayList<>();
    	if (finished) {
            return valid;
        }
        Coord newPos;
        if (playerNumber == WOLF){
        	for(int i = 0; i < 4;++i){
        		newPos = pieces[0].add(this.moves[i]);
        		WasAction action = new WasAction(playerNumber, newPos, pieces[0]);
        		if (isValid(action)){
        			valid.add(action);
        		}
        	}
        }
        else{
        	for(int sheep=1;sheep<5;++sheep){
        		for(int i = 0; i < 2;++i){
        			newPos = pieces[sheep].add(this.moves[i]);
        			WasAction action = new WasAction(playerNumber, newPos, pieces[sheep]);
        			if (isValid(action)){
        				valid.add(action);
        			}
        		}
        	}
        }
        return valid;
	}

    public boolean isWinner(int playerNumber) {
        if(playerNumber==WOLF) {
        	return pieces[0].row==0;
        } else {
        	return validActions(WOLF).isEmpty();        	
        }   
    }    
    
    public boolean sheepBlocked(){
    	return validActions(SHEEP).isEmpty();
    }

    public int at(int row, int col) {
    	if (row < 0 || col < 0 || row >= dim || col >= dim) return OUTSIDE;
    	int i=0;
    	for (Coord c : pieces) {
    		if (c.isAt(row, col)) return i;
    		i=1;
    	}
        return EMPTY;
    }

    public int getTurn() {
        return turn;
    }
    
    public Coord[] getPieces() {
    	return this.pieces.clone();
    }

    public boolean isFinished() {
        return finished;
    }

    public int getWinner() {
        return winner;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n  ");
        for (int j=0; j<dim; j++) {
            sb.append("  " + j + " ");
        }
        sb.append("\n");
        sb.append("  ");
        for (int j = 0; j < dim; ++j) {
        	sb.append(" ---");
        }
        sb.append("\n");
        
        for (int i=0; i<dim; i++) {
            sb.append(i + " |");
            for (int j=0; j<dim; j++) {
                sb.append(at(i, j) == EMPTY ? "   |" : at(i, j) == 0 ? " W |" : " S |");
            }
            sb.append("\n");
            sb.append("  ");
            for (int j = 0; j < dim; ++j) {
            	sb.append(" ---");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
