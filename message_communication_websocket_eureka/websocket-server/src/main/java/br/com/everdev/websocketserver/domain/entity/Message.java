package br.com.everdev.websocketserver.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Message {
    
    private UUID userOrigin;
    
    private UUID userIdDestination;
    
    private String userDestination;
    
    private LocalDateTime dateTime;
    
    private String message;
    
    private Status status;
    
    @Builder
    public Message(String userDestination, String message) {
        this.userDestination = userDestination;
        this.message = message;
    }
}
