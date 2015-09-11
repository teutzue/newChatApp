package TestPack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Yoana
 */
public class DummyClient 
{
     Socket socket;
    PrintWriter pw;
    Scanner scanner;
    String ip;
    int port;
    
    public void connect(String ip, int port) throws IOException{
        this.ip = ip;
        this.port = port;
        socket = new Socket(ip, port);
        scanner = new Scanner(socket.getInputStream());
        pw = new PrintWriter(socket.getOutputStream(),true);
    }
    public void send(String msg){
        pw.println(msg);
        //pw.flush();
     }
    
    //This has in some way to go into a thread so we dont block the caller
    public String receive(){
        return scanner.nextLine(); //
    }
    
  
    
    
}
