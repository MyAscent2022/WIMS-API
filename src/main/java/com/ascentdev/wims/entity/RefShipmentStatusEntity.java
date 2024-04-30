/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author ASCENT SOLUTIONS INC
 */
@Data
@Entity
@Table(name = "ref_shipment_status")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RefShipmentStatusEntity {
  
  @Id
  int id;
  String name;
  String code;
  
}
