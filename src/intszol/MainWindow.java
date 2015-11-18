package intszol;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;

public class MainWindow {

	private JFrame initialWindow;
	private InitialPanel startPanel= new InitialPanel();
	private BrowsePanel browsePanel = new BrowsePanel();
	private UploadPanel uploadPanel = new UploadPanel();
	private SearchPanel searchPanel = new SearchPanel();
	public static Utility ut;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {		
		ut = new Utility();
		int i;
		
		ut.delete_annotation(null, null);
		ut.delete_comment(null, null);
		ut.delete_image(null, null);
		
		System.out.print("Get user metadatas with user_id=1" + '\n');
		System.out.print("---------------------------------" + '\n');
		User usr = new User();
		usr = ut.get_user(1);
		System.out.print(usr + "\n" + "\n" + "\n");
		
		
		
		// New images
		System.out.print("Adding new images" + '\n');
		System.out.print("-----------------" + '\n');
		
		i = ut.add_image(1, "kep_1", "20150101", "Budapest");
		System.out.print("user 1, kep_1: " + i + '\n');
		i = ut.add_image(1, "kep_2", "20150101", null);
		System.out.print("user 1, kep_2: " + i + '\n');
		i = ut.add_image(1, "kep_3", null, "Budapest");
		System.out.print("user 1, kep_3: " + i + '\n');
		i = ut.add_image(1, "kep_4", null, null);
		System.out.print("user 1, kep_4: " + i + '\n');
		
		i = ut.add_image(2, "kep_1", "20150101", "Pécs");
		System.out.print("user 2, kep_1: " + i + '\n');
		i = ut.add_image(2, "kep_2", "20150101", null);
		System.out.print("user 2, kep_2: " + i + '\n');
		i = ut.add_image(2, "kep_3", null, "Pécs");
		System.out.print("user 2, kep_3: " + i + '\n');
		i = ut.add_image(2, "kep_4", null, null);
		System.out.print("user 2, kep_4: " + i + '\n' + '\n' + '\n');
		
		
		// Search user_id=2 user's images
		System.out.print("Get images with user_id=2" + '\n');
		System.out.print("-------------------------" + '\n');
		List<Image> img_list = new ArrayList<Image>();
		System.out.print("ID" + "\t" + "USER_ID" + "\t" + "NAME" + "\t" + "DATE" + "\t\t" + "PLACE" + "\n");
		img_list = ut.get_image(null, 2, null, null, null, null);
		for (i=0; i<img_list.size(); i++){
			System.out.print(img_list.get(i) + "\n");
		}
	 
		System.out.print("\n" + "\n");
		
		// Add comment
		System.out.print("Add new comments" + '\n');
		System.out.print("----------------" + '\n');
		ut.add_comment(ut.get_image(null, null, "kep_1", null, null, null).get(0).id  , 1, "ez egy jó kép1");
		System.out.print("user_id = 1" + "\t" + "image_name = kep_1" + "\t" + "content = ez egy jó kép1" + "\n");
		ut.add_comment(ut.get_image(null, 1, "kep_2", null, null, null).get(0).id  , 1, "ez egy jó kép2");
		System.out.print("user_id = 1" + "\t" + "image_name = kep_2" + "\t" + "content = ez egy jó kép2" + "\n");
		ut.add_comment(ut.get_image(null, 1, "kep_2", null, null, null).get(0).id  , 1, "ez egy jó kép3");
		System.out.print("user_id = 1" + "\t" + "image_name = kep_2" + "\t" + "content = ez egy jó kép3" + "\n");
		ut.add_comment(ut.get_image(null, 2, "kep_1", null, null, null).get(0).id  , 2, "ez egy jó kép4");
		System.out.print("user_id = 2" + "\t" + "image_name = kep_1" + "\t" + "content = ez egy jó kép4" + "\n");
		ut.add_comment(ut.get_image(null, 2, "kep_2", null, null, null).get(0).id  , 2, "ez egy jó kép5");
		System.out.print("user_id = 2" + "\t" + "image_name = kep_2" + "\t" + "content = ez egy jó kép5" + "\n");
		ut.add_comment(ut.get_image(null, 2, "kep_2", null, null, null).get(0).id  , 2, "ez egy jó kép6");
		System.out.print("user_id = 2" + "\t" + "image_name = kep_2" + "\t" + "content = ez egy jó kép6" + "\n");		
		
		System.out.print("\n" + "\n");
		
		// Search comment
		System.out.print("Get comments with user_id=1" + '\n');
		System.out.print("---------------------------" + '\n');
		List<Comment> cmt_list = new ArrayList<Comment>();
		System.out.print("IMAGE_ID" + "\t" + "USER_ID" + "\t" + "DATE" + "\t" + "\t" + "COMMENT" + "\n");
		cmt_list = ut.get_comment(null, 1);
		for (i=0; i<cmt_list.size(); i++){
			System.out.print(cmt_list.get(i) + "\n");
		}
		
		System.out.print("\n" + "\n");
		
		
		// Add annotation
		System.out.print("Add annotations" + '\n');
		System.out.print("---------------" + '\n');
		ut.add_annotation(ut.get_image(null, 2, "kep_2", null, null, null).get(0).id, "#party");
		System.out.print("image_name = kep_2" + "\t" + "content = party" + "\n");
		ut.add_annotation(ut.get_image(null, 2, "kep_2", null, null, null).get(0).id, "#balaton");
		System.out.print("image_name = kep_2" + "\t" + "content = balaton" + "\n");
		ut.add_annotation(ut.get_image(null, 2, "kep_2", null, null, null).get(0).id, "#spenót");
		System.out.print("image_name = kep_2" + "\t" + "content = spenót" + "\n");
		ut.add_annotation(ut.get_image(null, 1, "kep_3", null, null, null).get(0).id, "#party2");
		System.out.print("image_name = kep_3" + "\t" + "content = party2" + "\n");
		ut.add_annotation(ut.get_image(null, 1, "kep_3", null, null, null).get(0).id, "#balaton2");
		System.out.print("image_name = kep_3" + "\t" + "content = balaton2" + "\n");
		ut.add_annotation(ut.get_image(null, 1, "kep_3", null, null, null).get(0).id, "#spenót2");
		System.out.print("image_name = kep_3" + "\t" + "content = spenót2" + "\n");
		
		System.out.print("\n" + "\n");
		
		// Search comment
		System.out.print("Get annotations from kep_3" + '\n');
		System.out.print("--------------------------" + '\n');
		List<Annotation> ano_list = new ArrayList<Annotation>();
		System.out.print("IMAGE_ID" + "\t" + "CONTENT" + "\n");
		ano_list = ut.get_annotation(ut.get_image(null, 1, "kep_3", null, null, null).get(0).id);
		for (i=0; i<ano_list.size(); i++){
			System.out.print(ano_list.get(i) + "\n");
		}
		
		System.out.print("\n" + "\n");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.initialWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initialWindow = new JFrame();
		initialWindow.setBounds(100, 100, 800, 670);
		initialWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialWindow.getContentPane().setLayout(null);
		 
		startPanel.setBounds(0, 26, 784, 605);
		initialWindow.getContentPane().add(startPanel);
				
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 784, 26);
		initialWindow.getContentPane().add(menuBar);
		
