package util.gui;

import javax.swing.*;

import util.board.Board;
import util.controls.GuiMenuBar;
import util.controls.board.ButtonSolve;

import java.awt.*;

/**
 * 
 * @author robb
 *
 */
public class CrosswordFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Crossword Solver";

	/**
	 * 
	 * @param width
	 * @param height
	 * @param puzzleSize
	 */
	public CrosswordFrame(int width, int height, int puzzleSize) {
		super(TITLE);
		this.setSize(width, height);
		this.setMinimumSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setResizable(false);
		
		// Add the board
		Board.initializeBoard(puzzleSize, puzzleSize);
		this.add(Board.getBoard(), BorderLayout.CENTER);
		
		// Add the Menu
		this.setJMenuBar(new GuiMenuBar());
		
		// Add controls
		this.add(new ButtonSolve(), BorderLayout.NORTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}
