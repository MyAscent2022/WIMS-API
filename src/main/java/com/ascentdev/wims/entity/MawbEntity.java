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
 * @author ASCENT
 */
@Data
@Entity
@Table(name="txn_mawb", schema="manifest")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MawbEntity {
  @Id
  long id;
  
  @Column(name="created_at")
  Timestamp createdAt;
  
  @Column(name="date_of_arrival")
  Date dateOfArrival;
  
  @Column(name="destination_code")
  String destinationCode;
  
  @Column(name="mawb_number")
  String mawbNumber;
  
  @Column(name="modified_at")
  Timestamp modifiedAt;
  
  @Column(name="number_of_containers")
  long numberOfContainers;
  
  @Column(name="number_of_packages")
  int numberOfPackages;
  
  @Column(name="origin_code")
  String originCode;

  @Column(name="registry_number")  
  String registryNumber;
  
  @Column(name="time_of_arrival") 
  Time timeOfArrival;
  
  @Column(name="volume") 
  float volume;
  
  @Column(name="flight_number") 
  String flightNumber;
  
  @Column(name="uld_number") 
  String uldNumber;
  
  @Column(name="uld_container_type_id")
  Long uldContainerTypeId;
}
