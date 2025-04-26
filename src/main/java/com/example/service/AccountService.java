package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
//more lib for java
import java.util.*;
import com.example.entity.Account;

//special annotation for serivce
/**
 * I will need to store a repository to call for business and logic
 * Ensure all information is tested for account objects here
 */
@Service
public class AccountService {
    AccountRepository accountRepos;

    /**
     * 
     * this is key into our repoistory -> socialmemdiacontroller
     * where the service tests and ensures logic is correct
     */

    //start with register and search
    //I think we need autowired too
    @Autowired
    AccountService(AccountRepository accountRepos) {
        this.accountRepos = accountRepos;
    }

    /**
     * Determine if username lives in the account db, used for testing purposes
     * @param username - the user of an account object
     * @return true - if username lives in the account DB
     * @return false - if the username is not in DB
     */
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

    /**
     * find if the account exists, used for testing purposes
     * @param id - an account id that can exist or not in the DB
     * @return account object - if exists
     * @return null - if the account does not live in the DB
     */
    public Account idExists( int id) {

        List<Account> everyAccount = accountRepos.findAll();
        for (Account currAccount : everyAccount) {
            if (currAccount.getAccountId() == id) {
                return currAccount;
            }

        }
        return null;
    }
    /**
     * Used for testing purposes
     * Determine if a DB change is being made
     * status of DB for account
     */
    public int getDBSize() {

        List<Account> everyAccount = accountRepos.findAll();
        return everyAccount.size();
    }

    /**
     * This method assumes no id is given
     * ID will autom allocate when creating a new Account object
     * 
     * @param account - object to be added in the DB of accounts
     * @return the account that has been added
     */
    public Account addAccountWithNoPK(Account account) throws IllegalArgumentException {
        Account newAccount = new Account(account.getUsername(), account.getPassword());
        //here if an exception or error was made when creaing the account it should be made here
        //and tested again on controller
        return accountRepos.save(newAccount);

    }

    /**
     * The login will be successful if and only if the username and password provided in the 
     * request body JSON match a real account existing on the database
     * 
     * @param account - ensure the username and password match whats within the db
     * @return Account object - if matched with correct username and password
     * @return null - if no match is found or information is wrong
     */
    public Account verifyAccount(Account account) {
        List<Account> everyAccount = accountRepos.findAll();
        for (Account currAccount : everyAccount) {
            if (currAccount.getUsername().equals(account.getUsername()) && currAccount.getPassword().equals(account.getPassword())) {
                return currAccount;
            }
        }
        return null; //nothing found
    }
}
