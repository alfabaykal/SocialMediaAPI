package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.dto.AuthenticationDto;
import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
public class AuthenticationControllerTest {

    private final String USERNAME = "username";
    private final String EMAIL = "email@example.com";
    private final String PASSWORD = "password";

    private String token;

    @Test
    public void testPerformRegistration() {

        UserRegistrationDto userToRegister = new UserRegistrationDto();
        userToRegister.setUsername(USERNAME);
        userToRegister.setEmail(EMAIL);
        userToRegister.setPassword(PASSWORD);

        token = given()
                .contentType(ContentType.JSON)
                .body(userToRegister)
                .when()
                .post("/v1/auth/registration")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("jwt-token", notNullValue())
                .extract()
                .path("jwt-token");
    }

    @Test
    public void testPerformRegistrationWithInvalidData() {

        UserRegistrationDto wrongUser = new UserRegistrationDto();
        wrongUser.setUsername("");
        wrongUser.setEmail("invalid email");
        wrongUser.setPassword("");

        given()
                .contentType(ContentType.JSON)
                .body(wrongUser)
                .when()
                .post("/v1/auth/registration")
                .then()
                .statusCode(400);
    }

    @Test
    public void testPerformLogin() {

        UserRegistrationDto userToRegister = new UserRegistrationDto();
        userToRegister.setUsername(USERNAME);
        userToRegister.setEmail(EMAIL);
        userToRegister.setPassword(PASSWORD);

        AuthenticationDto userToLogin = new AuthenticationDto();
        userToLogin.setUsername(USERNAME);
        userToLogin.setPassword(PASSWORD);

        given()
                .contentType(ContentType.JSON)
                .body(userToRegister)
                .when()
                .post("/v1/auth/registration")
                .then()
                .statusCode(201);

        token = given()
                .contentType(ContentType.JSON)
                .body(userToLogin)
                .when()
                .post("/v1/auth/login")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("jwt-token", notNullValue())
                .extract()
                .path("jwt-token");

    }

    @Test
    public void testPerformLoginWithInvalidData() {

        AuthenticationDto wrongUserToLogin = new AuthenticationDto();
        wrongUserToLogin.setUsername("");
        wrongUserToLogin.setPassword(PASSWORD);

        given()
                .contentType(ContentType.JSON)
                .body(wrongUserToLogin)
                .when()
                .post("/v1/auth/login")
                .then()
                .statusCode(400);

    }

    @AfterEach
    public void tearDown() {

        if (token != null && !token.isEmpty())
            given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .delete("/v1/users")
                    .then()
                    .statusCode(200);
    }

}
