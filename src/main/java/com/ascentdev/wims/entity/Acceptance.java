/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
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
@Table(name = "txn_acceptance")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Acceptance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "actual_pcs")
  private int actualPcs;

  @Column(name = "weight")
  private int weight;

  @Column(name = "length")
  private int length;

  @Column(name = "width")
  private int width;

  @Column(name = "height")
  private int height;

  @Column(name = "txn_mawb_id")
  private int txnMawbId;

  @Column(name = "txn_hawb_id")
  private int txnHawbId;

  @Column(name = "cargo_category")
  private int cargoCategory;

  @Column(name = "excess_pcs")
  private int excessPcs;

  @Column(name = "short_landed_pcs")
  private int shortLandedPcs;

  @Column(name = "cargo_class")
  private int cargoClass;

  @Column(name = "cargo_status")
  private int cargoStatus;

}
