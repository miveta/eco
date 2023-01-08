package com.fer.hr.waps.lab2.domain.repository;

import com.fer.hr.waps.lab2.domain.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findUserByUsername(String username);
}
