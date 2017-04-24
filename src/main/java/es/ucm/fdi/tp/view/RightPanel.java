package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;

public class RightPanel <S extends GameState<S, A>, A extends GameAction<S, A>> extends JPanel{

	private static final long serialVersionUID = 5798952159009121986L;
	
	ArrayList<GameObserver<S, A>> obs;

	public RightPanel(int numPlayers, ColorModel cm){
		
		Border b = BorderFactory.createLineBorder(Color.BLACK, 2, false);
		obs = new ArrayList<GameObserver<S, A>>();
		
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
			ColorTableUI ct = new ColorTableUI(numPlayers, 2, cm);
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
			colors.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					int row = colors.rowAtPoint(evt.getPoint());
					int col = colors.columnAtPoint(evt.getPoint());
					if (row >= 0 && col >= 0) {
						ct.changeColor(row);
						
					}
				}

			});
			JScrollPane colorTable = new JScrollPane(colors);
			colorTable.setPreferredSize(new Dimension(200, 200));
			colorTable.setBorder(BorderFactory.createTitledBorder(b, "Player Information",
					TitledBorder.CENTER, TitledBorder.TOP));
	
	// East Panel
			this.setLayout(new BorderLayout());
			this.add(status, BorderLayout.CENTER);
			this.add(colorTable, BorderLayout.SOUTH);
	}
}
