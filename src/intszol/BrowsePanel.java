package intszol;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class BrowsePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3983851051824262810L;
	private JTextField txtBrowse;

	/**
	 * Create the panel.
	 */
	public BrowsePanel() {
		
		txtBrowse = new JTextField();
		txtBrowse.setText("Browse");
		add(txtBrowse);
		txtBrowse.setColumns(10);
	}

}
