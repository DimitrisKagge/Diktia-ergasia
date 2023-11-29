import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


public class MessagingServer {
    private static BufferedReader in=null;
    private static PrintWriter out=null;
    public static void main(String[] args) throws IOException {
        ArrayList<String> usernames=new ArrayList<>();
        ArrayList<Integer>  authToken=new ArrayList<>();
        ArrayList<Account> users=new ArrayList<>();

        ServerSocket serverSocket = new ServerSocket(1000);
        System.out.println("java server 1000");
        while (true) {
            Socket connectionSocket = serverSocket.accept();
            new Thread(() -> {

                try {
                    in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    String line = "";
                    try {
                        line = in.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println((line));

                    Character g=line.charAt(27);
                    if (g.equals('1')) {
                        String[] sentence = line.split(" ");
                        String username = sentence[sentence.length - 1];
                        char[] chars = username.toCharArray();
                        boolean flag = true;
                        for (char c : chars) {
                            if ((!Character.isLetter(c)) && c != '-') {
                                flag = false;
                            }
                        }
                        if (flag == false) {
                            out = new PrintWriter(connectionSocket.getOutputStream(), true);
                            out.println("Invalid Username");
                            out.flush();
                        } else if (usernames.contains(username)) {
                            out = new PrintWriter(connectionSocket.getOutputStream(), true);
                            out.println("Sorry, the user already exists");
                            out.flush();
                        } else {
                            usernames.add(username);
                            int i = 0;
                            while(authToken.contains(i)){
                                i++;
                            }
                            Account user=new Account(username,i);
                            users.add(user);
                            authToken.add(i);
                            out = new PrintWriter(connectionSocket.getOutputStream(), true);
                            out.println(i);
                            out.flush();
                        }

                    }else if(g.equals('2')){
                        String[] sentence = line.split(" ");
                        String auth = sentence[sentence.length - 1];
                        Integer auth2=Integer.parseInt(auth);
                        boolean flag=false;
                        for(Integer c : authToken){
                            if(c==auth2){
                                flag=true;
                            }
                        }
                        if(flag==true){
                        out=new PrintWriter(connectionSocket.getOutputStream(),true);
                        out.println(usernames);
                        out.flush();
                        }else{
                            out=new PrintWriter(connectionSocket.getOutputStream(),true);
                            out.println("Invalid Auth Token");
                            out.flush();
                        }
                    }


                    connectionSocket.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}