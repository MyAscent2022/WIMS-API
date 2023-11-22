/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
@Entity
@Table(name="user_activity_logs", schema="commons")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserLogsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  
  @Column(name="log_in_at")
  Timestamp logInAt;
  
  @Column(name="log_out_at")
  Timestamp logOutAt;
  
  @Column(name="modified_at")
  Timestamp modifiedAt;
  
  @Column(name="name")
  String name;
  
  @Column(name="role_id")
  long roleId;
  
  @Column(name="user_id")
  long userId;
  
  @Column(name="is_active")
  boolean isActive;
  
  @Column(name="is_mobile")
  boolean isMobile;
}
