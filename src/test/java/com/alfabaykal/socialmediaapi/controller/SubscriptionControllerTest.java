package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.security.JwtUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
public class SubscriptionControllerTest {

    @Autowired
    private JwtUtil jwtUtil;

    private String publisherToken;
    private String subscriberToken;

    @BeforeEach
    public void setUp() {

        UserRegistrationDto userToRegister1 = new UserRegistrationDto();
        String username1 = "publisher";
        userToRegister1.setUsername(username1);
        String email1 = "publisher@example.com";
        userToRegister1.setEmail(email1);
        String password1 = "publisherPassword";
        userToRegister1.setPassword(password1);

        UserRegistrationDto userToRegister2 = new UserRegistrationDto();
        String username2 = "subscriber";
        userToRegister2.setUsername(username2);
        String email2 = "subscriber@example.com";
        userToRegister2.setEmail(email2);
        String password2 = "subscriberPassword";
        userToRegister2.setPassword(password2);

        publisherToken = given()
                .contentType(ContentType.JSON)
                .body(userToRegister1)
                .when()
                .post("/v1/auth/registration")
                .then()
                .statusCode(201)
                .extract()
                .path("jwt-token");

        subscriberToken = given()
                .contentType(ContentType.JSON)
                .body(userToRegister2)
                .when()
                .post("/v1/auth/registration")
                .then()
                .statusCode(201)
                .extract()
                .path("jwt-token");

    }

    @Test
    public void testSubscribe() {

        given()
                .header("Authorization", "Bearer " + subscriberToken)
                .post("/v1/subscriptions/subscribe/"
                        + jwtUtil.getUserIdByJwtHeader("Bearer " + publisherToken))
                .then()
                .statusCode(200);

    }

    @Test
    public void testUnsubscribe() {

        given()
                .header("Authorization", "Bearer " + subscriberToken)
                .post("/v1/subscriptions/subscribe/"
                        + jwtUtil.getUserIdByJwtHeader("Bearer " + publisherToken))
                .then()
                .statusCode(200);

        given()
                .header("Authorization", "Bearer " + subscriberToken)
                .post("/v1/subscriptions/unsubscribe/"
                        + jwtUtil.getUserIdByJwtHeader("Bearer " + publisherToken))
                .then()
                .statusCode(200);

    }

    @AfterEach
    public void tearDown() {

        if (publisherToken != null && !publisherToken.isEmpty())
            given()
                    .header("Authorization", "Bearer " + publisherToken)
                    .when()
                    .delete("/v1/users")
                    .then()
                    .statusCode(200);

        if (subscriberToken != null && !subscriberToken.isEmpty())
            given()
                    .header("Authorization", "Bearer " + subscriberToken)
                    .when()
                    .delete("/v1/users")
                    .then()
                    .statusCode(200);

    }
}
