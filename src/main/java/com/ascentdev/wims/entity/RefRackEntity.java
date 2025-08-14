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
 * @author ASCENT SOLUTIONS INC
 */
@Data
@Entity
@Table(name="ref_rack")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RefRackEntity {
  @Id
  long id;
  
  @Column(name="ref_airline_id")
  Long refAirlineId;
  
  @Column(name="ref_category_id")
  int refCategoryId;
  
  @Column(name="rack_name")
  String rackName;
  
  @Column(name="dimension")
  String dimension;
  
  @Column(name="coordinate")
  String coordinate;
  
  @Column(name="max_volume")
  Float maxVolume;
  
  @Column(name="rack_code")
  String rackCode;
  
  @Column(name="ref_classification_id")
  int refClassificationId;
  
  @Column(name="capacity")
  Float capacity;
  
  @Column(name="height")
  Float height;
}