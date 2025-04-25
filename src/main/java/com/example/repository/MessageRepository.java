package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//all classes import such as account and util classes
import com.example.entity.Message;
import java.util.*;


public interface MessageRepository extends JpaRepository<Message, Long> {
}
