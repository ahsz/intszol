package intszol;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class BrowsePanel extends JPanel {

	private JButton btnRight;
	private JButton btnLeft;
	private JTextField txtTitleComment;
	private JTextField txtComment;
	private JButton btnModifyImage;
	private JButton btnDeleteImage;
	private JButton btnShareImage;
	private JTextField txtImage;
	
	private int currentImgId;
	private int currentImgIndex;
	private ArrayList<Image> imgList;
	private DriveConnector driveInstance;
	private JLabel imageLabel;
	
	/**
	 * Create the panel.
	 */
	public BrowsePanel() {
		driveInstance = DriveConnector.getInstance();
		btnRight = new JButton("");
		
		 try {
			    BufferedImage img = ImageIO.read(getClass().getResource("/images/right_arrow.png"));
			    btnRight.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {
			  }
		
		btnLeft = new JButton("");		
		
		JPanel imagePanel = new JPanel();
		try {
		    BufferedImage img = ImageIO.read(getClass().getResource("/images/left_arrow.png"));
		    btnLeft.setIcon(new ImageIcon(img));
		    
		    JPanel commentPanel = new JPanel();
		    
		    btnModifyImage = new JButton("M\u00F3dos\u00EDt\u00E1s");
		    
		    btnDeleteImage = new JButton("T\u00F6rl\u00E9s");
		    
		    btnShareImage = new JButton("Megoszt\u00E1s");
		    GroupLayout groupLayout = new GroupLayout(this);
		    groupLayout.setHorizontalGroup(
		    	groupLayout.createParallelGroup(Alignment.LEADING)
		    		.addGroup(groupLayout.createSequentialGroup()
		    			.addGap(49)
		    			.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
		    			.addGap(18)
		    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		    				.addComponent(imagePanel, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addComponent(btnDeleteImage)
		    					.addGap(146)
		    					.addComponent(btnModifyImage)
		    					.addPreferredGap(ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
		    					.addComponent(btnShareImage))
		    				.addComponent(commentPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))
		    			.addGap(18)
		    			.addComponent(btnRight, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
		    			.addGap(32))
		    );
		    groupLayout.setVerticalGroup(
		    	groupLayout.createParallelGroup(Alignment.LEADING)
		    		.addGroup(groupLayout.createSequentialGroup()
		    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addGap(119)
		    					.addComponent(btnRight, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addGap(119)
		    					.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addContainerGap()
		    					.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)))
		    			.addGap(29)
		    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
		    				.addComponent(btnDeleteImage)
		    				.addComponent(btnModifyImage)
		    				.addComponent(btnShareImage))
		    			.addGap(29)
		    			.addComponent(commentPanel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
		    			.addContainerGap(41, Short.MAX_VALUE))
		    );
		    
		    txtImage = new JTextField();
		    txtImage.setEditable(false);
		    txtImage.setColumns(10);
		    
		    txtTitleComment = new JTextField();
		    txtTitleComment.setEditable(false);
		    txtTitleComment.setText("Megjegyz\u00E9s:");
		    txtTitleComment.setColumns(10);
		    
		    txtComment = new JTextField();
		    txtComment.setColumns(10);
		    GroupLayout gl_commentPanel = new GroupLayout(commentPanel);
		    gl_commentPanel.setHorizontalGroup(
		    	gl_commentPanel.createParallelGroup(Alignment.LEADING)
		    		.addGroup(gl_commentPanel.createSequentialGroup()
		    			.addComponent(txtTitleComment, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
		    			.addContainerGap(447, Short.MAX_VALUE))
		    		.addComponent(txtComment, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
		    );
		    gl_commentPanel.setVerticalGroup(
		    	gl_commentPanel.createParallelGroup(Alignment.LEADING)
		    		.addGroup(gl_commentPanel.createSequentialGroup()
		    			.addComponent(txtTitleComment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    			.addPreferredGap(ComponentPlacement.RELATED)
		    			.addComponent(txtComment, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
		    			.addContainerGap(21, Short.MAX_VALUE))
		    );
		    commentPanel.setLayout(gl_commentPanel);
		    setLayout(groupLayout);
		  } catch (IOException ex) {
		  }
		
		imgList = new ArrayList<Image>();
		System.out.print("ID" + "\t" + "USER_ID" + "\t" + "NAME" + "\t" + "DATE" + "\t\t" + "PLACE" + "\n");
		imgList = (ArrayList<Image>) MainWindow.ut.get_image(null, 2, null, null, null, null,null,null);
		for (int i=0; i<imgList.size(); i++){
			System.out.print(imgList.get(i) + "\n");
		}
		
		if(imgList.size() != 0){
			imgList = (ArrayList<Image>) MainWindow.ut.get_image(null, 2, null, null, null, null,null,null);
		}
		
		if(imgList.size() != 0){
			currentImgId = imgList.get(0).id;
			currentImgIndex = 0;
			txtImage.setText(imgList.get(currentImgIndex).name);
		}
		imageLabel = new JLabel("");
		GroupLayout gl_imagePanel = new GroupLayout(imagePanel);
		gl_imagePanel.setHorizontalGroup(
			gl_imagePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_imagePanel.createSequentialGroup()
					.addGroup(gl_imagePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_imagePanel.createSequentialGroup()
							.addGap(183)
							.addComponent(txtImage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_imagePanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(imageLabel, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_imagePanel.setVerticalGroup(
			gl_imagePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_imagePanel.createSequentialGroup()
					.addGap(5)
					.addComponent(txtImage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(imageLabel, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
					.addContainerGap())
		);
		imagePanel.setLayout(gl_imagePanel);
		if(imgList.size() != 0 && MainWindow.ut.get_comment(currentImgId, null).size() != 0)
			txtComment.setText(MainWindow.ut.get_comment(currentImgId, null).get(0).content);
		else
			txtComment.setText("");
		
		File downloadedImage;
		ImageIcon icon;
		try {
			if(imgList.size() != 0){
				downloadedImage = driveInstance.getFile(imgList.get(currentImgIndex).gd_id, imgList.get(currentImgIndex).gd_url, imgList.get(currentImgIndex).gd_id2, imgList.get(currentImgIndex).gd_url2, imgList.get(currentImgIndex).name);
				BufferedImage img=ImageIO.read(downloadedImage);
		        icon=new ImageIcon(img);
				imageLabel.setIcon(icon);
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(imgList.size() != (currentImgIndex + 1) && imgList.get(currentImgIndex + 1) != null){
					currentImgId = imgList.get(currentImgIndex + 1).id;
					currentImgIndex++;
					txtImage.setText(imgList.get(currentImgIndex).name);
					if(MainWindow.ut.get_comment(currentImgId, null).size() !=0)
						txtComment.setText(MainWindow.ut.get_comment(currentImgId, null).get(0).content);
					else
						txtComment.setText("");
					
					File downloadedImage;
		    		ImageIcon icon;
		    		try {
		    			downloadedImage = driveInstance.getFile(imgList.get(currentImgIndex).gd_id, imgList.get(currentImgIndex).gd_url, imgList.get(currentImgIndex).gd_id2, imgList.get(currentImgIndex).gd_url2, imgList.get(currentImgIndex).name);
		    			BufferedImage img=ImageIO.read(downloadedImage);
		    	        icon=new ImageIcon(img);
		    			imageLabel.setIcon(icon);
		    			
		    		} catch (IOException e1) {
		    			// TODO Auto-generated catch block
		    			e1.printStackTrace();
		    		} 
				}
			}
		});
		
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(imgList.size() != (currentImgIndex - 1) && currentImgIndex > 0 && imgList.get(currentImgIndex - 1) != null){
					
					try {
						driveInstance.getFile(imgList.get(currentImgIndex).gd_id, imgList.get(currentImgIndex).gd_url, imgList.get(currentImgIndex).gd_id2, imgList.get(currentImgIndex).gd_url2, imgList.get(currentImgIndex).name);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					
					currentImgId = imgList.get(currentImgIndex - 1).id;
					currentImgIndex--;
					txtImage.setText(imgList.get(currentImgIndex).name);
					if(MainWindow.ut.get_comment(currentImgId, null).size() !=0)
						txtComment.setText(MainWindow.ut.get_comment(currentImgId, null).get(0).content);
					else
						txtComment.setText("");
					
					File downloadedImage;
		    		ImageIcon icon;
		    		try {
		    			downloadedImage = driveInstance.getFile(imgList.get(currentImgIndex).gd_id, imgList.get(currentImgIndex).gd_url, imgList.get(currentImgIndex).gd_id2, imgList.get(currentImgIndex).gd_url2, imgList.get(currentImgIndex).name);
		    			BufferedImage img=ImageIO.read(downloadedImage);
		    	        icon=new ImageIcon(img);
		    			imageLabel.setIcon(icon);
		    			
		    		} catch (IOException e1) {
		    			// TODO Auto-generated catch block
		    			e1.printStackTrace();
		    		} 
				}
			}
		});
	}
}
