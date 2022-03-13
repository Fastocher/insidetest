package com.example.fastocher.user.impl.repository;

import com.example.fastocher.user.impl.entity.MsgEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsgRepository extends CrudRepository<MsgEntity,Long> {

}
