/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Subselect;

/**
 *
 * @author ASCENT
 */
@Data
@Entity
@Subselect("SELECT cal.id,\n"
        + "cal.actual_item_count, \n"
        + "m.mawb_number, \n"
        + "tru.id AS rack_util_id, \n"
        + "rr.rack_name,\n"
        + "rr.layer_name,\n"
        + "f.flight_number, \n"
        + "h.hawb_number,\n"
        + "cc.classdesc, \n"
        + "m.cargo_status\n"
        + "FROM public.cargo_activity_logs cal\n"
        + "INNER JOIN public.txn_mawb m ON m.id = cal.mawb_id\n"
        + "INNER JOIN public.txn_rack_utilization tru ON tru.txn_mawb_id = cal.mawb_id\n"
        + "INNER JOIN public.ref_rack rr ON rr.id = tru.ref_rack_id\n"
        + "INNER JOIN public.flights f ON f.id = cal.flight_id\n"
        + "LEFT JOIN public.txn_hawb h ON h.id = cal.hawb_id\n"
        + "INNER JOIN public.ref_cargo_class cc ON cc.id = m.cargo_class_id\n"
        + "WHERE cal.activity_status = 'STORING'")
public class StorageCargoEntity {

  @Id
  int id;

  @Column(name = "actual_item_count")
  int actualPcs;

  @Column(name = "mawb_number")
  String mawbNumber;

  @Column(name = "rack_util_id")
  int rackUtilId;
  
  @Column(name = "rack_name")
  String rackName;
  
  @Column(name = "layer_name")
  String layerName;

  @Column(name = "flight_number")
  String flightNumber;

  @Column(name = "hawb_number")
  String hawbNumber;

  @Column(name = "classdesc")
  String classDesc;

  @Column(name = "cargo_status")
  String cargoStatus;
}
