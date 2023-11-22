/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Time;
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
@Table(name="txn_storage_logs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StorageLogsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  
  @Column(name="user_id")
  String userId;
  
  @Column(name="flight_number")
  String flightNumber;
  
  @Column(name="registry_number")
  String registryNumber;
  
  Time ttc;
  
  @Column(name="modified_at")
  Timestamp modifiedAt;
  
  @Column(name="modified_by_id")
  Long modifiedById;
  
  @Column(name="created_at")
  Timestamp createdAt;
  
  @Column(name="created_by_id")
  Long createdById;
  
  @Column(name="storager_status")
  String storagerStatus;
  
  @Column(name="mawb_number")
  String mawbNumber;
  
  @Column(name="hawb_number")
  String hawbNumber;
}
