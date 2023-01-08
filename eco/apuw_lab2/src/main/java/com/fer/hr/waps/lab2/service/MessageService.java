package com.fer.hr.waps.lab2.service;

import com.fer.hr.waps.lab2.domain.entity.MessageEntity;
import com.fer.hr.waps.lab2.domain.repository.MessageRepository;
import com.fer.hr.waps.lab2.domain.repository.UserRepository;
import com.fer.hr.waps.lab2.mapper.MessageMapper;
import com.fer.hr.waps.lab2.model.Message;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message sendMessage(String senderId, String message) {
        MessageEntity messageEntity = new MessageEntity();

        messageEntity.setMessage(message);
        messageEntity.setSenderId(senderId);
        messageEntity.setSent(Instant.now());

        messageRepository.save(messageEntity);

        return MessageMapper.INSTANCE.fromEntity(messageEntity);
    }
}
