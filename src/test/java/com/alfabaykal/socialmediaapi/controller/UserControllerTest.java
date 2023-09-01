package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.security.JwtUtil;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UserControllerTest {


    @Autowired
    private JwtUtil jwtUtil;

    private final static String USERNAME1 = "username1";
    private final static String EMAIL1 = "email1@example.com";
    private final static String PASSWORD1 = "password1";

    private final static String USERNAME2 = "username2";
    private final static String EMAIL2 = "email2@example.com";
    private final static String PASSWORD2 = "password2";

    private final static String USERNAME3 = "username3";
    private final static String EMAIL3 = "email3@example.com";
    private final static String PASSWORD3 = "password3";

    private static String token1;
    private static String token2;
    private static String token3;

    @BeforeAll
    public static void setUp(){

        UserRegistrationDto userToRegister1 = new UserRegistrationDto();
        userToRegister1.setUsername(USERNAME1);
        userToRegister1.setEmail(EMAIL1);
        userToRegister1.setPassword(PASSWORD1);

        UserRegistrationDto userToRegister2 = new UserRegistrationDto();
        userToRegister2.setUsername(USERNAME2);
        userToRegister2.setEmail(EMAIL2);
        userToRegister2.setPassword(PASSWORD2);

        UserRegistrationDto userToRegister3 = new UserRegistrationDto();
        userToRegister3.setUsername(USERNAME3);
        userToRegister3.setEmail(EMAIL3);
        userToRegister3.setPassword(PASSWORD3);

        token1 = given()
                .contentType(ContentType.JSON)
                .body(userToRegister1)
                .when()
                .post("/v1/auth/registration")
                .then()
                .statusCode(201)
                .extract()
                .path("jwt-token");

        token2 = given()
                .contentType(ContentType.JSON)
                .body(userToRegister2)
                .when()
                .post("/v1/auth/registration")
                .then()
                .statusCode(201)
                .extract()
                .path("jwt-token");

        token3 = given()
                .contentType(ContentType.JSON)
                .body(userToRegister3)
                .when()
                .post("/v1/auth/registration")
                .then()
                .statusCode(201)
                .extract()
                .path("jwt-token");

    }

    @Test
    public void testGetAllUsers() {

        given()
                .when()
                .header("Authorization", "Bearer " + token1)
                .get("/v1/users")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("users", not(empty()));

    }

    @Test
    public void getUserById() {

        given()
                .when()
                .header("Authorization", "Bearer " + token1)
                .get("/v1/users/" + jwtUtil.getUserIdByJwtHeader("Bearer " + token2))
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("username", equalTo(USERNAME2));

    }

    @Test
    public void testUpdateMyAccount() {

        UserRegistrationDto updatedUser = new UserRegistrationDto();
        updatedUser.setUsername("newUsername");
        updatedUser.setEmail("newEmail@example.com");
        updatedUser.setPassword("newPassword");

        given()
                .header("Authorization", "Bearer " + token1)
                .contentType(ContentType.JSON)
                .body(updatedUser)
                .put("/v1/users")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("username", equalTo("newUsername"));

    }

    @Test
    public void testDeleteMyAccount() {

        given()
                .header("Authorization", "Bearer " + token3)
                .when()
                .delete("/v1/users")
                .then()
                .statusCode(200);

        token3 = "";

    }

    @AfterAll
    public static void tearDown() {

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

        if (token3 != null && !token3.isEmpty())
            given()
                    .header("Authorization", "Bearer " + token3)
                    .when()
                    .delete("/v1/users")
                    .then()
                    .statusCode(200);

    }

}
