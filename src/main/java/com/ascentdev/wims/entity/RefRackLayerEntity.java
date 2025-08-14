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
import org.hibernate.annotations.Subselect;

/**
 *
 * @author ASCENT
 */
@Data
@Entity
@Subselect("SELECT id, layer_id, max_volume, \n"
        + "layer_name, volume, \n"
        + "dimension, layout_column, \n"
        + "layout_row, rack_id, \n"
        + "rack_name, ref_airline_id \n"
        + "FROM ref_rack_layer\n"
        + "ORDER BY layer_id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RefRackLayerEntity {

  @Id
  long id;

  @Column(name = "layer_id")
  long layerId;

  @Column(name = "max_volume")
  Float maxVolume;

  @Column(name = "layer_name")
  String layerName;

  @Column(name = "volume")
  Float volume;

  @Column(name = "dimension")
  String dimension;

  @Column(name = "layout_column")
  Long layoutColumn;

  @Column(name = "layout_row")
  Long layoutRow;

  @Column(name = "rack_id")
  long rackId;

  @Column(name = "rack_name")
  String rackName;

  @Column(name = "ref_airline_id")
  Long refAirlineId;
}
