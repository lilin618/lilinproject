package com.example.lilinproject.pojo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "pwquestion")
public class PWQuestion {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String question;
    @Column
    private String answer;
    @Column(name = "userid")
    private long userId;
    @Column
    private String isvaild;
    @Column(name = "createdate")
    private String createDate;
}
