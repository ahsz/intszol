package intszol;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DriveConnector {

	private static final String CLIENT_ID="384864079934-snevj5mc16cnpururbqj8n1ojr0ko9d9.apps.googleusercontent.com";
	private static final String CLIENT_SECRET="K5ay67g-EmFHBeBFr7Fyye7Q";

	private static final String REDIRECT_URL = "urn:ietf:wg:oauth:2.0:oob";

	private static final HttpTransport httpTransport = new NetHttpTransport();
	private static final JsonFactory jsonFactory = new JacksonFactory();

	private static final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
			httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET,
			Arrays.asList(DriveScopes.DRIVE)).setAccessType("online")
			.setApprovalPrompt("auto").build();
	private final String url = flow.newAuthorizationUrl()
			.setRedirectUri(REDIRECT_URL).build();

	private String code = "";

	private static DriveConnector instance = null;
	private static Drive service=null;
	
	public static Drive getService() {
		return service;
	}

	public static void setService(Drive service) {
		DriveConnector.service = service;
	}

	protected DriveConnector() {
		// Exists only to defeat instantiation.
	}

	public static DriveConnector getInstance() {
		if (instance == null) {
			instance = new DriveConnector();
		}
		return instance;
	}

	public String getAuthorizationUrl() {
		return url;
	}

	public void setCode(String code) throws IOException {
		this.code = code;
		GoogleTokenResponse response = flow.newTokenRequest(code)
				.setRedirectUri(REDIRECT_URL).execute();
		GoogleCredential credential = new GoogleCredential()
				.setFromTokenResponse(response);

		 service = new Drive.Builder(httpTransport, jsonFactory,
				credential).build();

	}

	public String[] uploadFile(java.io.File file, String title, String mimeType) throws IOException{

		File body = new File();
		body.setTitle(title);
		//body.setDescription(description);
		body.setMimeType(mimeType);
		
		java.io.File fileContent = file;
		FileContent mediaContent = new FileContent(mimeType, fileContent);
		
		File fileUploaded = service.files().insert(body, mediaContent).execute();
		System.out.println("File ID uploadfilebol: " + fileUploaded.getId());
		String returnString[]=new String[2];
		returnString[0]=fileUploaded.getId();
		returnString[1]=fileUploaded.getDownloadUrl();
		return returnString;
	}

	public InputStream getFileToInputStream(String flieID, String fileUrl){
		com.google.api.services.drive.model.File myFile = new com.google.api.services.drive.model.File();
		myFile.setDownloadUrl(fileUrl);
		myFile.setId(flieID);
		
		InputStream in=downloadFile(service,myFile);
		
		return in;
	}
	
	public void getFileToFile(){
		final Path destination = Paths.get("jovagy.jpg");
		try (
			//teszt miatt ilyen a parameter, ki kell venni
		    final InputStream in = getFileToInputStream("0B5a5__T-xkoVZmFOVUVlaWZqRUk","https://drive.google.com/file/d/0B5a5__T-xkoVaWRKY2VJcEZIZkU/view?usp=drivesdk");
		) {
		    try {
				Files.copy(in, destination);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public List<File> retrieveAllFiles()
			throws IOException {
		List<File> result = new ArrayList<File>();
		Drive.Files.List request = service.files().list();

		do {
			try {
				FileList files = request.execute();

				result.addAll(files.getItems());
				request.setPageToken(files.getNextPageToken());
			} catch (IOException e) {
				System.out.println("An error occurred: " + e);
				request.setPageToken(null);
			}
		} while (request.getPageToken() != null
				&& request.getPageToken().length() > 0);

		return result;
	}
	
	private static InputStream downloadFile(Drive service, File file) {
	    if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
	      try {
	        // uses alt=media query parameter to request content
	        return service.files().get(file.getId()).executeMediaAsInputStream();
	      } catch (IOException e) {
	        // An error occurred.
	        e.printStackTrace();
	        return null;
	      }
	    } else {
	      // The file doesn't have any content stored on Drive.
	      return null;
	    }
	  }

}