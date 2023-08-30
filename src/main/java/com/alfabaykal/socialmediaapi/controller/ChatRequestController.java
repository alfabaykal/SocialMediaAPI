package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.dto.ChatRequestResponseDto;
import com.alfabaykal.socialmediaapi.dto.ChatRequestInitiationDto;
import com.alfabaykal.socialmediaapi.facade.ChatRequestFacade;
import com.alfabaykal.socialmediaapi.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Чат и переписка", description = "Методы для запроса переписки и добавления в чат")
@RestController
@RequestMapping("/v1/chat_requests")
public class ChatRequestController {

    private final ChatRequestFacade chatRequestFacade;
    private final JwtUtil jwtUtil;

    public ChatRequestController(ChatRequestFacade chatRequestFacade, JwtUtil jwtUtil) {
        this.chatRequestFacade = chatRequestFacade;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Отправка запроса на личную переписку")
    @PostMapping("/send")
    public void sendChatRequest(@Parameter(description = "Bearer header with JWT")
                                @RequestHeader("Authorization") String jwtHeader,
                                @RequestBody ChatRequestInitiationDto chatRequestInitiationDto) {
        chatRequestFacade.sendChatRequest(jwtUtil.getUserIdByJwtHeader(jwtHeader),
                chatRequestInitiationDto.getReceiverId(), null);
    }

    @Operation(summary = "Отправка приглашения в чат")
    @PostMapping("/send/{chatId}")
    public void sendChatRequest(@Parameter(description = "Bearer header with JWT")
                                @RequestHeader("Authorization") String jwtHeader,
                                @RequestBody ChatRequestInitiationDto chatRequestInitiationDto,
                                @Parameter(description = "Идентификатор чата")
                                @PathVariable Long chatId) {
        chatRequestFacade.sendChatRequest(jwtUtil.getUserIdByJwtHeader(jwtHeader),
                chatRequestInitiationDto.getReceiverId(), chatId);
    }

    @Operation(summary = "Принятие запроса на личную переписку или приглашения в чат")
    @PatchMapping("/accept")
    public void acceptChatRequest(@Parameter(description = "Bearer header with JWT")
                                  @RequestHeader("Authorization") String jwtHeader,
                                  @RequestBody ChatRequestResponseDto chatRequestResponseDto) {
        chatRequestFacade.acceptChatRequest(chatRequestResponseDto.getChatRequestId(),
                jwtUtil.getUserIdByJwtHeader(jwtHeader));
    }

    @Operation(summary = "Отклонение запроса на личную переписку или приглашения в чат")
    @PatchMapping("/decline")
    public void declineChatRequest(@Parameter(description = "Bearer header with JWT")
                                   @RequestHeader("Authorization") String jwtHeader,
                                   @RequestBody ChatRequestResponseDto chatRequestResponseDto) {
        chatRequestFacade.declineChatRequest(chatRequestResponseDto.getChatRequestId(), jwtUtil.getUserIdByJwtHeader(jwtHeader));
    }
}
