package TestPack;


import TestPack.DummyClient;
import echoclient.EchoClient;
import echoserver.EchoServer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Lars Mortensen
 */
public class TestClient {

    public TestClient() {
    }

    @BeforeClass
    public static void setUpClass() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EchoServer.main(null);
            }
        }).start();
        Thread.sleep(1000);
    }

    @AfterClass
    public static void tearDownClass() {
        EchoServer.stopServer();
    }

    @Before
    public void setUp() {
    }

    @Test
    public void send() throws IOException, InterruptedException 
    {
 DummyClient dum = new DummyClient();
      
           dum.connect("localhost", 9090);
           
      
       dum.send("USER#Teo");
       
         DummyClient dum2 = new DummyClient();
     
           dum2.connect("localhost", 9090);
           
      
       dum2.send("USER#Tom");
       
       
       
       
         Assert.assertEquals("USERLIST#Tom,Teo",dum2.receive());
         Thread.sleep(2000);
        DummyClient dum3 = new DummyClient();
      
           dum3.connect("localhost", 9090);
           
     
       dum3.send("USER#Avijaja");
       dum.send(("STOP#"));
       Thread.sleep(3000);
       Assert.assertEquals("USERLIST#Tom,Avijaja",dum3.receive());
//       Thread.sleep(5000);
//       Assert.assertEquals("MSG#USERLIST#Tom,Teo,Avijaja",dum.receive());
//       
    // dum3.send("MSG#Tom,Teo#hi people ");
//       Thread.sleep(5000);
//               
//              
//       Assert.assertEquals("MSG#Avijaja#hi people",dum2.receive());
       Thread.sleep(2000);
    }

}
