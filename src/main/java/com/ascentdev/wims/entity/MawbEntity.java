/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
import java.sql.Time;
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
@Table(name = "txn_mawb")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MawbEntity {

  @Id
  int id;

  @Column(name = "date_of_arrival")
  Date dateOfArrival;

  @Column(name = "destination_code")
  String destinationCode;

  @Column(name = "mawb_number")
  String mawbNumber;

  @Column(name = "number_of_containers")
  int numberOfContainers;

  @Column(name = "number_of_packages")
  int numberOfPackages;

  @Column(name = "origin_code")
  String originCode;

  @Column(name = "registry_number")
  String registryNumber;

  @Column(name = "time_of_arrival")
  Time timeOfArrival;

  @Column(name = "volume")
  float volume;

  @Column(name = "uld_number")
  String uldNumber;

  @Column(name = "uld_container_type_id")
  int uldContainerTypeId;

  @Column(name = "cargo_status")
  String cargoStatus;

  @Column(name = "length")
  float length;

  @Column(name = "width")
  float width;

  @Column(name = "height")
  float height;

  @Column(name = "actual_weight")
  float actualWeight;

  @Column(name = "actual_volume")
  Float actualVolume;

  @Column(name = "actual_pcs")
  int actualPcs;

  @Column(name = "cargo_category_id")
  Long cargoCategoryId;

  @Column(name = "cargo_class_id")
  Long cargoClassId;
  
  @Column(name = "flight_id")
  int flightId;

}
