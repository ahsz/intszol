package intszol;

import javax.swing.JPanel;
import javax.swing.JTextPane;

public class InitialPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public InitialPanel() {
		
		JTextPane txtpnInitial = new JTextPane();
		txtpnInitial.setText("Initial");
		add(txtpnInitial);
		
	}

}
