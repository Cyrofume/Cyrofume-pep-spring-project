package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.SocialMediaApp;
import com.example.entity.*;
//we also need to query all account objects and more perhaps
import com.example.repository.AccountRepository;
import com.example.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account noIdAcount) {
        //in our class if no id is presented our entites account class will auto ID it
        // accountServ.userNameExists(noIdAcount.getUsername());
        System.out.println("Hey register an account");
        System.out.println("What is the db size? " + accountServ.getDBSize());
        if (accountServ.userNameExists(noIdAcount.getUsername())) {
                return ResponseEntity.status(409).body(noIdAcount);
                // return null;
                // System.out.println("Hey this requestbody's username exists in db!");
        } else {
            ///
            accountServ.addAccount(noIdAcount);
            return ResponseEntity.status(HttpStatus.OK).body(noIdAcount);

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
}
