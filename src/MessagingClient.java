import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MessagingClient {
    private static Socket socket;


    public static void main(String [] args) throws IOException {

        socket = new Socket("localhost",1000);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Scanner sc = new Scanner(System.in);
        String line = null;
        line = sc.nextLine();
        out.println(line);
        out.flush();


        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String rid = "";
        try {
            rid = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(rid);
        sc.close();
        socket.close();
    }
}