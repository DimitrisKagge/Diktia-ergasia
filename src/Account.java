import java.util.ArrayList;
import java.util.List;

public class Account {
    private String username;
    private int authToken;
    private List<Message> messageBox;

    public Account(String username,int authToken){
        this.username=username;
        this.authToken=authToken;
        messageBox=new ArrayList<>();
    }

    public void addMessage(Message m){
        messageBox.add(m);
    }

    public String getUsername(){
        return username;
    }


}
