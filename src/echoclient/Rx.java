package echoclient;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CosticaTeodor
 */
public class Rx {

    private String command;
    private String sender;
    private String message;
    private List<String> onlineUsers = new ArrayList<>();

    public Rx(Rx r){
    
        this.command = r.getCommand();
        this.sender = r.getSender();
        this.message = r.getMessage();
        this.onlineUsers = r.getOnlineUsers();
    }
    
    public Rx(){
        
    }
    
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
    

    
}
