package util.board;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import util.controls.board.*;

/**
 * 
 * @author robb
 *
 */
public class Board extends JTable {
	private static final long serialVersionUID = 1L;

	private static final Board BOARD = new Board();
	
	private static final int FONT_SIZE = 12;
	private static final Font FONT = new Font(Font.MONOSPACED, Font.BOLD, FONT_SIZE);
	
	private static BoardRenderer renderer = new BoardRenderer();
	
	private Board() {}

	/**
	 * 
	 * @param columns
	 * @param rows
	 */
	public static void initializeBoard (int columns, int rows) {
		BOARD.addMouseMotionListener(new CellSelector(BOARD));

		// Initialize the board
		BoardData.initializeBoard(columns, rows);
		BOARD.setModel(BoardData.getBoard());
		
		// Table selection
		BOARD.setBorder(BorderFactory.createLineBorder(Color.black));
		BOARD.setGridColor(Color.black);
		BOARD.setFont(FONT);
		BOARD.setModel(BoardData.getBoard());
		
		// Cell properties
		renderer.setFont(FONT);
		renderer.setHorizontalAlignment(JLabel.CENTER);
		
		for (int i = 0 ; i < BOARD.getColumnCount() ; i++) {
			TableColumn col = BOARD.getColumnModel().getColumn(i);
			col.setCellRenderer(renderer);

			col.setMinWidth(BOARD.getRowHeight());
			col.setMaxWidth(BOARD.getRowHeight());
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static Board getBoard() {
		return BOARD;
	}
	
}
