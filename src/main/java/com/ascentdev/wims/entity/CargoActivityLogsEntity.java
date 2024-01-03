/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author
 * ASCENT
 */
@Data
@Entity
@Table(name = "cargo_activity_log_old")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoActivityLogsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @Column(name = "flight_id")
  int flightId;

  @Column(name = "mawb_id")
  int mawbId;

  @Column(name = "hawb_id")
  int hawbId;

  @Column(name = "location")
  String location;

  @Column(name = "handled_by_id")
  String handledById;

  @Column(name = "received_date")
  Timestamp receivedDatetime;

  @Column(name = "stored_datetime")
  Timestamp storedDatetime;

  @Column(name = "released_datetime")
  Timestamp releasedDatetime;

  @Column(name = "payment_datetime")
  Timestamp paymentDatetime;

  @Column(name = "updated_at")
  Timestamp updatedAt;

  @Column(name = "released_type_id")
  int releasedTypeId;

  @Column(name = "actual_pcs")
  int actualPcs;

  @Column(name = "created_by_id")
  int createdById;

  @Column(name = "updated_by_id")
  int updatedById;

}
