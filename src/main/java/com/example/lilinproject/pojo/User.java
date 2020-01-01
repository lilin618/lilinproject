package com.example.lilinproject.pojo;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User{
  @Id
  @GeneratedValue
  private long id;
  @Column
  private String username;
  @Column
  private String password;
  @Column(name = "birthDay")
  private String birthDay;
  @Column
  private String address;
  @Column(name = "isVaild")
  private String isVaild;
  @Column
  private String salt;
  @Column(name = "createDate")
  private String createDate;

}
