package util.solver;

import java.awt.*;
import java.util.*;
import util.board.*;

public class SolverWord implements Comparable<SolverWord> {

	private Point direction;
	private LinkedList<BoardCell> letters;
	private HashMap<Integer, Character> filter;
	
	/**
	 * 
	 * @param letters
	 */
	public SolverWord(LinkedList<BoardCell> letters, Point direction) {
		this.letters = letters;
		this.direction = direction;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLength() {
		return letters.size();
	}
	
	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, Character> getFilter() {
		filter = new HashMap<Integer, Character>();
		
		for (int i = 0 ; i < letters.size() ; i++) {
			if (letters.get(i) != null && letters.get(i).getLetter() >= 'A' && letters.get(i).getLetter() <= 'Z') {
				filter.put(i+1, letters.get(i).getLetter());
			}
		}
		
		return filter;
	}


	/**
	 * 
	 * @param word
	 * @return
	 */
	public boolean solve(String word) {
		if (word.length() != letters.size()) {
			System.out.println("Lengths don't match");
			return false;
		}
		
		for (int i = 0 ; i < letters.size() ; i++) {
			letters.get(i).setLetter(word.charAt(i));
		}
		
		return true;
	}

	/**
	 * 
	 */
	public void undo() {
		for (int i = 0 ; i < letters.size() ; i++) {
			if (filter != null && filter.containsKey(i+1))
				letters.get(i).setLetter(filter.get(i+1));
			else
				letters.get(i).setLetter(BoardCell.EMPTY);
		}
	}
	
	public String getDetails() {
		String s = (direction == BoardCell.EAST) ? "ACROSS" : "DOWN";
		s += String.format(" (%d, %d)", letters.get(0).y, letters.get(0).x);
		s += " " + getWord();
		return s;
	}
	
	public String getWord() {
		String s = "";

		for (BoardCell letter : letters)
			s += letter.getLetter();
		
		return s;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		
		String s = (direction == BoardCell.EAST) ? "ACROSS : " : "DOWN   : ";

		for (BoardCell letter : letters)
			s += letter.getLetter();
		return s;
	}
	
	/**
	 * 
	 * @return
	 */
	public double distanceFromZero() {
		int x = letters.get(0).x;
		int y = letters.get(0).y;
		return Math.sqrt((x * x) + (y * y));
	}
	
	/**
	 * 
	 * @param arg0
	 * @return
	 */
	@Override
	public int compareTo(SolverWord arg0) {
		
		if (this.distanceFromZero() < arg0.distanceFromZero())
			return -1;
		else
			return 1;
	}

}
