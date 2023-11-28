import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectedClient {
    private Socket clientSocket;
    private DataInputStream in;

    public ConnectedClient(Socket clientSocket){
        this.clientSocket=clientSocket;
        try {
            this.in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void readMessages(){
        String line ="";
        while(!line.equals(MessagingServer.Stop_String)){
            try{
                line=in.readUTF();
            }catch (IOException e){
                e.printStackTrace();
            }
            System.out.println((line));
        }
    }
    public void close() throws IOException{
        clientSocket.close();

    }
    public static void main(String[] args){
        new MessagingServer();
    }
}
