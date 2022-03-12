package com.example.demo.user.impl.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "msg")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MsgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    private UserEntity user;
}
