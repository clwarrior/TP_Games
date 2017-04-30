package es.ucm.fdi.tp.was;

import java.awt.Dimension;

import javax.swing.JFrame;

import es.ucm.fdi.tp.view.BoardUI.BoardListener;
import es.ucm.fdi.tp.view.ColorTableUI;

public class WasBoardUITest {

	public static void main(String ... args) {
		WasBoardUI b = new WasBoardUI(0, new ColorTableUI().new ColorModel(2), new WasState(), new BoardListener<WasState, WasAction>() {

			@Override
			public void makeManualMove(WasAction a) {
				System.out.println("Clicked on " + a.getEndPos());
			}

			@Override
			public void sendMessage(String s) {
				System.out.println("Sent message: " + s);
			}			
		});
		
		JFrame j = new JFrame();
		j.add(b);
		j.setSize(new Dimension(500, 500));
		j.setVisible(true);
	}

	
}
