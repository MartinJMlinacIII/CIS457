import java.io.*;
import java.net.*;

public class tcpclient {

  public final static int SOCKET_PORT = 13267;      // you may change this
  public final static String SERVER = "127.0.0.1";  // localhost
  public final static String FILE_TO_RECEIVED = "test.jpg"; 
  public final static int FILE_SIZE = 602000000;

  public static void main (String [] args ) throws IOException {
    int bytesRead;
    int current = 0;
    Socket sock = null;
    
    BufferedReader inFromUser = 
      new BufferedReader(
          new InputStreamReader(System.in));
          
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
      byte [] myByteArray  = new byte [FILE_SIZE];
      InputStream is = sock.getInputStream();
      
      BufferedOutputStream bos = 
        new BufferedOutputStream(
          new FileOutputStream("new"+fileName));
          
      bytesRead = is.read(myByteArray, 0, myByteArray.length);
      current = bytesRead;

      do {
         bytesRead =
            is.read(myByteArray, current, (myByteArray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      bos.write(myByteArray, 0 , current);
      bos.flush();
      System.out.println("File " + fileName
          + " downloaded (" + current + " bytes read)");
    }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (sock != null) sock.close();
    }
  }

}
