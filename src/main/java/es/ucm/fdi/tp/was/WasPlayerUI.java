package es.ucm.fdi.tp.was;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.view.*;
import es.ucm.fdi.tp.view.ColorTableUI.ColorModel;

public class WasPlayerUI extends PlayerUI< WasState, WasAction >{

	public WasPlayerUI(WasState state) {
		
		FrameUI jf = new FrameUI("Wolf and Sheeps");

		ColorModel cm = new ColorTableUI().new ColorModel(state.getPlayerCount());
		ColorTableUI ct = new ColorTableUI();
		ct.addcolumn
		
		JButton button = new JButton("Button");
		button.setSize(100, 50);
		
		WasBoardUI wb = new WasBoardUI(cm, state);
		jf.add(button, BorderLayout.NORTH);
		jf.add(wb, BorderLayout.CENTER);
		jf.add(ct, BorderLayout.EAST);
		
		
	}
	public static void main(String ... args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WasPlayerUI(new WasState());
			}
		});
	}
	
}
