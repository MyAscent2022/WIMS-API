/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
@Entity
@Table(name="ref_cargo_class", schema="refs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoClassEntity {
  @Id
  long id;
  
  @Column(name="ref_cargo_class")
  String refCargoClass;
  
  @Column(name="classdesc")
  String classdesc;
  
  @Column(name="storagefee")
  Float storagefee;
  
  @Column(name="minrate")
  Float minrate;
}
