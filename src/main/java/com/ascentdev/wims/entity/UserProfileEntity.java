/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
@Entity
@Table(name="user_profile")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserProfileEntity {
  @Id
  long id;
  
  @Column(name="firstname")
  String firstname;
  
  @Column(name="lastname")
  String lastname;
  
  @Column(name="mobile_number")
  Long mobileNumber;
  
  @Column(name="user_id")
  long userId;
  
  @Column(name="middlename")
  String middlename;
}
