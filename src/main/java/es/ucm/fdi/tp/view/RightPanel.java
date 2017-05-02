package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.logging.Logger;

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
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;

public class RightPanel<S extends GameState<S, A>, A extends GameAction<S, A>>
		extends JPanel implements GameObserver<S, A> {

	private static final long serialVersionUID = 5798952159009121986L;

	/**
	 * Listener to the actions made in this panel
	 * @author Claudia Guerrero García-Heras and Rafael Herrera Troca
	 * @version 1 (03/05/2017)
	 */
	public interface RightPanelListener {
		public void changeColor();
	}

	private Logger log;
	private int id;
	private JTextArea text;
	private ColorTableUI ct;
	private ColorModel cm;
	private RightPanelListener listener;

	public RightPanel(int numPlayers, ColorModel cm,
			RightPanelListener listener, int id) {

		this.log = Logger.getLogger("log");
		this.id = id;
		this.listener = listener;
		this.cm = cm;
		this.text = new JTextArea("");
		this.ct = new ColorTableUI(numPlayers, 2, cm);

		initGUI();

	}

	/**
	 * Initializes the north panel with the default values
	 */
	private void initGUI() {
		Border b = BorderFactory.createLineBorder(Color.BLACK, 2, false);

		// Status Messages
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
		for (int i = 0; i < ct.getRowCount(); ++i) {
			ct.setValueAt("Player " + i, i, 0);
			ct.setValueAt("", i, 1);
		}
		JTable colors = new JTable(ct) {
			private static final long serialVersionUID = -2473651432765026169L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer,
					int row, int col) {
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
					log.info("Player " + id + " clicked in the color table");
					listener.changeColor();
				}
			}
		});
		colors.setToolTipText("Click a row to change the player color");
		JScrollPane colorTable = new JScrollPane(colors);
		colorTable.setPreferredSize(new Dimension(200, 100));
		colorTable.setBorder(BorderFactory.createTitledBorder(b,
				"Player Information", TitledBorder.CENTER, TitledBorder.TOP));

		// East Panel
		this.setLayout(new BorderLayout());
		this.add(status, BorderLayout.CENTER);
		this.add(colorTable, BorderLayout.SOUTH);
	}

	/**
	 * Adds a message to the status messages box
	 * @param message
	 */
	public void addMessage(String message) {
		log.info("Sent message: \"" + message + "\" to player " + id );
		text.append(message + '\n');
	}

	/**
	 * Clears the status messages box
	 */
	public void clearMessages() {
		log.info("Messages clear to player " + id);
		text.setText("");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		switch (e.getType()) {
		case Start:
			clearMessages();
			addMessage("The game has started.");
			break;
		case Stop:
				addMessage("The game has ended.");
				int winner = e.getState().getWinner();
				if (winner == id)
					addMessage("Congratulations. You have won.");
				else if(winner != -1)
					addMessage("The winner is player "
							+ e.getState().getWinner() + '.');
				else
					addMessage("It's a draw!");
				break;
		default:
			break;
		}
		repaint();
	}
}
