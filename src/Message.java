public class Message {
    private boolean isRead;
    private String sender;
    private String receiver;
    private String body;
    private int id;

    public Message(boolean isRead,String sender,String receiver,String body,int id){
        this.isRead=isRead;
        this.sender=sender;
        this.receiver=receiver;
        this.body=body;
        this.id=id;
    }

    public String getBody() {
        return body;
    }
    public String getSender(){
        return sender;
    }

    public int getId() {
        return id;
    }
}
