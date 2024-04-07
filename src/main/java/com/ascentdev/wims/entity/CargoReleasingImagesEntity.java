/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author User
 */
@Data
@Entity
@Table(name = "txn_cargo_releasing_images")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoReleasingImagesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  
  @Column(name="txn_cargo_releasing_id")
  long txnCargoReleasingId;
  @Column(name="filename")
  String filename;
  @Column(name="filepath")
  String filepath;

}
