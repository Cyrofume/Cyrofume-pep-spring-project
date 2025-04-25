package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
//more lib for java
import java.util.*;
import com.example.entity.Account;

//special annotation for serivce
/**
 * I will need to store a repository to call in and query
 */
@Service
public class AccountService {
    AccountRepository accountRepos;

    /**
     * 
     * this is key into our repoistory -> soccialmedia controller
     * 
     */

    //start with register and search
    //I think we need autowired too
    @Autowired
    AccountService(AccountRepository accountRepos) {
        this.accountRepos = accountRepos;
    }

    //start with find by username

    public boolean userNameExists(String username) {
        // return accountRepos.find;
        List<Account> everyAccount = accountRepos.findAll();
        for (Account currAccount : everyAccount) {
            if (currAccount.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    public int getDBSize() {
        // return accountRepos.find;
        List<Account> everyAccount = accountRepos.findAll();
        // for (Account currAccount : everyAccount) {
        //     if (currAccount.getUsername() == username) {
        //         return true;
        //     }
        // }
        return everyAccount.size();
        
    }

    public Account addAccount(Account account) {
        return accountRepos.save(account);
    }
    


}
