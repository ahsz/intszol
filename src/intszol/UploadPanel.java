package intszol;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadPanel extends JPanel {
	private JTextField txtUpload;
	private DriveConnector driveInstance;

	File file=new java.io.File("");
	
	/**
	 * Create the panel.
	 */
	public UploadPanel() {
		
		txtUpload = new JTextField();
		txtUpload.setEditable(false);
		txtUpload.setText("Felt\u00F6lt\u00E9s:");
		txtUpload.setColumns(20);
		
		driveInstance = DriveConnector.getInstance();
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

							returnString = driveInstance.uploadFile(file,file.getName(),"image/jpg");
							String uploadedEvenFileID=returnString[0];
							String uploadedEvenFileUrl=returnString[1];
							String uploadedOddFileID=returnString[2];
							String uploadedOddFileUrl=returnString[3];
							
							int imageID = MainWindow.ut.add_image(2, file.getName(),new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()), "Budapest", uploadedEvenFileID, uploadedEvenFileUrl, uploadedOddFileID, uploadedOddFileUrl);

						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
			            System.out.println("Uploading: " + file.getName());
						txtUpload.setText("Kep feltoltve!");
		            	 
		            	 
		            	 
		            } else{
		    			JOptionPane.showMessageDialog(UploadPanel.this,
		    				    "Rossz formatum, valassz .jpeg-et!!!");
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
