/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import java.sql.Timestamp;
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
@Subselect("SELECT tm.id,\n"
        + "tm.mawb_number,\n"
        + "th.hawb_number,\n"
        + "cal.actual_item_count,\n"
        + "sp.datepaid,\n"
        + "cal.location,\n"
        + "r.rack_name,\n"
        + "r.layer_name\n"
        + "FROM cargo_activity_logs cal\n"
        + "INNER JOIN txn_mawb tm ON tm.id = cal.mawb_id\n"
        + "INNER JOIN txn_hawb th ON th.id = cal.hawb_id\n"
        + "INNER JOIN txn_rack_utilization tru ON tru.txn_mawb_id = cal.mawb_id\n"
        + "INNER JOIN storage_payments sp ON sp.mawb_id = cal.mawb_id\n"
        + "LEFT JOIN ref_rack r ON r.id = tru.ref_rack_id\n"
        + "WHERE cal.location = 'RELEASING'\n"
        + "AND sp.datepaid IS NOT NULL")
public class ReleasingCargoEntity {

  @Id
  long id;

  @Column(name = "mawb_number")
  String mawbNumber;

  @Column(name = "hawb_number")
  String hawbNumber;

  @Column(name = "actual_item_count")
  int actualPcs;

  @Column(name = "location")
  String location;

//  @Column(name = "release_status")
//  String releaseStatus;

  @Column(name = "datepaid")
  String paidDt;

  @Column(name = "rack_name")
  String rackName;

  @Column(name = "layer_name")
  String layerName;

}
