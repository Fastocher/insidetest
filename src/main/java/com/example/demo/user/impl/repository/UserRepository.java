package com.example.demo.user.impl.repository;

import com.example.demo.user.impl.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    public UserEntity findByName(String name);
}
