/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.entity;

/**
 *
 * @author User
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "txn_trucker_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoReleasingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  @Column(name = "awb_id")
  long awb_id;
  @Column(name = "trucker")
  String trucker;
  @Column(name = "plate_no")
  String plateNo;
  @Column(name = "created_by")
  long createdBy;
  @Column(name = "is_hawb")
  boolean isHawb;
  @Column(name = "created_dt")
  Timestamp createdDt;

}
