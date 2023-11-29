import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;


public class MessagingServer {
    private static BufferedReader in=null;
    private static PrintWriter out=null;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1000);
        System.out.println("java server 1000");

        while (true) {
            Socket connectionSocket = serverSocket.accept();
            new Thread(() -> {

                try {
                    out=new PrintWriter(connectionSocket.getOutputStream(),true);
                    in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    String line="";
                    try{
                         line=in.readLine();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        System.out.println((line));

                    connectionSocket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}