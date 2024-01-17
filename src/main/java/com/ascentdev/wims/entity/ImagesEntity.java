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
public class ImagesEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  
  @Column(name="cargo_activity_log_id")
  long cargoActivityLogId;
  
  @Column(name="cargo_condition_id")
  long cargoConditionId;
  
  @Column(name="file_name")
  String fileName;
  
  @Column(name="file_path")
  String filePath;
  
  @Column(name="remarks")
  String remarks;
}
