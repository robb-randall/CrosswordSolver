package util.dictionary;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;


/**
 * 
 * @author robb
 *
 */
public class DictionaryWord {
	private static boolean debug;
	public static DictionaryLetter root = new DictionaryLetter();
	private static int wordCount = 0; 

	/**
	 * 
	 * @throws FileNotFoundException
	 */
	public DictionaryWord() throws FileNotFoundException {
		this(false, "");
	}
	
	/**
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public DictionaryWord(String file) throws FileNotFoundException {
		this(false, file);
	}
	
	/**
	 * 
	 * @param debug
	 * @param file
	 * @throws FileNotFoundException
	 */
	public DictionaryWord(boolean debug, String file) throws FileNotFoundException {
		if (file.length() > 0)
			loadDictionary(file);
		DictionaryWord.debug = debug;
	}
	
	/**
	 * Adds a word to the dictionary
	 * @param word
	 */
	public static void addWord(String word) {
		try {
			_addWord(root, word);
			wordCount++;
		}
		catch (IllegalArgumentException e) {
			if (debug)
				System.out.println(String.format("\"%s\": %s", word, e));
		}
	}
	
	/**
	 * 
	 * @param current
	 * @param word
	 * @throws IllegalArgumentException
	 */
	private static void _addWord(DictionaryLetter current, String word) throws IllegalArgumentException {
		if (word.length() == 0) {
			if (current.isWordTerminator)
				throw new IllegalArgumentException("Word already exists.");
			current.isWordTerminator = true;
		}
		else
			_addWord(current.addLetter(word.charAt(0)), word.substring(1));
	}
	
	/**
	 * Prints the words for the given length
	 * 
	 * @param length
	 */
	public static LinkedList<String> getWords(int length) {
		return getWords(length, new HashMap<Integer, Character>());
	}
	public static LinkedList<String> getWords(int length, HashMap<Integer, Character> filter) {
		LinkedList<String> temp = new LinkedList<String>();
		_getWords(root, length, filter, temp, "");
		return temp;
	}
	
	/**
	 * 
	 * @return
	 */
	public static int wordCount() { return wordCount; }
	
	/**
	 * 
	 * @param current
	 * @param length
	 * @param filter
	 * @param words
	 * @param word
	 */
	private static void _getWords(DictionaryLetter current, int length, HashMap<Integer, Character> filter, LinkedList<String> words, String word) {
		
		// Print the word
		if (word.length() == length && current.isWordTerminator) {
			words.add(word);
		}
		
		// Apply a filter
		else if (filter.containsKey(word.length()+1)) {
			if (current.next.containsKey(filter.get(word.length()+1))) {
				_getWords(
						current.next.get(filter.get(word.length()+1)),
						length,
						filter,
						words,
						word+filter.get(word.length()+1)
				);
			}
		}
		
		// Go through all the available letters 
		else {
			for (char letter : current.next.keySet()) {
				_getWords(current.next.get(letter), length, filter, words, word + letter);
			}
		}
	}
	
	/**
	 * Load words from a file, each line is a word and single quotes are removed.
	 * Generate form http://app.aspell.net/create
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public static void loadDictionary(String file) throws FileNotFoundException {
		System.out.println("Loading file: " + file);
		Scanner handle = new Scanner(new File(file));

		while (handle.hasNext()) {
			addWord(handle.next());
		}
		
		handle.close();
		System.out.println(wordCount + " words loaded.");
	}
	
	/**
	 * 
	 * @param parent
	 * @throws IOException
	 */
	public static void loadDictionaryFromFile(Component parent) throws IOException {
		JFileChooser file = new JFileChooser();
		file.setDialogType(JFileChooser.OPEN_DIALOG);
	    file.setCurrentDirectory(new File("data"));
	    int returnVal = file.showOpenDialog(parent);
	    
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	DictionaryLetter oldRoot = root;
	    	root = new DictionaryLetter();
	    	int oldWordCount = wordCount;
	    	wordCount = 0;
	    	loadDictionary(file.getSelectedFile().getAbsolutePath());
	    	if (wordCount == 0) {
	    		root = oldRoot;
	    		wordCount = oldWordCount;
	    	}
	    }
	}
	
	/**
	 * 
	 */
	public static void dictionaryStats() {
		HashMap<Integer, Integer> wordsByLength = new HashMap<Integer, Integer>();
		int letterCount = _dictionaryStats(root, 0, wordsByLength);
		int wordCount = 0;
		for (Integer length : wordsByLength.keySet()) {
			System.out.println(String.format("Length %d: %d", length, wordsByLength.get(length)));
			wordCount += wordsByLength.get(length);
		}
		
		System.out.println(String.format("Total Words: %d", wordCount));
		System.out.println(String.format("Total Chars: %d", letterCount));
	}
	
	/**
	 * 
	 * @param current
	 * @param depth
	 * @param wordsByLength
	 * @return
	 */
	private static int _dictionaryStats(DictionaryLetter current, int depth, HashMap<Integer, Integer> wordsByLength) {
		
		if (current == null)
			return 0;
		
		int letterCount = (current.letter != DictionaryLetter.EMPTY) ? 1 : 0;
		
		if (current.isWordTerminator) {
			if (!wordsByLength.containsKey(depth))
				wordsByLength.put(depth, 1);
			else
				wordsByLength.put(depth, wordsByLength.get(depth)+1);
		}
		
		for (Character letter : current.next.keySet()) {
			letterCount += _dictionaryStats(current.next.get(letter), depth+1, wordsByLength);
		}
		
		return letterCount;
	}
	
}
