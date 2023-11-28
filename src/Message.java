public class Message {
    private boolean isRead;
    private String sender;
    private String receiver;
    private String body;

    public Message(boolean isRead,String sender,String receiver,String body){
        this.isRead=isRead;
        this.sender=sender;
        this.receiver=receiver;
        this.body=body;
    }


}
