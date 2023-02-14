package com.example.admin.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {

    private String from;
    private String text;
}
