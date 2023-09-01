package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.dto.ChatRequestInitiationDto;
import com.alfabaykal.socialmediaapi.dto.ChatRequestResponseDto;
import com.alfabaykal.socialmediaapi.dto.UserRegistrationDto;
import com.alfabaykal.socialmediaapi.security.JwtUtil;
import com.alfabaykal.socialmediaapi.service.ChatRequestService;
import com.alfabaykal.socialmediaapi.service.UserService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
public class ChatRequestControllerTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ChatRequestService chatRequestService;

    @Autowired
    private UserService userService;

    private String token1;
    private String token2;

    private Long userId1;
    private Long userId2;

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

        userId1 = userService.getUserIdByUsername("user1");
        userId2 = userService.getUserIdByUsername("user2");

    }

    @Test
    public void testSendChatRequest() {

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

        ChatRequestInitiationDto chatRequestInitiationDto = new ChatRequestInitiationDto();
        chatRequestInitiationDto.setReceiverId(jwtUtil.getUserIdByJwtHeader("Bearer " + token2));

        given()
                .header("Authorization", "Bearer " + token1)
                .header("Content-Type", "application/json")
                .body(chatRequestInitiationDto)
                .post("/v1/chat_requests/send")
                .then()
                .statusCode(200);

    }

    @Test
    public void testAcceptChatRequest() {

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

        ChatRequestInitiationDto chatRequestInitiationDto = new ChatRequestInitiationDto();
        chatRequestInitiationDto.setReceiverId(jwtUtil.getUserIdByJwtHeader("Bearer " + token2));

        given()
                .header("Authorization", "Bearer " + token1)
                .header("Content-Type", "application/json")
                .body(chatRequestInitiationDto)
                .post("/v1/chat_requests/send")
                .then()
                .statusCode(200);

        ChatRequestResponseDto chatRequestResponseDto = new ChatRequestResponseDto();
        chatRequestResponseDto.setChatRequestId(chatRequestService.getChatRequestBySenderIdAndReceiverId(userId1, userId2)
                .orElseThrow().getId());

        given()
                .header("Authorization", "Bearer " + token2)
                .header("Content-Type", "application/json")
                .log().all()
                .body(chatRequestResponseDto)
                .patch("/v1/chat_requests/accept")
                .then()
                .statusCode(200);

    }

    @Test
    public void testDeclineChatRequest() {

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

        ChatRequestInitiationDto chatRequestInitiationDto = new ChatRequestInitiationDto();
        chatRequestInitiationDto.setReceiverId(jwtUtil.getUserIdByJwtHeader("Bearer " + token2));

        given()
                .header("Authorization", "Bearer " + token1)
                .header("Content-Type", "application/json")
                .body(chatRequestInitiationDto)
                .post("/v1/chat_requests/send")
                .then()
                .statusCode(200);

        ChatRequestResponseDto chatRequestResponseDto = new ChatRequestResponseDto();
        chatRequestResponseDto.setChatRequestId(chatRequestService.getChatRequestBySenderIdAndReceiverId(userId1, userId2)
                .orElseThrow().getId());

        given()
                .header("Authorization", "Bearer " + token2)
                .header("Content-Type", "application/json")
                .log().all()
                .body(chatRequestResponseDto)
                .patch("/v1/chat_requests/decline")
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
