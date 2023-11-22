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
@Table(name="txn_uld_images", schema="manifest")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UldImagesEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  
  @Column(name="file_path")
  String filePath;
  
  @Column(name="file_name")
  String fileName;
  
  @Column(name="uld_condition_id")
  long uldConditionId;
  
  String remarks;
  
  @Column(name="flight_number")
  String flightNumber;
  
  @Column(name="uld_number")
  String uldNumber;
}
