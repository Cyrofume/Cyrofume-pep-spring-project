package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
//all classes import such as account and util classes
import com.example.entity.Message;
// import java.util.*;

/**
 * Just like the AccountRepository
 * We want to follow JPQL(?), also de we want to extend messagerepository?
 * the JpaRepository allows us to easily call the db as long as signature is 
 * corretly attached
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
}
