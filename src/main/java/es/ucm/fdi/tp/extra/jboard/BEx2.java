package es.ucm.fdi.tp.extra.jboard;

import java.awt.Color;

import javax.swing.JFrame;

public class BEx2 extends JBoard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame jf = new JFrame();
		jf.add(new BEx2());
		jf.setSize(400, 400);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}

	@Override
	protected void keyTyped(int keyCode) {

	}

	@Override
	protected void mouseClicked(int row, int col, int clickCount,
			int mouseButton) {
		
	}

	@Override
	protected Shape getShape(int player) {
		return player == 0 ? Shape.RECTANGLE: Shape.CIRCLE;
	}

	@Override
	protected Color getColor(int player) {
		// TODO Auto-generated method stub
		return player == 0 ? Color.blue : player==1? Color.red : null;
	}

	@Override
	protected Integer getPosition(int row, int col) {
		// TODO Auto-generated method stub
		return row==0 && col==0 ? 1 : row < col ? 0 : -1;
	}

	@Override
	protected Color getBackground(int row, int col) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getNumRows() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	protected int getNumCols() {
		// TODO Auto-generated method stub
		return 4;
	}

}
