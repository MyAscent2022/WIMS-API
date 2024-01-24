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
@Subselect("SELECT tm.id, \n"
        + "cal.actual_item_count, \n"
        + "tm.mawb_number, \n"
        + "th.hawb_number, \n"
        + "f.flight_number, \n"
        + "cc.classdesc,\n"
        + "tm.actual_weight,\n"
        + "rr.rack_name, \n"
        + "rr.layer_name\n"
        + "FROM public.cargo_activity_logs cal\n"
        + "INNER JOIN public.txn_mawb tm ON tm.id = cal.mawb_id\n"
        + "LEFT JOIN public.txn_hawb th ON th.id = cal.hawb_id\n"
        + "INNER JOIN public.flights f ON f.id = cal.flight_id\n"
        + "INNER JOIN public.ref_cargo_class cc ON cc.id = tm.cargo_class_id\n"
        + "INNER JOIN public.txn_rack_utilization tru ON tru.txn_mawb_id = cal.mawb_id\n"
        + "INNER JOIN public.ref_rack rr ON rr.id = tru.ref_rack_idF")
public class RackDetailsEntity {

  @Id
  int id;

  @Column(name = "flight_number")
  String flightNumber;

  @Column(name = "mawb_number")
  String mawbNumber;

  @Column(name = "hawb_number")
  String hawbNumber;

  @Column(name = "actual_weight")
  int weight;

  @Column(name = "actual_item_count")
  int actualPcs;

  @Column(name = "classdesc")
  String classdesc;

//  @Column(name = "storage_personnel")
//  String storagePersonnel;
  @Column(name = "rack_name")
  String rackName;

  @Column(name = "layer_name")
  String layerName;

}
