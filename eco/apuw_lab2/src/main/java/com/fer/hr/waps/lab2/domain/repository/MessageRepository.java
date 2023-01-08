package com.fer.hr.waps.lab2.domain.repository;

import com.fer.hr.waps.lab2.domain.entity.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<MessageEntity, String> {
    MessageEntity findMessageEntityById(String id);

    List<MessageEntity> findAllBySenderId(String senderId);

    List<MessageEntity> findAllByReceiverId(String receiverId);

}