package model;

import java.util.List;
import java.util.Stack;

public class Message {

    Stack<String> messages;
    boolean isOk;

    public Message (){
        messages = new Stack<>();
    }

    public Stack<String> getMessages() {
        return messages;
    }

    public boolean isOk() {
        return isOk;
    }

    public void addMessage(String msg){
        messages.push(msg);
    }

    public void setMessages(Stack<String> messages) {
        this.messages = messages;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }
}
