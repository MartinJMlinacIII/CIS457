import java.io.*;
import java.net.*;

public class tcpclient {

  public final static int SOCKET_PORT = 13267;      // you may change this
  public final static String SERVER = "127.0.0.1";  // localhost
  public final static String
       FILE_TO_RECEIVED = "test.jpg"; 
  public final static int FILE_SIZE = 602000000;

  public static void main (String [] args ) throws IOException {
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos  = null;
    Socket sock = null;
    

    BufferedReader inFromUser = 
	new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Enter an IP Address: ");
    String  serverAddress = inFromUser.readLine();

    System.out.println("Enter a Port: ");
    int port = Integer.parseInt(inFromUser.readLine());

    try {
      sock = new Socket(serverAddress, port);
      System.out.println("Connecting...");

      System.out.println("Connected! Please enter a file name: ");
      String fileName = inFromUser.readLine();
      DataOutputStream outToServer = 
	  new DataOutputStream(sock.getOutputStream());
      outToServer.writeBytes(fileName+'\n');
      
      // receive file
      byte [] mybytearray  = new byte [FILE_SIZE];
      InputStream is = sock.getInputStream();
      if (is.read() != -1){
	   fos = new FileOutputStream("new"+fileName);
           bos = new BufferedOutputStream(fos);
	   bytesRead = is.read(mybytearray,0,mybytearray.length);
	   current = bytesRead;

      do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      //leFromServer = new BufferedOutputStream(
      //			      new FileOutputStream("new"+fileName));

      bos.write(mybytearray, 0 , current);
      bos.flush();
      System.out.println("File " + fileName
          + " downloaded (" + current + " bytes read)");

      }
      else{
	  System.out.println("File Not Found");
	  return;
      }
    }

    catch(Exception e){
	//BufferedReader inFromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	//String serverText = inFromServer.readLine();
	//System.out.println(serverText);
	System.out.println("File Not Found");
	
    }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (sock != null) sock.close();
    }
  }

}
