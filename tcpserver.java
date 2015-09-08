import java.io.*;
import java.net.*;

class tcpserver{
    //public final static int SOCKET_PORT = 13267;  // you may change this
    //public final static String fileName = "thecreator.jpg";  // you may change this

  public static void main (String [] args ) throws IOException {
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Enter a Port: ");
    int socketPort = Integer.parseInt(inFromUser.readLine());


    
    try {
      servsock = new ServerSocket(socketPort);
      
      while (true) {
        System.out.println("Waiting...");
        try {
          sock = servsock.accept();
          System.out.println("Accepted connection : " + sock);
          // send file

	   BufferedReader inFromClient =
	       new BufferedReader(
			     new InputStreamReader(sock.getInputStream()));
          String fileName = inFromClient.readLine(); 
	  System.out.println(fileName);
          File myFile = new File (fileName);
          byte [] byteArray  = new byte [(int)myFile.length()];
          fis = new FileInputStream(myFile);
          bis = new BufferedInputStream(fis);
          bis.read(byteArray,0,byteArray.length);
          os = sock.getOutputStream();
          System.out.println("Sending " + fileName + "(" + byteArray.length + " bytes)");
          os.write(byteArray,0,byteArray.length);
          os.flush();
          System.out.println("Done.");
        }
        finally {
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock!=null) sock.close();
        }
      }
    }
    finally {
      if (servsock != null) servsock.close();
    }
  }
}
