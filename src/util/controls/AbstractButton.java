package util.controls;

import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author robb
 *
 */
public abstract class AbstractButton extends JButton {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private AbstractButton() {}
	
	/**
	 * 
	 * @param title
	 * @param board
	 */
	protected AbstractButton(String title) {
		super(title);		
		
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runAction();
			}
		});
	}
	
	/**
	 * 
	 */
	protected abstract void runAction();

}
