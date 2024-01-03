/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name="txn_cargo_images")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoImagesEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  
  @Column(name="file_path")
  String filePath;
  
  @Column(name="file_name")
  String fileName;
  
  @Column(name="cargo_condition_id")
  long cargoConditionId;
  
  @Column(name="mawb_number")
  String mawbNumber;
  
  @Column(name="hawb_number")
  String hawbNumber;
  
  @Column(name="flight_number")
  String flightNumber;
  
  @Column(name="uld_number")
  String uldNumber;
  
  @Column(name="remarks")
  String remarks;
}
