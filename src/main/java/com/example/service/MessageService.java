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


}
