package com.example.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

// import com.example.SocialMediaApp;
import com.example.entity.*;
//we also need to query all account objects and more perhaps
// import com.example.repository.AccountRepository;
// import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

// import javassist.bytecode.stackmap.BasicBlock.Catch;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//
import java.util.*;

// import javax.websocket.server.PathParam;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

//we need to let springbook about this class, aka a controller class
//restful implementation
@RestController
public class SocialMediaController {

    //before beginning we will need to follow spring book annotations

    //we need to map into the restful verbs

    //we also need our service to now work for ous here
    //we want to inject dependency here
    @Autowired
    AccountService accountServ;
    @Autowired //I didn't think I would need to autowired for each declaration...
    MessageService messageServ;

    /**
     * The registration will be successful if and only if the 
     * username is not blank, 
     * the password is at least 4 characters long, 
     * and an Account with that username does not already exist. 
     * If all these conditions are met, the response body should contain 
     * a JSON of the Account, including its account_id. 
     * The response status should be 200 OK, which is the default. 
     * 
     * 
     * @param noIdAcount - represented as JSON into Account object, used for testing if register possible
     * @return status code 200 if successfully added into db
     * @return status code 408 if duplicate username ,accutally 409
     * @return status code 400 if not sucessful for any other reason
     */
    @PostMapping("/register") 
    public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account noIdAccount) {
        //Note, in our classes if no id is presented our entites account class will auto ID it

        // System.out.println("Hey register an account");
        // System.out.println("What is the db size? " + accountServ.getDBSize());
        if (accountServ.userNameExists(noIdAccount.getUsername())) {

            // System.out.println("Hey this requestbody's username exists in db!");
            return ResponseEntity.status(409).body(noIdAccount);
            // return null;
        } else {
            //we need to make a new account that will generate its own id
            try {
                Account newAccount = accountServ.addAccountWithNoPK(noIdAccount);
                return ResponseEntity.status(HttpStatus.OK).body(newAccount);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(400).body(null);
            }
            //[1!]
        
        }
    }

    /**
     * 
     * As a user, I should be able to verify my login on the endpoint 
     * POST localhost:8080/login. 
     * The request body will contain a JSON representation of an Account, 
     * not containing an account_id. 
     * In the future, this action may generate a Session token to allow the user to 
     * securely use the site. We will not worry about this for now.
     * 
     * @param account - represents an account object that could exist in the account db, test it
     * @return status OK - if account exists along with its properties in the body
     * @return status 401 - if no account exists.
     */
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> verifyAccount(@RequestBody Account account) {

        // System.out.println("does account have an id? " + account.getAccountId());
        Account tempAccount = accountServ.verifyAccount(account);

        if (tempAccount != null) {
            // System.out.println("does account have an id? " +tempAccount.getAccountId());
            return ResponseEntity.status(HttpStatus.OK).body(tempAccount);
        }
        // System.out.println("does account have an id? " + account.getAccountId());
        return ResponseEntity.status(401).body(account);
    }

    /**
     * Here below we deal with message restful api's
     */

    /**
     * As a user, I should be able to submit a new post on the endpoint 
     * POST localhost:8080/messages. 
     * The request body will contain a JSON representation of a message, 
     * which should be persisted to the database, but will not contain a message_id.
     * 
     * @param message - body turned into a message to be added in DB
     * @return
     */
    @PostMapping("/messages") 
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message) {
        
        //in our class if no id is presented our entites account class will auto ID it
        Account user = accountServ.idExists(message.getPostedBy());
        Message result = messageServ.addMessage(user, message);

        if (result != null) {
                return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(400).body(message);

        }

    }
    
    /**
     * As a user, I should be able to submit a GET request on the endpoint 
     * GET localhost:8080/messages.
     * 
     * @return status of all messages - always in OK status even if list of messages is empty
     */
    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages() {

        List<Message> listOfMessages = messageServ.getAllMessages();
        //empty or not return it
        return ResponseEntity.status(HttpStatus.OK).body(listOfMessages);
    }

    /**
     * As a user, I should be able to submit a GET request on the endpoint 
     * GET localhost:8080/messages/{message_id}.
     * 
     * 
     * @param messageId - leads to the message we wish to get
     * @return status of message object - always return OK status, message could be null
     */   
    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessageByID(@PathVariable("messageId") Integer messageId) {
        // System.out.println(messageId  + " what is the id for this item?");

        Message aMessage = messageServ.getMessageByID(messageId);
        //empty or not return it
        return ResponseEntity.status(HttpStatus.OK).body(aMessage);
    }  

    /**
     * As a User, I should be able to submit a DELETE request on the endpoint DELETE 
     * localhost:8080/messages/{messageId}.
     * 
     * @param messageId - contains object to be deleted
     * @return status of message object - deleted or not but always in OK status
     *                                  - Note, if deleted return how many rows updated in body
     */
    @DeleteMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> deleteMessageByID(@PathVariable("messageId") Integer messageId) {
        Message deletedMessage = messageServ.getMessageByID(messageId);
        int rowsUpdated = 0;
        if (deletedMessage != null) {
            rowsUpdated++;
            return ResponseEntity.status(HttpStatus.OK).body(rowsUpdated);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
    
    /**
     * As a user, I should be able to submit a PATCH request on the 
     * endpoint PATCH localhost:8080/messages/{messageId}. 
     * The request body should contain a new message_text values to replace 
     * the message identified by messageId/. 
     * 
     * The request body can not be guaranteed to contain any other information.
     * @param messageId - this id leads to a message object to be updated
     * @param newMessage - this object contains a new text to be used
     * @return status 400 - along with an empty body indicating failure
     * @return status 200 - along with value 1 indicating a row has been updated
     */
    @PatchMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> updateMessageByID(@PathVariable("messageId") Integer messageId, @RequestBody Message newMessage ) {

        Message messageResult = messageServ.updateMessageByID(messageId, newMessage);
        
        if (messageResult == null) {
            return ResponseEntity.status(400).body(null);   
        } else {
            return ResponseEntity.status(200).body(1);
        }
        
        

        
    }

    /**
     * As a user, I should be able to submit a GET request on the endpoint GET 
     * localhost:8080/accounts/{accountId}/messages.
     * The response body should contain a JSON representation 
     * of a list containing all messages posted by
     * 
     * @param userID - a particular user, which is retrieved from the database. 
     * @return status/reponse of a List of Messages - empty or filled but always in OK status
     */
    @GetMapping("accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getMessagesPostedBy(@PathVariable("accountId") Integer userID) {

        List<Message> results = messageServ.getMessagesPostedBy(userID);
        return ResponseEntity.status(200).body(results);

    }
    
}
//[1!] thiss needs more testing to verify id is consistent[!]

