package es.ucm.fdi.tp.was;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.*;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;

public class WasPlayerUI extends PlayerUI< WasState, WasAction > implements GameObserver<WasState, WasAction>, ActionListener{

	public WasPlayerUI(GameTable<WasState, WasAction> game) {
		
		FrameUI jf = new FrameUI("Wolf and Sheeps");
		Border b = BorderFactory.createLineBorder(Color.BLACK, 2, false);
		
// Status Messages
		String messages = "Hola\nEsto\nEs\nUn\nTexto\nMuy\nLargo\nPara\nVer\nSi\nFunciona la barrita y lo del salto de linea automatico";
		JTextArea text = new JTextArea(messages);
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		
		JScrollPane status = new JScrollPane(text, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		status.setPreferredSize(new Dimension(200, 200));
		status.setBorder(BorderFactory.createTitledBorder(b, "Status Messages",
				TitledBorder.CENTER, TitledBorder.TOP));
		
// Color Table
		int numPlayers = game.getState().getPlayerCount();
		final ColorModel cm = new ColorTableUI().new ColorModel(numPlayers);
		ColorTableUI ct = new ColorTableUI(numPlayers, 2);
		for(int i = 0; i < numPlayers; ++i) {
			ct.setValueAt("Player " + i, i, 0);
			ct.setValueAt("", i, 1);
		}
		JTable colors = new JTable(ct) {
			private static final long serialVersionUID = -2473651432765026169L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				if (col == 1)
					comp.setBackground(cm.at(row));
				else
					comp.setBackground(Color.WHITE);
				comp.setForeground(Color.BLACK);
				return comp;
			}
		};
		JScrollPane colorTable = new JScrollPane(colors);
		colorTable.setPreferredSize(new Dimension(200, 200));
		colorTable.setBorder(BorderFactory.createTitledBorder(b, "Player Information",
				TitledBorder.CENTER, TitledBorder.TOP));
		
// Buttons
		JButton bRandom = createButton("dice.png");
		JButton bSmart = createButton("nerd.png");
		JButton bRestart = createButton("restart.png");
		JButton bExit = createButton("exit.png");
		
		JPanel buttons = new JPanel();
		buttons.add(bRandom);
		buttons.add(bSmart);
		buttons.add(bRestart);
		buttons.add(bExit);
		
// Player Mode
		String modes[] = {"Manual", "Random", "Smart"};
		JComboBox< String > mode = new JComboBox<String>(modes);
		mode.setSelectedIndex(0);
		
		JLabel modeText = new JLabel("Player Mode: ");
		
		JPanel plMode = new JPanel();
		plMode.add(modeText);
		plMode.add(mode);
		
// Board
		WasBoardUI board = new WasBoardUI(cm, game);
		
// North Panel
		JPanel nPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nPanel.add(buttons);
		nPanel.add(plMode);

// East Panel
		JPanel ePanel = new JPanel(new BorderLayout());
		ePanel.add(status, BorderLayout.CENTER);
		ePanel.add(colorTable, BorderLayout.SOUTH);
		
// Frame	
		jf.add(board, BorderLayout.CENTER);
		jf.add(ePanel, BorderLayout.EAST);
		jf.add(nPanel, BorderLayout.NORTH);		
	}
	
	public JButton createButton(String image) {
		JButton b = new JButton();
		b.setIcon(new ImageIcon("src/main/resources/" + image));
		b.addActionListener(this);
		b.setPreferredSize(new Dimension(45, 45));
		return b;
	}
	
	public static void main(String ... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WasPlayerUI(new GameTable<WasState, WasAction>(new WasState()));
			}
		});
	}

	@Override
	public void notifyEvent(GameEvent<WasState, WasAction> e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bRandom){
			
		}
	}
	
}
