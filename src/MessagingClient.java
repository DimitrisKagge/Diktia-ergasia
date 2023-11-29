import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MessagingClient {
    private static Socket socket;
    private static DataInputStream dataIn;
    private static DataOutputStream dataOut;

    public static void main(String [] args) throws IOException {

        socket = new Socket("localhost",1000);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Scanner sc = new Scanner(System.in);
        String line = null;
        line = sc.nextLine();
        out.println(line);
        out.flush();
        sc.close();
        socket.close();
    }
}