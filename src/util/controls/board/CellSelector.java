package util.controls.board;

import util.board.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * 
 * @author robb
 *
 */
public class CellSelector implements MouseInputListener {

	private Board board;
	
	/**
	 * 
	 * @param board
	 */
	public CellSelector(Board board) {
		this.board = board;
	}

	/**
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		BoardData.getBoard().activatePoint(new Point(board.getSelectedColumn(), board.getSelectedRow()));
		board.repaint();
	}

	/**
	 * 
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		BoardData.getBoard().activatePoint(new Point(board.getSelectedColumn(), board.getSelectedRow()));
		board.repaint();
	}
	
	/**
	 * 
	 */
	@Override
	public void mouseDragged(MouseEvent arg0) {
		BoardData.getBoard().activatePoint(new Point(board.getSelectedColumn(), board.getSelectedRow()));
		board.repaint();	
	}

	/**
	 * 
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
}
