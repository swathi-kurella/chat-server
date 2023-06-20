package nio.demo.socket.programming.chat.server.model;

import java.io.Serializable;

public class Conversation implements Serializable {
    private String from;
    private String to;
    private String msg;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Conversation(){}

    public Conversation(String from, String to, String msg) {
        this.from = from;
        this.to = to;
        this.msg = msg;
    }

    //Server Registration
    public Conversation(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
