package com.example.ReRun.Config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.ReRun.reRun.ChatMessage;
import com.example.ReRun.reRun.MessageType;

import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j


public class WebSocketEventListener {
	private final SimpMessageSendingOperations messageTemplate;
	@EventListener
	
	public void handleWebSocketDisconectListener(
			SessionDisconnectEvent event
			) {
		StompHeaderAccessor headerAccessor= StompHeaderAccessor.wrap(event.getMessage());
		String username=(String)headerAccessor.getSessionAttributes().get("username");
		if(username!=null) {
			log.info("user disconnected:{}",username );
			var chatMessage= ChatMessage.builder()
					.type(MessageType.LEAVE)
					.sender(username)
					.build();
			messageTemplate.convertAndSend("/topic/public",chatMessage);
		}
		
	
		
	}

}

