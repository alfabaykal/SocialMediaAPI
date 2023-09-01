package com.alfabaykal.socialmediaapi.repository;

import com.alfabaykal.socialmediaapi.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Friendship f WHERE f.userId = :id OR f.friendId = :id")
    void deleteAllByUserId(Long id);

}
