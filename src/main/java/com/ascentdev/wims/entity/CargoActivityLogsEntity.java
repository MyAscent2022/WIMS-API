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
@Table(name = "cargo_activity_logs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoActivityLogsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "flight_id")
  long flightId;

  @Column(name = "mawb_id")
  long mawbId;

  @Column(name = "hawb_id")
  int hawbId;

  @Column(name = "location")
  String location;

  @Column(name = "actual_item_count")
  int actualPcs;
  
  @Column(name = "job_assignment_id")
  long handledById;

  @Column(name = "received_released_date")
  Timestamp receivedReleasedDate;
  
  @Column(name = "created_at")
  Timestamp createdAt;

  @Column(name = "updated_at")
  Timestamp updatedAt;

  @Column(name = "created_by_id")
  long createdById;

  @Column(name = "updated_by_id")
  Long updatedById;
  
  @Column(name = "is_deleted")
  boolean isDeleted;
  
  @Column(name = "activity_status")
  String activityStatus;

}
