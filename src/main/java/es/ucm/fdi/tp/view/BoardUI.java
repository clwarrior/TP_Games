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
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WasState;

import java.lang.Math;

public abstract class BoardUI extends JFrame {
	
	private static final long serialVersionUID = -7704909457104227261L;
	
	protected GameState<?, ?> state;
	protected JBoard board;
	
	public BoardUI(GameState<?, ?> state){
		super("[=] Wolf and Sheeps [=]");
		this.state = state;
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(board, BorderLayout.CENTER);
		JPanel sizePabel = new JPanel();
		mainPanel.add(sizePabel, BorderLayout.PAGE_START);
		mainPanel.setOpaque(true);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
	}

	protected void setState(WasState state) {
		this.state = state;
	}

	protected void mouseClicked(int row, int col, int clickCount,
			int mouseButton) {
		// TODO Auto-generated method stub
	}

	protected void initializeColors(Color[] c) {
		//int red = Math.random(), green = random(255), blue = random(255); // color aleatorio ???
		c = new Color[]{Color.RED, Color.BLUE};
	}
}

