package intszol;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class UploadPanel extends JPanel {
	private JTextField txtUpload;

	/**
	 * Create the panel.
	 */
	public UploadPanel() {
		
		txtUpload = new JTextField();
		txtUpload.setText("Upload");
		add(txtUpload);
		txtUpload.setColumns(10);

	}

}
