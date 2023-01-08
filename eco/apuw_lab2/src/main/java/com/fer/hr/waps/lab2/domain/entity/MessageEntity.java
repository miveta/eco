package com.fer.hr.waps.lab2.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages")
public class MessageEntity {
    private String id;
    private String senderId;
    private String message;
    private Instant sent;
}
