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
 * @author
 * ASCENT
 */
@Data
@Entity
@Table(name = "txn_acceptance")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Acceptance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "user_id")
  int userId;

  @Column(name = "actual_pcs")
  Long actualPcs;

  @Column(name = "weight")
  private int weight;

  @Column(name = "volume")
  float volume;

  @Column(name = "txn_mawb_id")
  Long txnMawbId;

  @Column(name = "txn_hawb_id")
  int txnHawbId;

  @Column(name = "cargo_category")
  private int cargoCategoryId;

  @Column(name = "excess_pcs")
  private int excessPcs;

  @Column(name = "short_landed_pcs")
  private int shortLandedPcs;

  @Column(name = "cargo_class")
  private int cargoClassId;

  @Column(name = "cargo_status")
  private int cargoStatusId;

  @Column(name = "inbound_status")
  private int inboundStatus;

  @Column(name = "booked_pcs")
  int bookedPcs;

}
