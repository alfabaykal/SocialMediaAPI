package com.alfabaykal.socialmediaapi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtPayload {
    Long id;
    String username;
}
