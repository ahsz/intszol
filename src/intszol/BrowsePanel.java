package intszol;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class BrowsePanel extends JPanel {

	private JButton btnRight;
	private JButton btnLeft;
	private JTextField txtTitleComment;
	private JTextField txtComment;
	private JButton btnModifyImage;
	private JButton btnDeleteImage;
	private JButton btnShareImage;
	
	/**
	 * Create the panel.
	 */
	public BrowsePanel() {
				
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
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addComponent(btnDeleteImage)
		    					.addPreferredGap(ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
		    					.addComponent(btnModifyImage)
		    					.addGap(132)
		    					.addComponent(btnShareImage))
		    				.addComponent(commentPanel, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
		    				.addComponent(imagePanel, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))
		    			.addGap(18)
		    			.addComponent(btnRight, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
		    			.addGap(32))
		    );
		    groupLayout.setVerticalGroup(
		    	groupLayout.createParallelGroup(Alignment.LEADING)
		    		.addGroup(groupLayout.createSequentialGroup()
		    			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addContainerGap()
		    					.addComponent(imagePanel, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
		    					.addPreferredGap(ComponentPlacement.UNRELATED)
		    					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
		    						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
		    							.addComponent(btnShareImage)
		    							.addComponent(btnModifyImage))
		    						.addComponent(btnDeleteImage))
		    					.addGap(17)
		    					.addComponent(commentPanel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addGap(119)
		    					.addComponent(btnRight, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		    				.addGroup(groupLayout.createSequentialGroup()
		    					.addGap(119)
		    					.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
		    			.addContainerGap(192, Short.MAX_VALUE))
		    );
		    
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
		
	}
}
