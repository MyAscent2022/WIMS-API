/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;
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
@Table(name="txn_cargo_release")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoReleaseEntity {
  @Id
  long id;
  
  @Column(name="user_id")
  String userId;
  
  @Column(name="created_at")
  Timestamp createdAt;
  
  @Column(name="released_dt")
  Timestamp releasedDt;
  
  @Column(name="modified_at")
  Timestamp modifiedAt;
  
  @Column(name="mawb_number")
  String mawbNumber;
  
  @Column(name="hawb_number")
  String hawbNumber;
  
}
