package util.controls.board;

import util.board.*;
import util.controls.*;
import util.solver.*;

public class ButtonSolve extends AbstractButton {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public ButtonSolve() {
		super("Solve");
	}

	/**
	 * 
	 */
	@Override
	protected void runAction() {
		Solver.solve();
		Board.getBoard().repaint();
	}

}
