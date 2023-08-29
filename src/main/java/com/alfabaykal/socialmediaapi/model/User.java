package com.alfabaykal.socialmediaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username", unique = true)
    @NotEmpty(message = "Username shouldn't be empty")
    @Size(min = 2, max = 100, message = "Username should be from 2 to 100 characters long ")
    private String username;

    @Column(name = "email", unique = true)
    @Email
    @NotEmpty(message = "Email shouldn't be empty")
    private String email;

    @Column(name = "password")
    @Size(min = 6, message = "Password must be 6 characters or more")
    private String password;

    @Column(name = "role")
    private String role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friendship",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friend_requests",
            joinColumns = @JoinColumn(name = "sender_id"),
            inverseJoinColumns = @JoinColumn(name = "receiver_id")
    )
    private Set<User> sentFriendRequests;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "friend_requests",
            joinColumns = @JoinColumn(name = "receiver_id"),
            inverseJoinColumns = @JoinColumn(name = "sender_id")
    )
    private Set<User> receivedFriendRequests;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    private Set<User> subscriptions;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "publisher_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id")
    )
    private Set<User> subscribers;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<ChatRequest> sentChatRequests;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<ChatRequest> receivedChatRequests;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && username.equals(user.username) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }
}
