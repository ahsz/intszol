package intszol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SearchPanel extends JPanel {

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
	private JTextField txtSearchTitle;
	private JTextField txtSearchArea;
	private JRadioButton radioBtnSearchForComments;
	private JRadioButton radioButtonSearchForDate;
	
	private ArrayList<Image> imgList;
	private ArrayList<Image> filteredImages;
	
	/**
	 * Create the panel.
	 */
	public SearchPanel() {
		
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
		    
		    txtSearchTitle = new JTextField();
		    txtSearchTitle.setEditable(false);
		    txtSearchTitle.setText("Keres\u00E9s:");
		    txtSearchTitle.setColumns(10);
		    
		    txtSearchArea = new JTextField();
		    txtSearchArea.addMouseListener(new MouseAdapter() {
		    	  @Override
		    	  public void mouseClicked(MouseEvent e) {
		    	    txtSearchArea.setText("");
		    	  }
		    	});
		    txtSearchArea.setColumns(10);
		    
		    radioBtnSearchForComments = new JRadioButton("Megjegyz\u00E9sre");
		    radioBtnSearchForComments.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		radioButtonSearchForDate.setSelected(false);
		    		txtSearchArea.setText("Írja be a keresendő megjegyzést!");
		    	}
		    });
		    radioButtonSearchForDate= new JRadioButton("D\u00E1tumra");
		    radioButtonSearchForDate.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		radioBtnSearchForComments.setSelected(false);
		    		txtSearchArea.setText("2015-01-01");
		    	}
		    });
		    radioButtonSearchForDate.setSelected(true);
		    txtSearchArea.setText("2015-01-01");
		    
		    filteredImages = new ArrayList<>();
		    imgList = new ArrayList<Image>();
			imgList = (ArrayList<Image>) MainWindow.ut.get_image(null, 2, null, null, null, null,null);
		    
		    JButton btnSearch = new JButton("Keres\u00E9s");
		    btnSearch.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		imgList.clear();
		    		btnRight.setVisible(false);
		    		btnLeft.setVisible(false);
		    		txtTitleComment.setVisible(false);
		    		txtComment.setVisible(false);
		    		btnModifyImage.setVisible(false);
		    		btnDeleteImage.setVisible(false);
		    		btnShareImage.setVisible(false);
		    		txtImage.setVisible(false);
		    		
		    		if(radioBtnSearchForComments.isSelected()){
		    			List<Comment> commentList = new ArrayList<Comment>();
		    			commentList = MainWindow.ut.get_comment(null, 2); //TODO itt be van égetve a userID, ha több usert akarunk akkor dinamikussá kell ezt tenni
		    			for (int i=0; i<commentList.size(); i++){
							if(commentList.get(i).content.toLowerCase().contains((String)(txtSearchArea.getText()).toLowerCase())){
		    					filteredImages.addAll((ArrayList<Image>) MainWindow.ut.get_image(commentList.get(i).image_id, 2, null, null, null, null,null));
		    					if(filteredImages.size() > 0){
		    						btnRight.setVisible(true);;
		    						btnLeft.setVisible(true);;
		    						txtTitleComment.setVisible(true);;
		    						txtComment.setVisible(true);;
		    						btnModifyImage.setVisible(true);;
		    						btnDeleteImage.setVisible(true);;
		    						btnShareImage.setVisible(true);
		    						txtImage.setVisible(true);
		    						imgList = filteredImages;
		    					}
							}
		    				
		    			}

		    		}
		    		if(radioButtonSearchForDate.isSelected() && MainWindow.ut.get_image(null, 2, null, txtSearchArea.getText(), txtSearchArea.getText(), null,null).size() !=0){
		    			imgList = (ArrayList<Image>) MainWindow.ut.get_image(null, 2, null, txtSearchArea.getText(), txtSearchArea.getText(), null,null);
		    			btnRight.setVisible(true);;
						btnLeft.setVisible(true);;
						txtTitleComment.setVisible(true);;
						txtComment.setVisible(true);;
						btnModifyImage.setVisible(true);;
						btnDeleteImage.setVisible(true);;
						btnShareImage.setVisible(true);
						txtImage.setVisible(true);
		    		}
		    		
		    		if(imgList.size()==0)
		    			JOptionPane.showMessageDialog(SearchPanel.this,
		    				    "Nincs találat!");
		    	}
		    });
		    
		    GroupLayout groupLayout = new GroupLayout(this);
		    groupLayout.setHorizontalGroup(
		    	groupLayout.createParallelGroup(Alignment.LEADING)
		    		.addGroup(groupLayout.createSequentialGroup()
		    			.addGap(37)
		    			.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
		    			.addGap(18)
		    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		    						.addGroup(groupLayout.createSequentialGroup()
		    							.addComponent(btnDeleteImage)
		    							.addGap(147)
		    							.addComponent(btnModifyImage)
		    							.addPreferredGap(ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
		    							.addComponent(btnShareImage))
		    						.addComponent(commentPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
		    						.addComponent(imagePanel, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE))
		    					.addGap(18)
		    					.addComponent(btnRight, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
		    					.addGap(20))
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
		    						.addComponent(txtSearchArea)
		    						.addGroup(groupLayout.createSequentialGroup()
		    							.addComponent(txtSearchTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    							.addGap(18)
		    							.addComponent(radioButtonSearchForDate)
		    							.addPreferredGap(ComponentPlacement.UNRELATED)
		    							.addComponent(radioBtnSearchForComments, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
		    					.addGap(18)
		    					.addComponent(btnSearch)
		    					.addGap(217))))
		    );
		    groupLayout.setVerticalGroup(
		    	groupLayout.createParallelGroup(Alignment.LEADING)
		    		.addGroup(groupLayout.createSequentialGroup()
		    			.addContainerGap()
		    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
		    				.addComponent(txtSearchTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    				.addComponent(radioButtonSearchForDate)
		    				.addComponent(radioBtnSearchForComments))
		    			.addGap(8)
		    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
		    				.addComponent(txtSearchArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    				.addComponent(btnSearch))
		    			.addPreferredGap(ComponentPlacement.UNRELATED)
		    			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addGap(123)
		    					.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
		    					.addPreferredGap(ComponentPlacement.RELATED, 113, Short.MAX_VALUE))
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
		    					.addPreferredGap(ComponentPlacement.UNRELATED)))
		    			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
		    				.addComponent(btnShareImage)
		    				.addComponent(btnModifyImage)
		    				.addComponent(btnDeleteImage))
		    			.addGap(18)
		    			.addComponent(commentPanel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
		    			.addGap(33))
		    		.addGroup(groupLayout.createSequentialGroup()
		    			.addGap(184)
		    			.addComponent(btnRight, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
		    			.addContainerGap(269, Short.MAX_VALUE))
		    );
		    
		    txtImage = new JTextField();
		    txtImage.setEditable(false);
		    imagePanel.add(txtImage);
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
		    			.addContainerGap(508, Short.MAX_VALUE))
		    		.addComponent(txtComment, GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
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
		
		btnRight.setVisible(false);
		btnLeft.setVisible(false);
		txtTitleComment.setVisible(false);
		txtComment.setVisible(false);
		btnModifyImage.setVisible(false);
		btnDeleteImage.setVisible(false);
		btnShareImage.setVisible(false);
		txtImage.setVisible(false);
		
		if(radioButtonSearchForDate.isSelected())
			txtSearchArea.setText("ilyen formátumba írd be, majd kitaláljuk");
		if(radioBtnSearchForComments.isSelected())
			txtSearchArea.setText("Írja be a megjegyzést, melyre rákeresne!");
		
		currentImgId = imgList.get(0).id;
		currentImgIndex = 0;
		txtImage.setText(imgList.get(currentImgIndex).name);
		if(MainWindow.ut.get_comment(currentImgId, null) != null)
			txtComment.setText(MainWindow.ut.get_comment(currentImgId, null).get(0).content);
		else
			txtComment.setText("");
		
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
				}
			}
		});
		
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(imgList.size() != (currentImgIndex - 1) && currentImgIndex > 0 && imgList.get(currentImgIndex - 1) != null){
					currentImgId = imgList.get(currentImgIndex - 1).id;
					currentImgIndex--;
					txtImage.setText(imgList.get(currentImgIndex).name);
					if(MainWindow.ut.get_comment(currentImgId, null).size() !=0)
						txtComment.setText(MainWindow.ut.get_comment(currentImgId, null).get(0).content);
					else
						txtComment.setText("");
				}
			}
		});
	}
}
