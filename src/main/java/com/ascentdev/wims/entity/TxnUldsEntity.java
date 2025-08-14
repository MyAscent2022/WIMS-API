/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
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
@Table(name = "txn_ulds")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TxnUldsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;
  
  @Column(name="uld_number")
  String uldNumber;
  
  @Column(name="uld_type")
  Long uldType;
  
  @Column(name="total_count")
  Integer totalCount;
  
  @Column(name="short_landed")
  Integer shortLanded;
  
  @Column(name="total_expected")
  Integer totalExpected;
  
  @Column(name="flight_number")
  String flightNumber;
  
  @Column(name="airline_returnee")
  Integer airlineReturnee;
  
  @Column(name="arrival_date")
  Date arrivalDate;
  
  @Column(name="mawb_number")
  String mawbNumber;
  
  @Column(name="max_capacity")
  Integer maxCapacity;
  
  @Column(name="tare_weight")
  Float tareWeight;
  
  @Column(name="total_mawb")
  Integer totalMawb;
  
  @Column(name="uld_status")
  Long uldStatus;
  
  @Column(name="total_pieces")
  Integer totalPieces;
  
  @Column(name="total_weight")
  Integer totalWeight;
  
  @Column(name="uld_activity_log_id")
  Long uldActivityLogId;
  
  @Column(name="is_received")
  Boolean isReceived;
  
  @Column(name="mawb_id")
  Integer mawbId;
}
