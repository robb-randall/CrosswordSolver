package util.dictionary;
import java.util.*;

public class DictionaryLetter {
	public static final char EMPTY = '\0';
	public static final char MIN_CHAR = 'A';
	public static final char MAX_CHAR = 'Z';
	public static final char[] ADDITIONAL_CHARS = {'\''}; // Implemented in case we want to add any special characters
	
	public char letter;
	public boolean isWordTerminator = false;
	public HashMap<Character, DictionaryLetter> next = new HashMap<Character, DictionaryLetter>(MAX_CHAR - MIN_CHAR);

	/**
	 * 
	 */
	public DictionaryLetter() { this(EMPTY); }

	/**
	 * 
	 * @param letter
	 * @throws IllegalArgumentException
	 */
	public DictionaryLetter(char letter) throws IllegalArgumentException {
		if (!isValidLetter(letter))
			if (letter != EMPTY)
				throw new IllegalArgumentException(String.format("Invalid Letter: \"%s\"", letter));
		
		this.letter = letter;
	}

	/**
	 * Adds a next letter and returns that letter
	 * @param letter
	 * @return
	 */
	public DictionaryLetter addLetter(char letter) {
		if (letter >= 'a' && letter <= 'z')
			letter = Character.toUpperCase(letter);
		
		if (!isValidLetter(letter))
			throw new IllegalArgumentException(String.format("Invalid Letter: \"%s\"", letter));
		
		// Add the word if it doesn't exist
		if (!next.containsKey(letter))
			next.put(letter, new DictionaryLetter(letter));

		// Return the next letter
		return next.get(letter);
	}

	/**
	 * Checks if the letter is a valid letter or not
	 * 
	 * @param letter
	 * @return
	 */
	private static boolean isValidLetter(char letter) {
		if (letter >= MIN_CHAR && letter <= MAX_CHAR)
			return true;
		else if (letter == EMPTY)
			return true;
		else {
			for (char c : ADDITIONAL_CHARS) {
				if (letter == c)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return String.format("%s", (letter == EMPTY) ? "" : letter);
	}
	
}
