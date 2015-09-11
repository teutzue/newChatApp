package TestPack;


import TestPack.DummyClient;
import echoclient.EchoClient;
import echoserver.EchoServer;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yoana
 */
public class TestClientCool implements Observer
{
   private int count = 0;
   String testVariable = "";
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

    public int getCount()
    {
        return count;
    }
            
    @Test
    public void testMultipleUsers() throws IOException, InterruptedException
    {
//        EchoClient eco = new EchoClient(this);
//        eco.connect("localhost", 9090);
//        Thread t1 = new Thread(eco);
//        t1.start();
//        eco.send("USER#Teo");
////        
//        EchoClient eco2 = new EchoClient(this);
//        eco2.connect("localhost", 9090);
//        Thread t2 = new Thread(eco);
//        t2.start();
//        eco2.send("USER#Tom");
////        
//        
//        
//        
//        EchoClient eco3 = new EchoClient(this);
//        eco3.connect("localhost", 9090);
//        Thread t3 = new Thread(eco);
//        t3.start();
//        eco3.send("USER#Vanessa");
//        
//        
//        Thread.sleep(2000);
//        eco3.send("MSG#Teo,Tom#hi");
//        t3.join();
//        t1.join();
//        //t2.join();
//      //  Thread.sleep(10000);
//        Assert.assertEquals(2,getCount());
        
    }
    
    
    
    
    @AfterClass
    public static void tearDownClass() {
        EchoServer.stopServer();
    }
    
       String result = "";
       // String resultTom = "";
    @Test
    public void testConnect() throws IOException, InterruptedException
    {
        EchoClient eco2 = new EchoClient(this);
        eco2.connect("localhost", 9090);
        Thread t2 = new Thread(eco2);
        t2.start();
        eco2.send("USER#Tom");
      //  Assert.assertEquals("USERLIST#Tom",result);
        
        
        
        EchoClient eco = new EchoClient(this);
        eco.connect("localhost", 9090);
        Thread t1 = new Thread(eco);
        t1.start();
        eco.send("USER#Teo");
        
        Thread.sleep(2000);
        Assert.assertEquals("USERLIST#Tom,Teo",result);
        eco.send("MSG#Tom#Hi bitch");
        Thread.sleep(5000);
        Assert.assertEquals("MSG#Teo#Hi bitch",result);
        eco.send("MSG#*#Hi guys");
        Thread.sleep(5000);
        Assert.assertEquals("MSG#Teo#Hi guys",result);
        
        
        
        
        eco.send("STOP#");
        Thread.sleep(2000);
        Assert.assertEquals("USERLIST#Tom",result);
        
    }

    @Override
    public synchronized void update(Observable o, Object o1) //no junit
    {
       // System.out.println("I am in update in the beggining ");
        result = (String)o1;
       // if((result.equals("MSG#Vanessa#hi")))
       // {
         //   count = count+1;
         //   System.out.println("I am in update and just updated count ");
            //increment();
       // }
       // System.out.println("we are in update method "+result);
       // System.out.println("the count is "+count);
    }
//    
//    public synchronized void increment()
//    {
//      count = count + 1;   
//    }
   
    
    
//    public void Test4e()
//    {
//        String bla = "";
//        EchoClient ec = new EchoClient(new Observer()
//        {
//
//            @Override
//            public void update(Observable o, Object o1)
//            {
//                
//            }
//        });
//        Thread t3 = new Thread(ec);
//        t3.start();
//        ec.send("USER#Teo");
//        
//         EchoClient et = new EchoClient(new Observer()
//        {
//              
//            @Override
//            public void update(Observable o, Object o1)
//            {
//                
//            }
//        });
//          et.send("USER#Tom");
//         EchoClient ep = new EchoClient(new Observer()
//        {
//
//            @Override
//            public void update(Observable o, Object o1)
//            {
//               // = (String)o1;
//            }
//        });
//          ep.send("USER#Ella");
//          ep.send("MSG#Tom,Teo#hi");
//          
//    }
    
    @Test
    public void testServerProtocol() throws InterruptedException
    {
//        String see = "";
//        DummyClient dum = new DummyClient();
//       try 
//       {
//           dum.connect("localhost", 9090);
//           
//       } catch (IOException ex) 
//       {
//           Logger.getLogger(TestClientnew.class.getName()).log(Level.SEVERE, null, ex);
//       }
//       dum.send("USER#Teo");
//       
//         DummyClient dum2 = new DummyClient();
//       try 
//       {
//           dum2.connect("localhost", 9090);
//           
//       } catch (IOException ex) 
//       {
//           Logger.getLogger(TestClientnew.class.getName()).log(Level.SEVERE, null, ex);
//       }
//       dum2.send("USER#Tom");
//       
//       
//       
//       
//         Assert.assertEquals("USERLIST#Tom,Teo",dum2.receive());
//         Thread.sleep(2000);
//        DummyClient dum3 = new DummyClient();
//       try 
//       {
//           dum3.connect("localhost", 9090);
//           
//       } catch (IOException ex) 
//       {
//           Logger.getLogger(TestClientnew.class.getName()).log(Level.SEVERE, null, ex);
//       }
//       dum3.send("USER#Avijaja");
//       dum.send(("STOP#"));
//       Thread.sleep(3000);
//       Assert.assertEquals("USERLIST#Tom,Avijaja",dum3.receive());
////       Thread.sleep(5000);
////       Assert.assertEquals("MSG#USERLIST#Tom,Teo,Avijaja",dum.receive());
////       
//    // dum3.send("MSG#Tom,Teo#hi people ");
////       Thread.sleep(5000);
////               
////              
////       Assert.assertEquals("MSG#Avijaja#hi people",dum2.receive());
//       Thread.sleep(2000);
   //   Assert.assertEquals("MSG#Avijaja#hi people",dum2.receive());
        
    }
    
}

