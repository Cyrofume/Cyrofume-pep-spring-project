package com.example.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.SocialMediaApp;
import com.example.entity.*;
//we also need to query all account objects and more perhaps
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//
import java.util.*;

import javax.websocket.server.PathParam;
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
     * @param noIdAcount
     * @return 200 if successfully added into db
     * @return 408 if duplicate username ,accutally 409
     * @return 400 if not sucessful for any other reason
     */
    @PostMapping("/register") 
    public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account noIdAccount) {
        //in our class if no id is presented our entites account class will auto ID it
        // accountServ.userNameExists(noIdAcount.getUsername());
        System.out.println("Hey register an account");
        System.out.println("What is the db size? " + accountServ.getDBSize());
        if (accountServ.userNameExists(noIdAccount.getUsername())) {
                return ResponseEntity.status(409).body(noIdAccount);
                // return null;
                // System.out.println("Hey this requestbody's username exists in db!");
        } else {
            ///
            //we need to make a new account that will generate its own id
            //thiss needs more testing to verify id is consistent[!]
            Account newAccount = new Account(noIdAccount.getUsername(), noIdAccount.getPassword());
            accountServ.addAccount(newAccount);
            return ResponseEntity.status(HttpStatus.OK).body(newAccount);

        }
        //now we need to make sure the account is added!
        // return noIdAcount; //does this work if null or not? lets see
        //but there are cases we need to follow as well.

        //what if the account cannot be created
        //check if the username exists, return 409
        //else return 400;
        // AccountRepository accountQ = new AccountRepository();
        // AccountRepository petRepository = 
        // List<Account> allUsers = AccountRepository
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> verifyAccount(@RequestBody Account account) {

        System.out.println("does account have an id? " + account.getAccountId());

        Account tempAccount = accountServ.verifyAccount(account);

        if (tempAccount != null) {
            System.out.println("does account have an id? " +tempAccount.getAccountId());
            return ResponseEntity.status(HttpStatus.OK).body(tempAccount);
        }
        System.out.println("does account have an id? " + account.getAccountId());
        return ResponseEntity.status(401).body(account);
    }

    /**
     * Here below we deal with message restful api's
     */

    @PostMapping("/messages") 
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message) {
        //in our class if no id is presented our entites account class will auto ID it
        // accountServ.userNameExists(noIdAcount.getUsername());
        System.out.println("Hey register an message");
        System.out.println("What is the db size? " + accountServ.getDBSize());
        if (accountServ.idExists(message.getPostedBy()) != null && message.getMessageText().length() > 0 && message.getMessageText().length() < 255) {
                

                Message newMessage = new Message(message.getPostedBy(), message.getMessageText(), message.getTimePostedEpoch());
                messageServ.addMessage(newMessage); //we can now add it in peice
                // messageServ.addMessage(newMessage)
                // return ResponseEntity.status(HttpStatus.OK).body(message);
                return ResponseEntity.status(HttpStatus.OK).body(newMessage);
                // return null;
                // System.out.println("Hey this requestbody's username exists in db!");
        } else {
            ///
            //we need to make a new account that will generate its own id
            //thiss needs more testing to verify id is consistent[!]
            // Account newAccount = new Account(noIdAccount.getUsername(), noIdAccount.getPassword());
            // accountServ.addAccount(newAccount);
            // return ResponseEntity.status(HttpStatus.OK).body(message);
            return ResponseEntity.status(400).body(message);

        }
        //now we need to make sure the account is added!
        // return noIdAcount; //does this work if null or not? lets see
        //but there are cases we need to follow as well.

        //what if the account cannot be created
        //check if the username exists, return 409
        //else return 400;
        // AccountRepository accountQ = new AccountRepository();
        // AccountRepository petRepository = 
        // List<Account> allUsers = AccountRepository
    }
    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages() {

        List<Message> listOfMessages = messageServ.getAllMessages();
        //empty or not return it
        return ResponseEntity.status(HttpStatus.OK).body(listOfMessages);
    }   
    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessageByID(@PathVariable("messageId") Integer messageId) {
        System.out.println(messageId  + " what is the id for this item?");
        // List<Message> listOfMessages = messageServ.getAllMessages();
        Message aMessage = messageServ.getMessageByID(messageId);
        //empty or not return it
        // return ResponseEntity.status(HttpStatus.OK).body("none");
        return ResponseEntity.status(HttpStatus.OK).body(aMessage);
    }  
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
        // return ResponseEntity.status(HttpStatus.OK).body(rowsUpdated);
    }
}


