package com.example.admin.websocket;


import com.example.admin.jwt.JwtProvider;
import com.example.admin.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    @Autowired
    UserRepository userRepository;
    private Message<?> Exception;

    @Override
    public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
        final var accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        final var cmd = accessor.getCommand();
        if (StompCommand.CONNECT == cmd || StompCommand.SEND == cmd) {
            String jwt = accessor.getLogin();
            if (jwt != null && jwt.startsWith("Bearer")) {
                jwt = jwt.substring(7);
            }

            if (!JwtProvider.validateTokenJWT(jwt)){
                return Exception;
            }
        }
        return message;
    }
}