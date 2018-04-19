/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.CallbackOnReceiveHandler;

/**
 *
 * @author fadia
 */
public class CommunicationLink extends Thread {

    private CallbackOnReceiveHandler callBackHandler;
    private Socket s;
    private String id;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    /**
     * @return the id
     */
    private CommunicationLink(CallbackOnReceiveHandler callBackHandler, String id, Socket s) {
        this.s = s;
        this.callBackHandler = callBackHandler;
        this.id = id;
        try {
            this.ois = new ObjectInputStream(s.getInputStream());
            this.oos = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(CommunicationLink.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //CommunicationLink factory to run code after object construction
    public static CommunicationLink generateCommunicationLink(CallbackOnReceiveHandler callBackHandler, String id, Socket s) {
        CommunicationLink cl = new CommunicationLink(callBackHandler, id, s);
        cl.start();
        return cl;
    }

    @Override
    public void run() {
        while (true) {
            ArrayList<String> received;
            try {
                received = (ArrayList<String>) ois.readObject();
                received.add(0, this.id);
                callBackHandler.handleReceivedData(received);
            } catch (IOException ex) {
                Logger.getLogger(CommunicationLink.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CommunicationLink.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void send(ArrayList<String> msg) {
        try {
            oos.writeObject(msg);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(CommunicationLink.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCommunicationLinkId() {
        return id;
    }
}