		JMenu menuFile = new JMenu("F\u00E1jl");
		menuBar.add(menuFile);
		
		JMenuItem menuItemBrowseImages = new JMenuItem("K\u00E9pek b\u00F6ng\u00E9sz\u00E9se");
		menuFile.add(menuItemBrowseImages);
	    menuItemBrowseImages.addActionListener(new MenuAction(startPanel, browsePanel));

				
		JMenuItem menuItemUploadImages = new JMenuItem("K\u00E9pek felt\u00F6lt\u00E9se");
		menuFile.add(menuItemUploadImages);
		menuItemUploadImages.addActionListener(new MenuAction(startPanel, uploadPanel));
		
		JMenuItem menuItemSearch = new JMenuItem("Keres\u00E9s");
		menuFile.add(menuItemSearch);
		menuItemSearch.addActionListener(new MenuAction(startPanel, searchPanel));
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	private class MenuAction implements ActionListener {

	    private JPanel newPanel;
	    private JPanel oldPanel;
	    private MenuAction(JPanel oldPanel, JPanel pnl) {
	        this.newPanel = pnl;
	        this.oldPanel = oldPanel;
	    }
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        changePanel(oldPanel, newPanel);
	    }
	    
	    private void changePanel(JPanel oldPanel, JPanel panel) {
		    oldPanel.removeAll();
		    oldPanel.add(panel);
		    oldPanel.revalidate();
		    oldPanel.repaint();
		}
	}
	
	
}
