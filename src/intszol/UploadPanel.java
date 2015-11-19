package intszol;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class UploadPanel extends JPanel {
	private JTextField txtUpload;

	File file=new java.io.File("");
	
	/**
	 * Create the panel.
	 */
	public UploadPanel() {
		
		txtUpload = new JTextField();
		txtUpload.setEditable(false);
		txtUpload.setText("Felt\u00F6lt\u00E9s:");
		txtUpload.setColumns(20);
		
		DriveConnector driveInstance = DriveConnector.getInstance();
		final JFileChooser fc = new JFileChooser();
		JButton btnSelectFiles = new JButton("F\u00E1jl kiv\u00E1laszt\u00E1sa");
		btnSelectFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int returnVal = fc.showOpenDialog(UploadPanel.this);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {

		            if(fc.getSelectedFile().getName().contains(".jpg")){
		            	File selectedFile = fc.getSelectedFile();
		            	 file=selectedFile;
		            	 
		 				String[] returnString;
						try {
							//returnString = driveInstance.uploadFile(new java.io.File("maxresdefault.jpg"),"budspencer","kiraly","image/jpg");
							returnString = driveInstance.uploadFile(file,file.getName(),"image/jpg");
							String uploadedFileID=returnString[0];
							String uploadedFileUrl=returnString[1];
				    		//driveInstance.getFileToFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//ezt a kettot fel kell tolni DB-be
			            //This is where a real application would open the file.
			            System.out.println("Opening: " + file.getName());
						txtUpload.setText("Kep feltoltve!");
		            	 
		            	 
		            	 
		            } else{
		            	 System.out.println("NEM .jpeg!!! " + file.getName());
		            	 
		            }
		            	
		           
		        } else {
		        	System.out.println("Open command cancelled by user.");
		        }
				

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
