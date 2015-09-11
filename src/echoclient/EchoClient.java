package echoclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import echoserver.ProtocolStrings;
import java.util.ArrayList;
import java.util.List;

public class EchoClient extends Observable implements Runnable {

    public Socket socket;
    private int port;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    private String msg;
    private final Observer observer;
    private boolean flag = true;
    private Rx rx;
    private String [] command;
    //private List<String> users = new ArrayList<>();

    public EchoClient(Observer observer) {
        this.observer = observer;
        
    }
    public void connect(String address, int port) throws UnknownHostException, IOException {
        this.port = port;
        serverAddress = InetAddress.getByName(address);
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
    }

    public void send(String msg) {
        System.out.println("Client sends message to server" + msg);
          output.println(msg);  
    }

    public void createMessageString(Tx t) {
        switch (t.command) {
            case ProtocolStrings.USER:
                user(t);
                break;
            case ProtocolStrings.MSG:
                msg(t);
                break;
            case ProtocolStrings.STOP:
                stop(t);
                break;
            default:
                System.err.println("Something went wrong in ptrotocol createMessageString");
                break;

        } // End of Switch()

    } // Translate()
    
    //public void tearUp(Rx r) throws IOException{
    public Rx tearUp() throws IOException{
        
        command = msg.split("#");
        
        switch(command[0]){
            case ProtocolStrings.USERLIST:
                userList();
                //userList(r);
                break;
            case ProtocolStrings.MSG:
                //displayMSG(r);
                messageReceived();
                break;
            default:
                System.err.println("Smth went wrong in client Tear up");
        }
        return rx;
    }
    
    //private void userList(Rx r){
    private Rx userList(){
        rx  = new Rx();
        ArrayList<String> users_ = new ArrayList<>();
        String [] commaSplit = command[1].split(ProtocolStrings.SEPARATOR);
        for (String user : commaSplit) {
            users_.add(user);//ADD USER HERE!
        }
        rx.setCommand(ProtocolStrings.USERLIST);
        rx.setOnlineUsers(users_);
        return rx;
    }
    
    //private void displayMSG(Rx r){
    private Rx messageReceived(){
        rx.setCommand(ProtocolStrings.MSG);
        rx.setSender(command[1]);
        rx.setMessage(command[2]);
        return rx;
    }
    
    private void user(Tx t) {
        String message = ProtocolStrings.USER + "#" + t.getName();
        send(message);
    }

    private void msg(Tx t) {
        String message = ProtocolStrings.MSG + "#";
        for (String r : t.getReceivers()) {
            message += r + ",";
            //System.out.println(r);
        }
        message = message.substring(0, message.length() - 1) +"#"+ t.getMessage();
        send(message);
    }

//    private void msgAll(Tx t){
//         send(ProtocolStrings.MSG + "*" + t.getMessage());
//    }
//    
    private void stop(Tx t) {
        send(ProtocolStrings.STOP);
    }

    public void stop() throws IOException {
        output.println(ProtocolStrings.STOP);
    }

    public void run() {
        addObserver(observer);
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (flag) {
                    msg = input.nextLine();

                    if (msg.equals(ProtocolStrings.STOP)) {
                        flag = false;
                        try {
                            socket.close();

                        } catch (IOException ex) {
                            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    System.out.println("notifying");
                    setChanged();
                    try {
                        //notifyObservers(msg);
                        notifyObservers(tearUp());
                    } catch (IOException ex) {
                        Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    clearChanged();
                    //System.out.println(msg);
                }
            }

        }).start();
        //return msg;
    }

//    public static void main(String[] args) {
//        int port = 9090;
//        String ip = "10.76.162.21";
//        if (args.length == 2) {//taks two arguments port and ip
//            port = Integer.parseInt(args[0]);
//            ip = args[1];
//        }
//        GUI g = new GUI();
//        EchoClient client = new EchoClient();
//
//        try {
//            client.connect(ip, port);
//            Thread t = new Thread(client);
//            t.start();
//            Tx te = new Tx();
//            client.createMessageString(te);
//        } catch (IOException ex) {
//            Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//         try {
//         EchoClient tester = new EchoClient();      
//          tester.connect(ip, port);
//               System.out.println("Sending 'Hello world'");
//               tester.send("Hello World");
//               System.out.println("Waiting for a reply");
//               System.out.println("Received: " + tester.receive()); //Important Blocking call         
//         tester.stop();      
//         System.in.read();      
//         } catch (UnknownHostException ex) {
//         Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
//         } catch (IOException ex) {
//         Logger.getLogger(EchoClient.class.getName()).log(Level.SEVERE, null, ex);
//         }
//    }
}
