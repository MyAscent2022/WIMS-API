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
@Table(name="ref_rack", schema="refs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RefRackEntity {
  @Id
  long id;
  
  @Column(name="layer_id")
  long layerId;
  
  @Column(name="max_volume")
  float maxVolume;
  
  @Column(name="layer_name")
  String layerName;
  
  @Column(name="volume")
  Float volume;
  
  @Column(name="layout_column")
  Long layoutColumn;
  
  @Column(name="layout_row")
  Long layoutRow;
  
  @Column(name="rack_id")
  long rackId;
  
  @Column(name="rack_name")
  String rackName;
}
