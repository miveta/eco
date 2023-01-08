package com.fer.hr.waps.lab2.domain.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.apache.logging.log4j.message.Message;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {
    private String id;
    private String username;
    private List<Message> received;
    private List<Message> sent;
}
