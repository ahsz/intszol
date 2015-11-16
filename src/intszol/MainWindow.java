package intszol;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
