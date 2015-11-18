package intszol;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DriveConnector {

	private static String CLIENT_ID="458183055413-rsu0ec4ptd02f7ekc39jins1ntns4kfv.apps.googleusercontent.com";
	private static String CLIENT_SECRET="-QWyKkbc-j6lT1iRY85nAomc";
	
	private static String REDIRECT_URL="urn:ietf:wg:oauth:2.0:oob";
	
	public static void main(String[] args) throws IOException{
		
		HttpTransport httpTransport=new NetHttpTransport();
		JsonFactory jsonFactory=new JacksonFactory();
		
		GoogleAuthorizationCodeFlow flow= new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE)).setAccessType("online").setApprovalPrompt("auto").build();		
		
		String url =flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URL).build();
		System.out.println("Please open the following url");
		System.out.println(" " + url);
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String code=br.readLine();
		
		GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URL).execute();
		GoogleCredential credential= new GoogleCredential().setFromTokenResponse(response);
		
		Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
		
		File body=new File();
		body.setTitle("My document Friss2");
		body.setDescription("A test doc2");
		body.setMimeType("text/plain");
		
		java.io.File fileContent = new java.io.File("document.txt");
		FileContent mediaContent = new FileContent("text/plain", fileContent);
		
		File file = service.files().insert(body, mediaContent).execute();

		
		
		System.out.println("File ID: " + file.getId());
	
		
		List<File> fileList=retrieveAllFiles(service);
		
		File body2=new File();
		body2.setTitle("test22");
		body2.setDescription("A test doc2");
		body2.setMimeType("text/plain");
		
		java.io.File fileContent2 = new java.io.File("test2.txt");
		FileContent mediaContent2 = new FileContent("text/plain", fileContent2);
		File file2 = service.files().insert(body2, mediaContent2).execute();
		System.out.println("File ID: " + file2.getId());

		
		
		
	}
	
	private static List<File> retrieveAllFiles(Drive service) throws IOException {
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
	    } while (request.getPageToken() != null &&
	             request.getPageToken().length() > 0);

	    return result;
	  }

	

}