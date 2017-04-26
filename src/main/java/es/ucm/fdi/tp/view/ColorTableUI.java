package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.table.AbstractTableModel;

import es.ucm.fdi.tp.extra.jcolor.ColorChooser;

public class ColorTableUI extends AbstractTableModel {

	private static final long serialVersionUID = -1501410827853002864L;

	public class ColorModel {

		Color[] colors;

		public ColorModel(int numColors) {
			colors = new Color[numColors];
			for (int i = 0; i < numColors; ++i) {
				colors[i] = randColor();
			}
		}

		public Color at(int num) {
			return colors[num];
		}

		public void put(int num, Color color){
			colors[num] = color;
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

	private String[] columnNames;
	private String[][] table;
	private int numRows;
	private int numCols;
	private ColorChooser colorChooser;
	private ColorModel cm;
	
	public ColorTableUI() {}

	public ColorTableUI(int numRows, int numCols, ColorModel cm) {
		columnNames = new String[]{"Players", "Colors"};
		table = new String[numRows][numCols];
		this.numCols = numCols;
		this.numRows = numRows;
		this.cm = cm;
		this.colorChooser = new ColorChooser(new JFrame(), "Choose a color", cm.at(0));
	}

	@Override
	public int getColumnCount() {
		return numCols;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex < 0 || columnIndex >= numCols)
			throw new IndexOutOfBoundsException("Table subscript out of range");
		else
			return columnNames[columnIndex];
	}

	@Override
	public int getRowCount() {
		return numRows;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= numRows || columnIndex < 0 || columnIndex >= numCols)
			throw new IndexOutOfBoundsException("Table subscript out of range");
		else
			return table[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= numRows || columnIndex < 0 || columnIndex >= numCols)
			throw new IndexOutOfBoundsException("Table subscript out of range");
		else
			return false;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= numRows || columnIndex < 0 || columnIndex >= numCols)
			throw new IndexOutOfBoundsException("Table subscript out of range");
		else
			table[rowIndex][columnIndex] = (String)aValue;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex){
		if (columnIndex < 0 || columnIndex >= numCols)
			throw new IndexOutOfBoundsException("Table subscript out of range");
		else
			return String.class;
	}	
	
	public void changeColor(int row) {
		colorChooser.setSelectedColorDialog(cm.at(row));
		colorChooser.openDialog();
		if (colorChooser.getColor() != null) {
			cm.put(row, colorChooser.getColor());
		}
	}
}
