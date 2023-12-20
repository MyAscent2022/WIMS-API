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
 * @author
 * ASCENT
 */
@Data
@Entity
@Subselect("SELECT tm.id, \n"
        + "tm.mawb_number, \n"
        + "th.hawb_number, \n"
        + "cal.actual_pcs, \n"
        + "rs.release_status, \n"
        + "cal.payment_datetime, \n"
        + "cal.location,\n"
        + "r.rack_name, \n"
        + "r.layer_name\n"
        + "FROM public.cargo_activity_logs cal\n"
        + "INNER JOIN public.txn_mawb tm ON tm.id = cal.mawb_id\n"
        + "LEFT JOIN public.txn_hawb th ON th.id = cal.hawb_id\n"
        + "LEFT JOIN public.ref_release_status rs ON rs.id = cal.released_type_id\n"
        + "INNER JOIN public.txn_rack_utilization tru ON tru.txn_mawb_id = cal.mawb_id\n"
        + "LEFT JOIN public.ref_rack r ON r.id = tru.ref_rack_id\n"
        + "WHERE NOT cal.payment_datetime IS NULL \n"
        + "AND cal.location = 'RELEASING' \n"
        + "AND cal.released_datetime IS NULL")
public class ReleasingCargoEntity {

  @Id
  long id;

  @Column(name = "mawb_number")
  String mawbNumber;

  @Column(name = "hawb_number")
  String hawbNumber;

  @Column(name = "actual_pcs")
  int actualPcs;

  @Column(name = "location")
  String location;

  @Column(name = "release_status")
  String releaseStatus;

  @Column(name = "payment_datetime")
  String paidDt;

  @Column(name = "rack_name")
  String rackName;

  @Column(name = "layer_name")
  String layerName;

}
