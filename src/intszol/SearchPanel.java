package intszol;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel {
	private JTextField txtSearch;

	/**
	 * Create the panel.
	 */
	public SearchPanel() {
		
		txtSearch = new JTextField();
		txtSearch.setText("Search");
		add(txtSearch);
		txtSearch.setColumns(10);
		
	}

}
