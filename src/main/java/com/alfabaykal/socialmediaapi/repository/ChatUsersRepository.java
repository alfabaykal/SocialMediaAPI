package com.alfabaykal.socialmediaapi.repository;

import com.alfabaykal.socialmediaapi.model.ChatUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ChatUsersRepository extends JpaRepository<ChatUsers, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ChatUsers c WHERE c.userId = :id")
    void deleteAllByUserId(Long id);

}
