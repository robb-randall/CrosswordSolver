package main;

import java.io.*;

import util.dictionary.DictionaryWord;
import util.gui.CrosswordFrame;

/**
 * 
 * @author robb
 *
 */
public class CrosswordSolverMain {

	/**
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		new DictionaryWord("data//Medium.dic");
		new CrosswordFrame(440, 515, 27);
	}
}
