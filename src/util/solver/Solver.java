package util.solver;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import util.board.*;
import util.dictionary.*;

public class Solver {
	private static final boolean DEBUG = true;
	
	/* Queue to store the words, pulls words from point (n, n) to point (0, 0) */
	private static PriorityQueue<SolverWord> 	words 		= new PriorityQueue<SolverWord>(64);
	//private static Queue<SolverWord> 	words 		= new LinkedList<SolverWord>();
	
	/* Stack of solved words, if we need to undo a solved word, we pull it from the stack */
	private static Stack<SolverWord> 			undoWords 	= new Stack<SolverWord>();
	
	/**
	 * 
	 */
	private Solver() {};
	
	/**
	 * 
	 */
	public static void solve() {
		// Undo the puzzle if in a solved state
		undoSolve();
		
		// Clear the current queue
		clearWords();
		
		// Get the words from the board
		getWords();
		
		// Print the words
		printWords();
		
		// Solve the puzzle
		long start = System.nanoTime(); 
		boolean solved = _solve(words);
		String msg;
		
		if (solved)
			msg = "Puzzle sovled in " + (double)((System.nanoTime() - start)) / Math.pow(10, 9) + " ms.";
		else
			msg = "Puzzle unsolvable, try another dictionary.";
			
		JOptionPane.showMessageDialog(Board.getBoard(), msg);
	}

	/**
	 * 
	 * @param words
	 * @return
	 */
	private static boolean _solve(Queue<SolverWord> words) {
		
		// End of the words
		if (words.isEmpty())
			return true;
		
		
		println("Next word: " + words.peek().getDetails());
		SolverWord word = words.peek();
		undoWords.push(words.poll());
		
		Stack<String> wordsStack = new Stack<String>();
		wordsStack.addAll(DictionaryWord.getWords(word.getLength(), word.getFilter()));

		while (!wordsStack.isEmpty()) {
			word.solve(wordsStack.pop());
			
			if (_solve(words)) {
				return true;
			}

			word.undo();
		}
		
		word.undo();
		words.add(word);
		undoWords.pop();
		return false;
	}
	
	/**
	 * 
	 */
	public static void undoSolve() {
		while (!undoWords.isEmpty()) {
			SolverWord word = undoWords.pop();
			if (word != null)
				word.undo();;
		}
	}

	/**
	 * 
	 */
	private static void getWords() {
		for (int row = 0 ; row < BoardData.getBoard().getRowCount() ; row++) {
			for (int col = 0 ; col < BoardData.getBoard().getColumnCount() ; col++) {
				addWord(BoardData.getBoard().getValueAt(row, col));
			} //Col
		} //Row
	}
	
	/**
	 * 
	 * @param cell
	 */
	private static void addWord(BoardCell cell) {
		if (cell == null)
			return;
		
		addWord(cell, BoardCell.EAST);
		addWord(cell, BoardCell.SOUTH);
	}
	
	/**
	 * 
	 * @param cell
	 * @param direction
	 */
	private static void addWord(BoardCell cell, Point direction) {
		if (cell.getNext(BoardCell.oppositeDiretion(direction)) != null)
			return;

		LinkedList<BoardCell> word = new LinkedList<BoardCell>();
		
		while (cell != null) {
			word.add(cell);
			cell = cell.getNext(direction);
		}
		
		if (word.size() > 1)
			words.add(new SolverWord(word, direction));
	}
	
	/**
	 * 
	 */
	public static void printWords() {
		String boarder = "=====================================================";
		println(boarder);
		for (SolverWord word : words) {
			println(word.getDetails());
		}
		println(boarder);
	}
	
	/**
	 * 
	 */
	public static void clearWords() {
		words = new PriorityQueue<SolverWord>(64, Collections.reverseOrder());
		undoWords = new Stack<SolverWord>();
	}
	
	/**
	 * 
	 * @param msg
	 */
	private static void print(String msg) {
		if (DEBUG) {
			System.out.print(msg);
		}
	}
	
	/**
	 * 
	 * @param msg
	 */
	private static void println(String msg) {
		print(msg + "\n");
	}
	
}
