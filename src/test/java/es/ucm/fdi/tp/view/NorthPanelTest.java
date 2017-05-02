package es.ucm.fdi.tp.view;

import java.awt.Dimension;

import javax.swing.JFrame;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.NorthPanel;
import es.ucm.fdi.tp.view.NorthPanel.NorthPanelListener;
import es.ucm.fdi.tp.view.PlayerUI.PlayerMode;

public class NorthPanelTest<S extends GameState<S, A>, A extends GameAction<S, A>> {
	
	/**
	 * Test checking whether the North Panel is shown properly
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String ... args) {
		NorthPanel<?, ?> np = new NorthPanel(0, new NorthPanelListener() {

			@Override
			public void makeRandomMove() {
				System.out.println("Clicked random");
			}

			@Override
			public void makeSmartMove() {
				System.out.println("Clicked smart");
				
			}

			@Override
			public void restartGame() {
				System.out.println("Clicked restart");
				
			}

			@Override
			public void closeGame() {
				System.out.println("Clicked exit");
				
			}

			@Override
			public void changePlayerMode(PlayerMode p) {
				System.out.println("Mode changed to " + p);
			}

			@Override
			public void sendMessage(String s) {
				System.out.println("Sent message: " + s);
			}
			
		});
		
		JFrame j = new JFrame();
		j.add(np);
		j.setSize(new Dimension(400, 100));
		j.setVisible(true);
	}

}
