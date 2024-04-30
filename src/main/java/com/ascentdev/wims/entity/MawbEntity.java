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
import org.hibernate.annotations.Subselect;

/**
 *
 * @author ASCENT
 */
@Data
@Entity
@Subselect("SELECT tm.id, \n"
        + "tm.date_of_arrival, \n"
        + "tm.destination_code, \n"
        + "tm.mawb_number,\n"
        + "tm.number_of_containers,\n"
        + "tm.number_of_packages,\n"
        + "tm.origin_code,\n"
        + "tm.registry_number,\n"
        + "tm.time_of_arrival,\n"
        + "tm.volume,\n"
        + "tu.uld_number,\n"
        + "tm.uld_container_type_id,\n"
        + "tm.cargo_status,\n"
        + "tm.length,\n"
        + "tm.width,\n"
        + "tm.height,\n"
        + "tm.gross_mass,\n"
        + "tm.actual_weight,\n"
        + "tm.actual_volume,\n"
        + "tm.actual_pcs,\n"
        + "tm.cargo_category_id,\n"
        + "tm.cargo_class_id,\n"
        + "tm.flight_id,\n"
        + "tm.consignee_name,\n"
        + "ru.uld_status\n"
        + "FROM txn_mawb tm\n"
        + "INNER JOIN txn_ulds tu ON tu.mawb_number = tm.mawb_number \n"
        + "INNER JOIN ref_uld ru ON ru.uld_no = tu.uld_number")
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
  
  @Column(name = "consignee_name")
  String consigneeName;

  @Column(name = "uld_status")
  int uldStatus;

  @Column(name = "gross_mass")
  int grossMass;

}
