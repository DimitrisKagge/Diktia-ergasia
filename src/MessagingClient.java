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

        Character g=line.charAt(27);
        if(g.equals('1')) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String rid = "";
            try {
                rid = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(rid);
        }else if(g.equals('2')){
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String rid = "";
            try {
                rid = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int l=1;
            if(rid.equals("Invalid Auth Token")){
                System.out.println(rid);
            }else {
                rid = rid.replace("[", "");
                rid = rid.replace("]", "");
                rid = rid.replace(",", "");
                for (String word : rid.split(" ")) {
                    System.out.printf("%d %s%n", l, word);
                    l++;
                }
            }
        }

        sc.close();
        socket.close();

    }
}