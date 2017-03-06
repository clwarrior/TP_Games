package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;

/**
 * An action for TickTackToe.
 */
public class WasAction implements GameAction<WasState, WasAction> {

	private static final long serialVersionUID = -8491198872908329925L;
	
	private int player;
    private WasState.Coord iniPos;
    private WasState.Coord endPos;

    
    public WasAction(int player, WasState.Coord fin, WasState.Coord ini) {
        this.player = player;
        this.endPos=fin;
        this.iniPos = ini;
        
    }

    public int getPlayerNumber() {
        return player;
    }

    public WasState applyTo(WasState state) {
        if (player != state.getTurn()) {
            throw new IllegalArgumentException("Not the turn of this player");
        }

        // make move
        WasState.Coord[] pieces = state.getPieces();
        for(WasState.Coord c : pieces){
        	if(c.isAt(iniPos.row, iniPos.col))
        		c = endPos;
        }

        // update state
        WasState next = new WasState(state, pieces, state.isFinished(), state.getTurn());
        if (next.isWinner(state.getTurn())) {
        	next = new WasState(state, pieces, true, state.getTurn());
        } else if (state.getTurn() == state.WOLF && next.sheepBlocked()){
        	next = new WasState(state, pieces, false, state.getTurn() - 1);
        } else {
            next = new WasState(state, pieces, false, -1);
        }
        return next;
    }
    
    public int getRow() {
        return endPos.row;
    }
    
    public int getCol() {
        return endPos.col;
    }

    public String toString() {
        return "place " + player + " at (" + endPos.row + ", " + endPos.col + ")";
    }

}
