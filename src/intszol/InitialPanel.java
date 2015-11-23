package intszol;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
//import java.util.stream.Collectors;

public class InitialPanel extends JPanel {
	private JTextField inputAuthCodeTxtField;
	private JButton authLinkButton;
	private JTextField txtTitleComment;
	private JTextField txtTitleComment2;
	private JButton sendAuthStringButton;
	private DriveConnector driveInstance;
	
	public InitialPanel() throws URISyntaxException {
		driveInstance = DriveConnector.getInstance();
		final java.net.URI uri = new java.net.URI(
				driveInstance.getAuthorizationUrl());
		class OpenUrlAction implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				open(uri);
			}
		}

		JButton button = new JButton();
		button.setText("<HTML>Kattints a <FONT color=\"#000099\"><U>linkre</U></FONT>"
				+ " a Drive engedelyezesehez</HTML>");
		button.setToolTipText(uri.toString());
		button.addActionListener(new OpenUrlAction());
		add(button);

		txtTitleComment = new JTextField();
		txtTitleComment.setEditable(false);
		txtTitleComment.setText("Authentikacios kod:");
		txtTitleComment.setColumns(10);
		add(txtTitleComment);

		JPanel commentPanel = new JPanel();
		inputAuthCodeTxtField = new JTextField();
		inputAuthCodeTxtField.setColumns(40);
		add(inputAuthCodeTxtField);

		JPanel authPanel = new JPanel();
		sendAuthStringButton = new JButton("Autentikacios kod megadasa");
		add(sendAuthStringButton);
		
		sendAuthStringButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					driveInstance.setCode(inputAuthCodeTxtField.getText());
					sendAuthStringButton.setEnabled(false);
					txtTitleComment.setText("elfogadva");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		

	}

	private static void open(java.net.URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) { /* TODO: error handling */
			}
		} else { /* TODO: error handling */
		}
	}
	/*
	public static String toString(InputStream inputStream) {
	    BufferedReader reader = new BufferedReader(
	        new InputStreamReader(inputStream));
	    return reader.lines().collect(Collectors.joining(
	        System.getProperty("line.separator")));
	}*/

	
	
	
	

}
