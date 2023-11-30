import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MessagingServer {
    private static BufferedReader in=null;
    private static PrintWriter out=null;
    public static void main(String[] args) throws IOException {
        ArrayList<String> usernames=new ArrayList<>();
        ArrayList<Integer>  authToken=new ArrayList<>();
        ArrayList<Account> users=new ArrayList<>();
        HashMap<Integer,String> connect=new HashMap<Integer,String>();
        HashMap<String,Account> accs=new HashMap<String,Account>();

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
                            connect.put(i,username);
                            accs.put(username,user);
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
                    }else if (g.equals('3')){
                        String[] sentence = line.split(" ");
                        String auth = sentence[sentence.length - 3];
                        Integer auth2=Integer.parseInt(auth);
                        boolean flag=false;
                        for(Integer c : authToken){
                            if(c==auth2){
                                flag=true;
                            }
                        }
                        if(flag==false){
                            out=new PrintWriter(connectionSocket.getOutputStream(),true);
                            out.println("Invalid Auth Token");
                            out.flush();
                        }else{
                            String mg=sentence[sentence.length-1];
                            String ur=sentence[sentence.length-2];
                            if(!usernames.contains(ur)){
                                out=new PrintWriter(connectionSocket.getOutputStream(),true);
                                out.println("User does not exist");
                                out.flush();
                            }else{

                                Message m=new Message(false,connect.get(auth2),ur,mg);

                                accs.get(ur).addMessage(m);
                                out=new PrintWriter(connectionSocket.getOutputStream(),true);
                                out.println("OK");
                                out.flush();
                            }

                        }
                    }else if(g.equals('4')) {
                        String[] sentence = line.split(" ");
                        String auth = sentence[sentence.length - 1];
                        Integer auth2 = Integer.parseInt(auth);
                        boolean flag = false;
                        for (Integer c : authToken) {
                            if (c == auth2) {
                                flag = true;
                            }
                        }
                        if (flag == false) {
                            out = new PrintWriter(connectionSocket.getOutputStream(), true);
                            out.println("Invalid Auth Token");
                            out.flush();
                        }else{
                            ArrayList<String> name=new ArrayList<>();
                            for(Message a : accs.get(connect.get(auth2)).getMessageBox()){
                                name.add(a.getSender()+"*");
                            }
                            out=new PrintWriter(connectionSocket.getOutputStream(),true);
                            out.println(name);
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