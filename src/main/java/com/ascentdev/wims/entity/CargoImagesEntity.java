/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Subselect;

/**
 *
 * @author ASCENT
 */
@Data
@Entity
@Subselect("SELECT tci.id,\n"
        + "rcc.condition AS cargo_condition,\n"
        + "tci.remarks,\n"
        + "tci.file_name,\n"
        + "tci.file_path,\n"
        + "tci.cargo_activity_log_id,\n"
        + "tci.cargo_condition_id\n"
        + "FROM txn_cargo_images tci\n"
        + "INNER JOIN cargo_activity_logs cal ON cal.id = tci.cargo_activity_log_id\n"
        + "INNER JOIN ref_cargo_condition rcc ON rcc.id = tci.cargo_condition_id")
public class CargoImagesEntity {

  @Id
  long id;

  @Column(name = "file_path")
  String filePath;

  @Column(name = "file_name")
  String fileName;
  
  @Column(name = "cargo_condition")
  String cargoCondition;

  @Column(name="cargo_condition_id")
  long cargoConditionId;

  @Column(name = "cargo_activity_log_id")
  long cargoActivityLogId;

  @Column(name = "remarks")
  String remarks;
}
