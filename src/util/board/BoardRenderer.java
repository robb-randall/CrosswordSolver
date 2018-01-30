package util.board;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * 
 * @author robb
 *
 */
public class BoardRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        this.setForeground(Color.BLACK);
        
        if (value instanceof BoardCell)
    		this.setBackground(Color.WHITE);
        else
        	this.setBackground(Color.BLACK);
        
        return this;
    }
}
