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
@Table(name="txn_rack_utilization")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RackUtilEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  
  @Column(name="ref_rack_id")
  long refRackId;
  
  @Column(name="stored_by_id")
  int storedById;
  
  @Column(name="stored_dt")
  Timestamp storedDt;
  
  @Column(name="volume")
  Float volume;
  
  @Column(name="location")
  String location;
  
  @Column(name="no_of_pieces")
  int noOfPieces;
  
  @Column(name="created_by_id")
  int createdById;
  
  @Column(name="created_at")
  Timestamp createdAt;
  
  @Column(name="modified_at")
  Timestamp modifiedAt;
  
  @Column(name="modified_by_id")
  int modifiedById;
  
  @Column(name="txn_mawb_id")
  long txnMawbId;
  
  @Column(name="txn_hawb_id")
  long txnHawbId;
}
