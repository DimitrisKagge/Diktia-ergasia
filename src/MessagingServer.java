import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MessagingServer {
    private ServerSocket server;

    public static final String Stop_String ="##";

    public MessagingServer(){
        try {
            server=new ServerSocket(1000);
            System.out.println("java server <1000>");
            while(true)iniConnections();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void iniConnections() throws IOException{
        Socket clientSocket=server.accept();
        if(clientSocket.isConnected()) {
            new Thread(()->{
                ConnectedClient client = new ConnectedClient(clientSocket);
                client.readMessages();
                try {
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

    }


    public static void main(String[] args){
        new MessagingServer();
    }
}
