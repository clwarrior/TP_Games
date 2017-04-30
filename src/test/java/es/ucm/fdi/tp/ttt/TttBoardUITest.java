package es.ucm.fdi.tp.ttt;

import java.awt.Dimension;

import javax.swing.JFrame;

import es.ucm.fdi.tp.view.ColorTableUI;
import es.ucm.fdi.tp.view.BoardUI.BoardListener;

public class TttBoardUITest {

	public static void main(String ... args) {
		TttBoardUI b = new TttBoardUI(0, new ColorTableUI().new ColorModel(2), new TttState(3), new BoardListener<TttState, TttAction>() {

			@Override
			public void makeManualMove(TttAction a) {
				System.out.println("Clicked on " + a.getRow() + ',' + a.getCol());
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
