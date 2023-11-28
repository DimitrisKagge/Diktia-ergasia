import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MessagingClient {
    private Socket socket;
    private DataOutputStream out;
    private Scanner in;


    public MessagingClient(){
        try{
            socket=new Socket("localhost", 1000);
            out=new DataOutputStream(socket.getOutputStream());
            in =new Scanner(System.in);
            writeMessages();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void writeMessages() throws IOException{
        String line="";
        while(!line.equals((MessagingServer.Stop_String))){
            line=in.nextLine();
            out.writeUTF(line);
        }
        close();
    }
    private void close() throws IOException{
        socket.close();
        out.close();
        in.close();
    }
    public static void main(String[] args){
        new MessagingClient();
    }
}
