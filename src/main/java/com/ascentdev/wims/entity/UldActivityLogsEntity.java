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
 * @author ASCENT SOLUTIONS INC
 */
@Data
@Entity
@Table(name = "uld_activity_logs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UldActivityLogsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "flight_id")
  int flightId;

  @Column(name = "uld_id")
  int uldId;

  @Column(name = "location")
  String location;

  @Column(name = "activity_status")
  String activityStatus;

  @Column(name = "received_stripped_by_id")
  int receivedStrippedById;

  @Column(name = "received_stripped_at")
  Timestamp receivedStrippedAt;
}
