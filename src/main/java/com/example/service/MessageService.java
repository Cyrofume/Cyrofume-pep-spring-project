package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
//more lib for java
import java.util.*;

import com.example.entity.Account;
import com.example.entity.Message;


//every component needs an annotation or else it won't work!
/**
 * I will need to store a repository to call for business and logic
 * Ensure all information is tested for Message objects here
 */
@Service
public class MessageService {

    //we need to wire the message service with its messge repos
    @Autowired
    MessageRepository messageRepos;

     /**
     * 
     * this is key into our repoistory -> socialmediacontroller
     * where the service tests and ensures logic is correct
     */

    @Autowired
    MessageService(MessageRepository messageRepos) {
        this.messageRepos = messageRepos;
    }

    /**
     * Include all functions and helpers to be used in the social media controller
     */

    /**
     * Given a user determine they exist, if so add message into the DB
     * 
     * 
     * The creation of the message will be successful if and only 
     * Condition:
     * if the message_text is not blank, \
     * is under 255 characters, \
     * and posted_by refers to a real, existing user.\
     * 
     * @param user - Account object can be null or not, used for testing
     * @param message - Message object to be used for testing and saved if all conditions met
     * @return
     */
    public Message addMessage(Account user, Message message) {

        // System.out.println("Hey register an message");
        // System.out.println("What is the db size? " + accountServ.getDBSize());

        if (user != null && message.getMessageText().length() > 0 && message.getMessageText().length() < 255) {
                return messageRepos.save(message);
        } else {
            return null;

        }
    }

    /** 
     * Gaather all messages from the message db
     */
    public List<Message> getAllMessages() {
        return messageRepos.findAll();
    }

    /**
     * Get a message by its message id
     * 
     * @param id - our key to finding a message object in the db
     * @return a message object or null indicating does not exist
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
    }

    /**
     * Remove message given an message id, if successful return old, removed message 
     * else fail and return null
     * 
     * @param id - leads to a message to be deleted
     * @return oldMessage - deleted object from DB but passed for testing
     * @return null - deletion not possible since id not in DB
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
     * The update of a message should be successful if and only 
     * Condition: 
     * if the message id already exists and \
     * the new message_text is not blank and is not over 255 characters.\
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
     * Passed down an id, used to retrieve a list of messages
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
