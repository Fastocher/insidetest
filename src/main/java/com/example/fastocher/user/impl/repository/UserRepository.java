package com.example.fastocher.user.impl.repository;

import com.example.fastocher.user.impl.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    public UserEntity findByName(String name);
}
