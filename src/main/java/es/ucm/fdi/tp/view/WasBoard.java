package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.BoardExample;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.extra.jboard.JBoard.Shape;
import es.ucm.fdi.tp.was.WasState;

import java.lang.Math;

public class WasBoard extends GenericBoard<WasState> {
	
	private static final long serialVersionUID = -7704909457104227261L;
	
	private WasState state;
	
	public WasBoard(){
		super(WasState.dim, WasState.dim);
		repaint();
	}
	
	public WasBoard(BoardActionListener bal) {
		super(WasState.dim, WasState.dim);
	}

	@Override
	protected void setState(WasState state) {
		this.state = state;
	}

	@Override
	protected void mouseClicked(int row, int col, int clickCount,
			int mouseButton) {
		// TODO Auto-generated method stub
	}

	@Override
	protected Integer getPosition(int row, int col) {
		return state.at(row,  col);
	}

	@Override
	protected void initializeColors(Color[] c) {
	//	int red = Math.random(255), green = random(255), blue = random(255); // color aleatorio ???
		c = new Color[]{Color.RED, Color.BLUE};
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WasBoard();
			}
		});
	}
}

