package es.ucm.fdi.tp.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import es.ucm.fdi.tp.view.RightPanel.RightPanelListener;

public class RightPanelTest {

	/**
	 * Test checking whether the Right Panel is shown properly
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String ... args) {
		
		RightPanel<?, ?> rp = new RightPanel(3, new ColorTableUI().new ColorModel(3), new RightPanelListener(){

			public void changeColor() {
				System.out.println("Clicked ColorTable");
			}
			
		}
		, 0);
		
		JFrame j = new JFrame();
		j.add(rp);
		j.setSize(new Dimension(400, 600));
		j.setVisible(true);
		rp.addMessage("Hola");
	}
}

