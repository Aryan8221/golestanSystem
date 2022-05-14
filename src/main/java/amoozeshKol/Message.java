package amoozeshKol;

public class Message {
    public String txt;
    public String sender;
    public String receiver;
    public boolean responded;

    public Message(String txt, String sender, String receiver) {
        this.txt = txt;
        this.sender = sender;
        this.receiver = receiver;
        this.responded = false;
    }
}
