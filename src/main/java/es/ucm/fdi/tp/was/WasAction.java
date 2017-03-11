package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;

/**
 * An action for Wolf and Sheeps.
 * @author Claudia Guerrero García-Heras and Rafael Herrera Troca
 * @version 1 (13/03/2017)
 */
public class WasAction implements GameAction<WasState, WasAction> {

	private static final long serialVersionUID = -8491198872908329925L;
	
	private int player;
    private WasState.Coord iniPos;
    private WasState.Coord endPos;

    
    public WasAction(int player, WasState.Coord fin, WasState.Coord ini) {
        this.player = player;
        this.endPos = fin.clone();
        this.iniPos = ini.clone();
        
    }

    public int getPlayerNumber() {
        return player;
    }

    /**
     * Method which applies the action to the game state given
     * @param state It contains the current game state
     * @return WasState which contains the new game state after applying the action
     */
    public WasState applyTo(WasState state) {
        if (player != state.getTurn()) {
            throw new IllegalArgumentException("Not the turn of this player");
        }

        // make move
        WasState.Coord[] pieces = state.getPieces();
        for(int i = 0; i < pieces.length; ++i){
        	if(pieces[i].isAt(iniPos.row, iniPos.col))
        		pieces[i] = endPos;
        }

        // update state
        WasState next = new WasState(state, pieces, state.isFinished(), -1);
        if (next.isWinner((state.getTurn()))) {
        	next = new WasState(state, pieces, true, state.getTurn());
        } else if (next.getTurn() == next.SHEEP && next.sheepBlocked()){
        	state = new WasState(state, pieces, false, -1);
        	next = new WasState(state, pieces, false, -1);
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
        String show = "Move ";
        if(player == 0)
        	show = show + "wolf";
        else
        	show = show + "sheep";
        show = show + " from " + iniPos + " to " + endPos;
        return show;
    }

}
