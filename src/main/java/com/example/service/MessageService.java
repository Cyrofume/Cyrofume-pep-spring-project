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
     * 
     * @param id - leads to a message to be deleted
     * @return 
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

    /**
     * Passed down the old and new messages
     * 
     * @param id - the current message object we want to find and change its text
     * @param newMessage - the new message contains a new blog, but test its conditions
     * @return oldMessge - if conditions of its text are met
     * @return null - if conditions of a text is not met we do not update the message object
     */
    public Message updateMessageByID(long id, Message newMessage) {

        // this.getMessageByID(0)
        int lengthOfNewText = newMessage.getMessageText().length();
        Message oldMessage = this.getMessageByID(id);

        if (oldMessage != null && lengthOfNewText > 0 && lengthOfNewText < 255) {
            oldMessage.setMessageText(newMessage.getMessageText());
            return oldMessage;
        } else { 
            return null;   
        }

    }

    /**
     * Passed down an id used to retireve a list of messages
     * It is expected for the list to simply be empty if there are no messages. 
     * 
     * @param id - represents a user id and used with getPostedBy from message methods
     * @return List of Messages - from a user's id
     * @return null - No match was found
     */
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
