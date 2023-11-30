import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


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
        final AtomicInteger count = new AtomicInteger();
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
                                int idg=count.intValue();
                                Message m=new Message(false,connect.get(auth2),ur,mg,idg);
                                count.getAndIncrement();
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

                            HashMap<Integer,String> name=new HashMap<Integer,String>();
                            for(Message a : accs.get(connect.get(auth2)).getMessageBox()){
                                if(a.getIsRead()==false) {
                                    name.put(a.getId(), a.getSender() + "*");
                                }else{
                                    name.put(a.getId(),a.getSender());
                                }
                            }
                            out=new PrintWriter(connectionSocket.getOutputStream(),true);
                            out.println(name);
                            out.flush();
                        }
                    }else if (g.equals('5')){
                        String[] sentence = line.split(" ");
                        String auth = sentence[sentence.length - 2];
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
                            int msgid=Integer.parseInt(sentence[sentence.length-1]);
                            boolean fl=false;
                            String usn = null;
                            String mga=null;
                            for(Message r:accs.get(connect.get(auth2)).getMessageBox()){
                                if(msgid==r.getId()){
                                    fl=true;
                                    usn=r.getSender();
                                    mga=r.getBody();
                                    r.setRead(true);
                                }
                            }
                            if(fl==false){
                                out=new PrintWriter(connectionSocket.getOutputStream(),true);
                                out.println("Message ID does not exist");
                                out.flush();
                            }else {
                            String h="("+usn+")"+mga;
                            out=new PrintWriter(connectionSocket.getOutputStream(),true);
                            out.println(h);
                            out.flush();
                            }
                        }
                    }else if(g.equals('6')){
                        String[] sentence = line.split(" ");
                        String auth = sentence[sentence.length - 2];
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
                            int msgid=Integer.parseInt(sentence[sentence.length-1]);
                            boolean fl=false;
                            Message minim=null;
                            for(Message r:accs.get(connect.get(auth2)).getMessageBox()){
                                if(msgid==r.getId()){
                                    fl=true;
                                    minim=r;
                                }
                            }
                            if(fl==false){
                                out=new PrintWriter(connectionSocket.getOutputStream(),true);
                                out.println("Message does not exist");
                                out.flush();
                            }else{
                                accs.get(connect.get(auth2)).getMessageBox().remove(minim);
                                out=new PrintWriter(connectionSocket.getOutputStream(),true);
                                out.println("OK");
                                out.flush();
                            }
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