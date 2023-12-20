/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author
 * ASCENT
 */
@Data
@Entity
@Table(name = "ref_cargo_condition", schema = "public")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoConditionEntity {

  @Id
  long id;

  @Column(name = "condition")
  String condition;

}
