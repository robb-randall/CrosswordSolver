package util.controls;

import java.awt.event.*;
import javax.swing.*;

import util.board.*;
import util.dictionary.DictionaryWord;
import util.solver.*;

public class GuiMenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public GuiMenuBar() {
		this.add(createBoardMenu());
		this.add(createDictionaryMenu());
	}
	
	/**
	 * 
	 * @return
	 */
	private JMenu createBoardMenu() {
		JMenu board = new JMenu("Board");
		board.add(createMenuItem("Open", KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK)));
		board.add(createMenuItem("Save", KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK)));
		board.add(createMenuItem("Solve", KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK)));
		board.addSeparator();
		board.add(createMenuItem("Undo Letter", KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK)));

		board.add(createMenuItem("Undo Solve", KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK)));
		board.addSeparator();
		board.add(createMenuItem("Clear", KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK)));
		return board;
	}
	
	/**
	 * 
	 * @return
	 */
	private JMenu createDictionaryMenu() {
		JMenu dictionary = new JMenu("Dictionary");
		dictionary.add(createMenuItem("Open", KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK), "OpenDict"));
		dictionary.add(createMenuItem("Clear", null, "ClearDict"));
		dictionary.addActionListener(this);
		return dictionary;
	}

	/**
	 * 
	 * @param text
	 * @param accelerator
	 * @return
	 */
	private JMenuItem createMenuItem(String text, KeyStroke accelerator) {
		return createMenuItem(text, accelerator, text);
	}
	
	/**
	 * 
	 * @param text
	 * @param accelerator
	 * @param action
	 * @return
	 */
	private JMenuItem createMenuItem(String text, KeyStroke accelerator, String action) {
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.setMnemonic(text.charAt(0));
		if (accelerator != null)
			menuItem.setAccelerator(accelerator);
		menuItem.addActionListener(this);
		menuItem.setActionCommand(action);
		return menuItem;
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			switch (arg0.getActionCommand())
			{
			// Board
			case "Open" :
				BoardData.getBoard().loadBoardFromFile();
				break;
			case "Save" :
				BoardData.getBoard().saveBoardToFile();
				break;
			case "Solve" :
				Solver.solve();
				break;
			case "Undo Letter" :
				BoardData.getBoard().undoLetter();
				break;
			case "Undo Solve" :
				Solver.undoSolve();
				break;
			case "Clear" :
				BoardData.getBoard().clearBoard();
				break;
				
			// Dictionary
			case "OpenDict" :
				DictionaryWord.loadDictionaryFromFile(this);
				break;
			case "ClearDict" :
				break;
			}
			
			Board.getBoard().repaint();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	
}
