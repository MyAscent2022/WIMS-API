/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "txn_hawb")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HawbEntity {

  @Id
  int id;

  @Column(name = "date_of_arrival")
  Date dateOfArrival;

//  @Column(name="flight_number")
//  String flightNumber;
  @Column(name = "gross_mass")
  Float grossMass;

  @Column(name = "hawb_number")
  String hawbNumber;

  @Column(name = "mawb_number")
  String mawbNumber;

  @Column(name = "number_of_containers")
  Long numberOfContainers;

  @Column(name = "number_of_packages")
  String numberOfPackages;

  @Column(name = "origin_code")
  String originCode;

  @Column(name = "registry_number")
  String registryNumber;

  @Column(name = "time_of_arrival")
  Time timeOfArrival;

  @Column(name = "volume")
  float volume;
}
