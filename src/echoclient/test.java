package echoclient;

import echoserver.ProtocolStrings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CosticaTeodor
 */
public class test implements Observer {

    private int PORT = 9090;
    private String IP = "localhost";
    EchoClient client;
    EchoClient client1;
    Tx transportUser1 = new Tx(ProtocolStrings.USER, "Teo");
    Tx transportUser2 = new Tx(ProtocolStrings.USER, "Bo");
    
    Tx transportStop = new Tx(ProtocolStrings.STOP);
    List<String>map = new ArrayList<>();

    public test() {
        client = new EchoClient(this);
        client1= new EchoClient(this);
        try {
            client.connect(IP, PORT);
            client1.connect(IP, PORT);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread t = new Thread(client);
        Thread t2 = new Thread(client1);
        t.start();
        t2.start();
    }

    private void processTransport() throws InterruptedException {
        client.createMessageString(transportUser1);
        Thread.sleep(2000);
        client1.createMessageString(transportUser2);
        Thread.sleep(2000);
        Tx transportMsg = new Tx(ProtocolStrings.MSG, map, "Hello Bo&Teo");
        client.createMessageString(transportMsg);
        Thread.sleep(2000);
        //client.createMessageString(transportStop);
    }

    public static void main(String[] args) throws InterruptedException {
        test test = new test();
        test.processTransport();
    }

    @Override
    public void update(Observable o, Object arg) {
        map.clear();
        Rx rx = new Rx((Rx) arg);
        for (String map1 : rx.getOnlineUsers()) {
            map.add(map1);
        }
        
        //System.out.println("Updated: " + arg.toString());
        
        System.out.println(rx.getSender());
        System.out.println(rx.getMessage());
        System.out.println(rx.getOnlineUsers().toString());
    }

}
