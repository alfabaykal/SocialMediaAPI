package com.alfabaykal.socialmediaapi.repository;

import com.alfabaykal.socialmediaapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
