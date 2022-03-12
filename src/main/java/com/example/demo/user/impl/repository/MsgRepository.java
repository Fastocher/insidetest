package com.example.demo.user.impl.repository;

import com.example.demo.user.impl.entity.MsgEntity;
import com.example.demo.user.impl.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsgRepository extends CrudRepository<MsgEntity,Long> {

}
