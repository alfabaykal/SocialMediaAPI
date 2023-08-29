package com.alfabaykal.socialmediaapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class UserRelationshipDto {
    private Long id;
    private String username;
    private String email;
    private Set<UserRelationshipDto> friends;
    private Set<UserRelationshipDto> sentFriendRequests;
    private Set<UserRelationshipDto> receivedFriendRequests;
    private Set<UserRelationshipDto> subscriptions;
    private Set<UserRelationshipDto> subscribers;
    private Set<ChatRequestDto> sentChatRequests;
    private Set<ChatRequestDto> receivedChatRequests;
    private String password;
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRelationshipDto that = (UserRelationshipDto) o;
        return id.equals(that.id) && username.equals(that.username) && email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }
}
