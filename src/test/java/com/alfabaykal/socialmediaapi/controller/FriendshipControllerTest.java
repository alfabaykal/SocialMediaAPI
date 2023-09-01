package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.security.JwtUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
public class FriendshipControllerTest {

    @Autowired
    private JwtUtil jwtUtil;

    private String token1;
    private String token2;

    @BeforeEach
    public void setUp() {

        UserRegistrationDto user1 = new UserRegistrationDto();
        String username1 = "user1";
        user1.setUsername(username1);
        String email1 = "email1@example.com";
        user1.setEmail(email1);
        String password1 = "password1";
        user1.setPassword(password1);

        UserRegistrationDto user2 = new UserRegistrationDto();
        String username2 = "user2";
        user2.setUsername(username2);
        String email2 = "email2@example.com";
        user2.setEmail(email2);
        String password2 = "password2";
        user2.setPassword(password2);

        token1 = given()
                .contentType(ContentType.JSON)
                .body(user1)
                .when()
                .post("/v1/auth/registration")
                .then()
                .statusCode(201)
                .extract()
                .path("jwt-token");

        token2 = given()
                .contentType(ContentType.JSON)
                .body(user2)
                .when()
                .post("/v1/auth/registration")
                .then()
                .statusCode(201)
                .extract()
                .path("jwt-token");

    }

    @Test
    public void testSendFriendRequest() {

        given()
                .header("Authorization", "Bearer " + token1)
                .post("/v1/friendship/request/"
                        + jwtUtil.getUserIdByJwtHeader("Bearer " + token2))
                .then()
                .statusCode(200);

    }

    @Test
    public void testAcceptFriendRequest() {

        given()
                .header("Authorization", "Bearer " + token1)
                .post("/v1/friendship/request/"
                        + jwtUtil.getUserIdByJwtHeader("Bearer " + token2))
                .then()
                .statusCode(200);

        given()
                .header("Authorization", "Bearer " + token2)
                .patch("/v1/friendship/accept/"
                        + jwtUtil.getUserIdByJwtHeader("Bearer " + token1))
                .then()
                .statusCode(200);

    }

    @Test
    public void testDeclineFriendRequest() {

        given()
                .header("Authorization", "Bearer " + token1)
                .post("/v1/friendship/request/"
                        + jwtUtil.getUserIdByJwtHeader("Bearer " + token2))
                .then()
                .statusCode(200);

        given()
                .header("Authorization", "Bearer " + token2)
                .patch("/v1/friendship/decline/"
                        + jwtUtil.getUserIdByJwtHeader("Bearer " + token1))
                .then()
                .statusCode(200);

    }

    @Test
    public void testRemoveFriend() {

        given()
                .header("Authorization", "Bearer " + token1)
                .post("/v1/friendship/request/"
                        + jwtUtil.getUserIdByJwtHeader("Bearer " + token2))
                .then()
                .statusCode(200);

        given()
                .header("Authorization", "Bearer " + token2)
                .patch("/v1/friendship/accept/"
                        + jwtUtil.getUserIdByJwtHeader("Bearer " + token1))
                .then()
                .statusCode(200);

        given()
                .header("Authorization", "Bearer " + token2)
                .delete("/v1/friendship/remove/"
                        + jwtUtil.getUserIdByJwtHeader("Bearer " + token1))
                .then()
                .statusCode(200);

    }

    @AfterEach
    public void tearDown() {

        if (token1 != null && !token1.isEmpty())
            given()
                    .header("Authorization", "Bearer " + token1)
                    .when()
                    .delete("/v1/users")
                    .then()
                    .statusCode(200);

        if (token2 != null && !token2.isEmpty())
            given()
                    .header("Authorization", "Bearer " + token2)
                    .when()
                    .delete("/v1/users")
                    .then()
                    .statusCode(200);

    }

}
