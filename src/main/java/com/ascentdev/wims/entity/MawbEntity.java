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
        + "tu.is_received,\n"
        + "tu.id AS uld_id,\n"
        + "ru.uld_status,\n"
        + "tm.is_priority\n"
        + "FROM txn_mawb tm\n"
        + "INNER JOIN txn_ulds tu ON tu.mawb_id = tm.id \n"
        + "INNER JOIN ref_uld ru ON ru.uld_no = tu.uld_number\n"
        + "ORDER BY tm.is_priority DESC")
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

//  @Column(name = "number_of_containers")
//  Integer numberOfContainers;
  @Column(name = "number_of_packages")
  Integer numberOfPackages;

  @Column(name = "origin_code")
  String originCode;

  @Column(name = "registry_number")
  String registryNumber;

  @Column(name = "time_of_arrival")
  Time timeOfArrival;

  @Column(name = "volume")
  Float volume;

  @Column(name = "uld_number")
  String uldNumber;

  @Column(name = "uld_container_type_id")
  Integer uldContainerTypeId;

  @Column(name = "cargo_status")
  String cargoStatus;

  @Column(name = "length")
  Float length;

  @Column(name = "width")
  Float width;

  @Column(name = "height")
  Float height;

  @Column(name = "actual_weight")
  Float actualWeight;

  @Column(name = "actual_volume")
  Float actualVolume;

  @Column(name = "actual_pcs")
  Integer actualPcs;

  @Column(name = "cargo_category_id")
  Long cargoCategoryId;

  @Column(name = "cargo_class_id")
  Long cargoClassId;

  @Column(name = "flight_id")
  Integer flightId;

  @Column(name = "consignee_name")
  String consigneeName;

  @Column(name = "uld_status")
  Integer uldStatus;

  @Column(name = "gross_mass")
  Integer grossMass;

  @Column(name = "is_received")
  Boolean isReceived;

  @Column(name = "uld_id")
  Integer uldId;
  
  @Column(name = "is_priority")
  Boolean isPriority;
}
