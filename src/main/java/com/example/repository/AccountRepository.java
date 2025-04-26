package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
//all classes import such as account and util classes
import com.example.entity.Account;
// import java.util.*;

/**
 * We want to follow JPQL(?), also de we want to extend accountrepository?
 * Our table name: 
 * our cloumns: 
 * 
 * create table account (
    accountId int primary key auto_increment,
    username varchar(255) not null unique,
    password varchar(255)
    );
 */
public interface AccountRepository extends JpaRepository<Account, Long> {


    // @Query("FROM account WHERE username = :nameVar")
    // List<Account> example1(@Param("nameVar") String name);
}
