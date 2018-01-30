package util.board;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * 
 * @author robb
 *
 */
public class BoardData extends AbstractTableModel {
	public static final BoardData BOARD = new BoardData();
	
	private static final long serialVersionUID = 1L;
	
	public static final Point ACROSS 	= new Point(1,0);
	public static final Point DOWN 		= new Point(0,1);
	
	private BoardCell[][] letters;
	private Stack<Point> lettersPoints = new Stack<Point>();

	/**
	 * 
	 */
	private BoardData() {};
	
	/**
	 * 
	 */
	public static void initializeBoard(int columns, int rows) {
		if (BOARD.letters == null) {
			BOARD.letters = new BoardCell[rows][columns];
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws IllegalStateException
	 */
	public static BoardData getBoard() throws IllegalStateException {
		if (BOARD.letters != null)
			return BOARD;
		else
			throw new IllegalStateException("Board not initialized.");
	}
	
	/**
	 * 
	 */
	public void clearBoard() {
		letters = new BoardCell[letters.length][letters[0].length];
		lettersPoints = new Stack<Point>();
	}
	
	/**
	 * 
	 */
	@Override
	public int getColumnCount() { return this.letters[0].length; }

	/**
	 * 
	 */
	@Override
	public int getRowCount() { return this.letters.length; }

	/**
	 * 
	 */
	@Override
	public BoardCell getValueAt(int arg0, int arg1) {
		if (arg0 >= 0 && arg1 >= 0 && arg0 < getRowCount() && arg1 < getColumnCount())
			return letters[arg0][arg1];
		else
			return null;
	}

	/**
	 * 
	 * @param point
	 * @return
	 */
	public BoardCell getValueAt(Point point) {
		return getValueAt(point.y, point.x);
	}
	
	/**
	 * 
	 * @param col
	 * @param row
	 * @return
	 */
	public boolean validCell(int col, int row) {
		return (col >= 0 && col < getColumnCount() && row >= 0 && row < getRowCount());
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	public boolean validCell(Point point) {
		return validCell(point.x, point.y);
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (letters[rowIndex][columnIndex] instanceof BoardCell) ? true : false;
	}
	
	/**
	 * 
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		String value = aValue.toString().replace("\\W", "");
		
		try {
			letters[rowIndex][columnIndex].setLetter(value.charAt(0));
		}
		catch (Exception e) {
			 errorPrompt(Board.getBoard(), e.toString(), "Error Setting Column Value");
		}		
	}

	/**
	 * 
	 * @param parent
	 * @param msg
	 * @param title
	 */
	public static void errorPrompt(Component parent, String msg, String title) {
		JOptionPane.showMessageDialog(parent, msg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * 
	 * @param current
	 */
	public void activatePoint(Point current) {
		if (letters[current.y][current.x] == null) {
			lettersPoints.push(new Point(current.x, current.y));
			letters[current.y][current.x] = new BoardCell(current.x, current.y);
		}
	}
	
	/**
	 * 
	 */
	public void undoLetter() {
		if (!lettersPoints.isEmpty()) {
			Point temp = lettersPoints.pop();
			letters[temp.y][temp.x] = null;
		}
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	public void saveBoardToFile() throws IOException {
		
		JFileChooser file = new JFileChooser();
		file.setDialogType(JFileChooser.SAVE_DIALOG);
	    file.setCurrentDirectory(new File("data"));
	    int returnVal = file.showSaveDialog(Board.getBoard());
	    
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	Writer writer = new BufferedWriter(new FileWriter(file.getSelectedFile()));
	    			
	    	for (int row = 0 ; row < getRowCount() ; row++) {
	    		for (int col = 0 ; col < getColumnCount() ; col++) {
	    			if (letters[row][col] != null)
	    				writer.write(String.format("%d,%d,%s\n", col, row, letters[row][col].getLetter()));
	    		} // Row
	    	} // Row
	    	writer.close();
	    }
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void loadBoardFromFile() throws IOException {
		JFileChooser file = new JFileChooser();
		file.setDialogType(JFileChooser.OPEN_DIALOG);
	    file.setCurrentDirectory(new File("data"));
	    int returnVal = file.showOpenDialog(Board.getBoard());
	    
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	
	    	@SuppressWarnings("resource")
			Scanner scanner = new Scanner(file.getSelectedFile());
	    	clearBoard();
	    	
	    	while (scanner.hasNext()) {
	    		@SuppressWarnings("resource")
				Scanner line = new Scanner(scanner.nextLine());
	    		line.useDelimiter(",");
	    		
	    		int col = line.nextInt();
	    		int row = line.nextInt();
	    		char letter = line.next().charAt(0);
	    		
	    		if (letter == '_')
	    			letter = BoardCell.EMPTY;
	    		
	    		letters[row][col] = new BoardCell(col, row, letter);
	    		Board.getBoard().repaint();
	    	}
	    }
	}
	
}
