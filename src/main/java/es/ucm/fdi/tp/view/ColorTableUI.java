package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.util.Random;

import javax.swing.JTable;

public class ColorTableUI extends JTable{
	
	private static final long serialVersionUID = -9111452566190691458L;

	public class ColorModel{
		
		Color[] colors;
		
		public ColorModel(int numColors) {
			colors = new Color[numColors];
			for(int i = 0; i < numColors; ++i) {
				colors[i] = randColor();
			}
		}
		
		public Color at(int num) {
			return colors[num];
		}
		
		private Color randColor() {
			int red, green, blue;
			Random aux = new Random();
			red = aux.nextInt(256);
			green = aux.nextInt(256);
			blue = aux.nextInt(256);
			return new Color(red, green, blue);
		}
		
	}	
}
