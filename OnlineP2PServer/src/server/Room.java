/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import java.util.HashMap;
import network.CommunicationLink;
import utility.Constants;

/**
 *
 * @author fadia
 */
public class Room {

    private String id;
    private String adminID;
    private String name;
    private HashMap<String, Client> participants;
    private StringBuilder chat;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }
    
    public boolean clientExists(String clientID) {
        return (participants.get(clientID) != null);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    public Room(String id, String adminID, String name) {
        this.id = id;
        this.adminID = adminID;
        this.name = name;
        participants = new HashMap();
        chat = new StringBuilder();
        //participants.put(this.adminID, admin);
    }

    public void addClient(Client client) {
        sendParticipants(client);  
        sendChatToNewParticipant(client);
        if(participants.get(client.getId())==null){
        sendNewParticipantToOtherParticipants(client, Constants.ADDNEWCLIENTTOROOMORDER);
        participants.put(client.getId(), client);
        }
    }

    public void sendChatToNewParticipant(Client client) {
        HashMap<String, String> message = new HashMap<>();
        message.put(Constants.REPLYTYPEATTR, Constants.ROOMHISTORY);
        message.put(Constants.ROOMIDATTR, this.getId());
        message.put(Constants.ROOMCHAT, chat.toString());

        client.getCommunicationLink().send(message);
    }

    public void removeClient(Client client) {
        if (participants.remove(client.getId()) != null) {
            sendNewParticipantToOtherParticipants(client, Constants.REMOVECLIENTFROMROOMORDER);
            /*if (participants.isEmpty()) {
                deleteRoom();
            }
            if (client.getId().equals(adminID)) {
                adminID = participants.keySet().iterator().next();
            }*/
        }
    }

    public void sendParticipants(Client newClient) {
        //need to put before adding in the new client so s/he doesnt get sent to her/im self
        participants.values().forEach((c) -> {
            if(!c.getId().equals(newClient.getId()))
                sendClient(c, newClient.getCommunicationLink(), Constants.ADDNEWCLIENTTOROOMORDER);
        });
    }

    public void sendClient(Client c, CommunicationLink cl, String order) {
        HashMap<String, String> message = new HashMap<>();
        message.put(Constants.REPLYTYPEATTR, order);
        message.put(Constants.ROOMIDATTR, this.getId());
        message.put(Constants.CLIENTIDATTR, c.getId());
        //message.put(GeneralConstants.CLIENTSTATUSATTR, c.getStatus());
        cl.send(message);
    }

    private void sendNewParticipantToOtherParticipants(Client newClient, String order) {

        participants.values().forEach((c) -> {
            sendClient(newClient, c.getCommunicationLink(), order);
        });
    }

    public void sendMessageToParticipants(String senderID, String msg) {
        chat.append(senderID).append(",").append(msg).append('\n');
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REPLYTYPEATTR, Constants.MESSAGEFROMROOM);
        //message.put(MessageConstants.MESSAGEFROM, MessageConstants.FROMROOM);
        message.put(Constants.ROOMIDATTR, this.getId());
        message.put(Constants.CLIENTIDATTR, senderID);
        message.put(Constants.MESSAGE, msg);
        participants.values().forEach((c) -> {
            if (!c.getId().equals(senderID)) {
                c.getCommunicationLink().send(message);
            }
        });
    }

   /* public void deleteRoom() {
        HashMap<String, String> message = new HashMap();
        message.put(Constants.REPLYTYPEATTR, Constants.ROOMDELETED);
        message.put(Constants.ROOMIDATTR, this.getId());
        participants.values().forEach((c) -> {
            c.getCommunicationLink().send(message);

        });
    }*/

    public void sendConfirmationToClient(Client client) {
        HashMap<String, String> confirmation = new HashMap();
        confirmation.put(Constants.REPLYTYPEATTR, Constants.CONFIRMJOINROOMORDER);
        confirmation.put(Constants.ROOMIDATTR, this.id);
        confirmation.put(Constants.ROOMNAMEATTR, this.name);
        confirmation.put(Constants.ADMINIDATTR, this.adminID);
        client.getCommunicationLink().send(confirmation);

    }
}
