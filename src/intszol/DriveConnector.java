package intszol;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.IOUtils;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

	//TODO download fuggveny ami meghivja ket reszbol az osszerakast
	
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

		File evenBody = new File();
		evenBody.setTitle(title+"even");
		//body.setDescription(description);
		evenBody.setMimeType(mimeType);
		
		java.io.File evenFileContent = getEvenFileHalf(file);
		FileContent evenMediaContent = new FileContent(mimeType, evenFileContent);
		
		File evenFileUploaded = service.files().insert(evenBody, evenMediaContent).execute();
		System.out.println("File ID uploadfilebol: " + evenFileUploaded.getId());
		System.out.println("File URL uploadfilebol: " + evenFileUploaded.getDownloadUrl());
		
		File oddBody = new File();
		oddBody.setTitle(title+"odd");
		//body.setDescription(description);
		oddBody.setMimeType(mimeType);
		
		
		java.io.File oddFileContent = getOddFileHalf(file);
		FileContent oddMediaContent = new FileContent(mimeType, oddFileContent);
		
		File oddFileUploaded = service.files().insert(oddBody, oddMediaContent).execute();
		System.out.println("File ID uploadfilebol: " + oddFileUploaded.getId());
		
		
		
		String returnString[]=new String[4];
		returnString[0]=evenFileUploaded.getId();
		returnString[1]=evenFileUploaded.getDownloadUrl();
		returnString[2]=oddFileUploaded.getId();
		returnString[3]=oddFileUploaded.getDownloadUrl();
		oddFileContent.delete();
		evenFileContent.delete();

		return returnString;
	}

	public InputStream getFileToInputStream(String flieID, String fileUrl){
		com.google.api.services.drive.model.File myFile = new com.google.api.services.drive.model.File();
		myFile.setDownloadUrl(fileUrl);
		myFile.setId(flieID);
		
		InputStream in=downloadFile(service,myFile);
		
		return in;
	}
	
	

	public java.io.File getEvenFileHalf(java.io.File inputFile) throws IOException{
		byte[] bytes = Files.readAllBytes(inputFile.toPath());
		StringBuilder evenHalf=new StringBuilder("");
		String binaryFile=toBinary(bytes);
		String oddHalf="";
		//String evenHalf="";
		for(int i=0;i<binaryFile.length();i++){
			if((i%2)==0)evenHalf.append(binaryFile.charAt(i));
			
		}
		byte[] evenBytes=fromBinary(evenHalf.toString());
		

		java.io.File evenFile=new java.io.File("tempeven");
		
		try (FileOutputStream fos = new FileOutputStream(evenFile.getPath())) {
		    fos.write(evenBytes);
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
		return evenFile;
	}
	
	public java.io.File getOddFileHalf(java.io.File inputFile) throws IOException{
		byte[] bytes = Files.readAllBytes(inputFile.toPath());
		StringBuilder oddHalf=new StringBuilder("");
		String binaryFile=toBinary(bytes);
		//String oddHalf="";
		String evenHalf="";
		for(int i=0;i<binaryFile.length();i++){
			if((i%2)==1)oddHalf.append(binaryFile.charAt(i));
			
		}
		byte[] oddBytes=fromBinary(oddHalf.toString());
		
		java.io.File oddFile=new java.io.File("tempodd");
		
		try (FileOutputStream fos = new FileOutputStream(oddFile.getPath())) {
		    fos.write(oddBytes);
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
		return oddFile;
	}
	
	public java.io.File makeFileFromHalves(java.io.File evenFile, java.io.File oddFile, String fileName) throws IOException{
		byte[] evenBytes = Files.readAllBytes(evenFile.toPath());
		byte[] oddBytes = Files.readAllBytes(oddFile.toPath());
		
		String evenBinaryFile=toBinary(evenBytes);
		String oddBinaryFile=toBinary(oddBytes);
		
		String added="";
		for(int i=0;i<evenBinaryFile.length();i++){
			added+=evenBinaryFile.charAt(i);
			added+=oddBinaryFile.charAt(i);
		}
		
		byte[] addedBytes=fromBinary(added);
		java.io.File addedFile=new java.io.File("fileName.jpg");
		
		try (FileOutputStream fos = new FileOutputStream(addedFile.getPath())) {
		    fos.write(addedBytes);
		} catch (IOException ioe) {
		    ioe.printStackTrace();
		}
		
		return addedFile;
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
	
	static String toBinary( byte[] bytes )
	{
	    StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
	    for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
	        sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
	    return sb.toString();
	}

	static byte[] fromBinary( String s )
	{
	    int sLen = s.length();
	    byte[] toReturn = new byte[(sLen + Byte.SIZE - 1) / Byte.SIZE];
	    char c;
	    for( int i = 0; i < sLen; i++ )
	        if( (c = s.charAt(i)) == '1' )
	            toReturn[i / Byte.SIZE] = (byte) (toReturn[i / Byte.SIZE] | (0x80 >>> (i % Byte.SIZE)));
	        else if ( c != '0' )
	            throw new IllegalArgumentException();
	    return toReturn;
	}

}