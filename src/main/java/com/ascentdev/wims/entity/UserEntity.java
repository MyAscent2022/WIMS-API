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
@Table(name="users", schema="commons")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {
  @Id
  long id;
  
  String email;
  
  String passkey;
  
  String username;
  
  @Column(name="role_id")
  Long roleId;
  
  @Column(name="user_type_id")
  Long userTypeId;
}
