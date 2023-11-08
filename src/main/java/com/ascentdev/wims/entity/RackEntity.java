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
public class RackEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  
  @Column(name="ref_rack_id")
  Long refRackId;
  
  @Column(name="mawb_number")
  String mawbNumber;
  
  @Column(name="hawb_number")
  String hawbNumber;
  
  @Column(name="volume_mawb")
  float volumeMawb;
  
  @Column(name="stored_by_id")
  Long storedById;
  
  @Column(name="stored_dt")
  Timestamp storedDt;
  
  @Column(name="released_by_id")
  Long releasedById;
  
  @Column(name="released_dt")
  Timestamp releasedDt;
  
  
  @Column(name="ref_layer_id")
  Long reflayerid;
  
  @Column(name="location_number")
  String locationNumber;
  
  @Column(name="no_of_pieces")
  Long noOfPieces;
  
  @Column(name="releasing_status_id")
  Long releasingStatusId;
  
  @Column(name="is_paid")
  Boolean isPaid;
  
  @Column(name="txn_mawb_id")
  Long txnMawbId;
}
