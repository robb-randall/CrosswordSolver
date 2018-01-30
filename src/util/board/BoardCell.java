package util.board;

import java.awt.Point;

public class BoardCell extends Point {
	private static final long serialVersionUID = 1L;
	
	public static final Point NORTH = new Point(0, -1);
	public static final Point SOUTH = new Point(0, 1);
	public static final Point EAST  = new Point(1, 0);
	public static final Point WEST  = new Point(-1, 0);

	public static final char EMPTY = '\0';
	private char letter;
	
	/**
	 * 
	 * @param col
	 * @param row
	 */
	public BoardCell(int col, int row) {
		this(col, row, EMPTY);
	}
	
	/**
	 * 
	 * @param col
	 * @param row
	 * @param letter
	 */
	public BoardCell(int col, int row, char letter) {
		super(col, row);
		setLetter(letter);
	}
	
	/**
	 * 
	 * @param letter
	 * @throws IllegalArgumentException
	 */
	public void setLetter(char letter) throws IllegalArgumentException {
		if (letter != EMPTY && !Character.isLetter(letter))
			throw new IllegalArgumentException("Invalid letter: " + letter);
		
		this.letter = Character.toUpperCase(letter);
	}
	
	/**
	 * 
	 * @return
	 */
	public char getLetter() {
		return (this.letter == EMPTY) ? '_' : this.letter;
	}
	
	/**
	 * 
	 * @param direction NORTH, SOUTH, EAST, or WEST
	 * @return
	 */
	public BoardCell getNext(Point direction) {
		return BoardData.getBoard().getValueAt(addPoint(direction));
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	private Point addPoint(Point point) {
		return new Point(this.x + point.x, this.y + point.y);
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	public static Point oppositeDiretion(Point point) {
		return new Point(-(point.x), -(point.y));
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return String.format("%s", this.letter);
	}
	
}
