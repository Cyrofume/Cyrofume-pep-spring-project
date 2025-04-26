package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
//more lib for java
import java.util.*;

import com.example.entity.Account;
import com.example.entity.Message;


//every component needs an annotation or else it won't work!
@Service
public class MessageService {

    //we need to wire the message service with its messge repos
    @Autowired
    MessageRepository messageRepos;

     /**
     * 
     * this is key into our repoistory -> soccialmedia controller
     * 
     */

    @Autowired
    MessageService(MessageRepository messageRepos) {
        this.messageRepos = messageRepos;
    }

    /**
     * Include all functions and helpers to be used in the social media controller
     */

     public Message addMessage(Message message) {
        return messageRepos.save(message);
    }

    /** 
     * Gaather all messages from the message db
     */
    public List<Message> getAllMessages() {
        return messageRepos.findAll();
    }

    /**
     * Get a message by its message id
     */
    public Message getMessageByID(long id) {
        List<Message> allMessages = messageRepos.findAll();
        // //Come back for the FindAllID
        for (Message currMessage : allMessages) {
            if (currMessage.getMessageId() == id) {
                return currMessage;
            }
        }
        return null;

        // Message testMessage = null;
        // // return (Message) messageRepos.getById((long) id);
        // System.out.println(id + " inside message service get ID");
        // Optional<Message> notTest = messageRepos.findById(id);
        // if (notTest.isPresent()) {
        //     // return testMessage.get();
        //     System.out.println("This message exisits in db");
        //     testMessage = notTest.get();
        // }
        // // // return testMessage.isPresent();
        // // // messageRepos.get
        // // // return messageRepos.getById((long)id);
        // // return null;
        // // return messageRepos.findById(id).get();
        // return testMessage;
        // return messageRepos.findById(id).orElse(null);
    }

    /**
     * Delete/remove the message
     */
    public Message removeMessageID(long id) {
        List<Message> allMessages = messageRepos.findAll();
        // //Come back for the FindAllID
        Message answer = null;
        for (Message currMessage : allMessages) {
            if (currMessage.getMessageId() == id) {
                // return currMessage;
                answer = currMessage;
                messageRepos.delete(currMessage); //here is the delete method
                return answer;
            }
        }
        return null;
    }

    public Message updateMessageByID(Message oldMessage, Message newMessage) {
        // return null;
        //we can call the remove and add functions to update a new message
        // long presistentID = id;

        // removeMessageID(id);
        // Account newAccount = new
        oldMessage.setMessageText(newMessage.getMessageText());
        return oldMessage;
        // return new Message(id, newMessage.getPostedBy(), newMessage.getMessageText(), newMessage.getTimePostedEpoch());
    }

    public List<Message> getMessagesPostedBy(long id) {

        List<Message> listOfMessages = messageRepos.findAll();

        List<Message> results = new ArrayList<>();

        for (Message currMessage : listOfMessages) {
            if (currMessage.getPostedBy() == id) {
                results.add(currMessage);
            }
        }
        return results;
    }
}
