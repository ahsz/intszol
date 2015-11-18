package intszol;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UploadPanel extends JPanel {
	private JTextField txtUpload;

	/**
	 * Create the panel.
	 */
	public UploadPanel() {
		
		txtUpload = new JTextField();
		txtUpload.setEditable(false);
		txtUpload.setText("Felt\u00F6lt\u00E9s:");
		txtUpload.setColumns(10);
		
		JButton btnSelectFiles = new JButton("F\u00E1jl kiv\u00E1laszt\u00E1sa");
		btnSelectFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtUpload, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelectFiles))
					.addContainerGap(571, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtUpload, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSelectFiles)
					.addContainerGap(420, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
