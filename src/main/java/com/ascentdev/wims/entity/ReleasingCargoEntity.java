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
@Subselect("SELECT m.id, m.mawb_number, h.hawb_number, a.actual_pcs, rs.release_status, cr.paid_dt, r.rack_name, r.layer_name, cr.status FROM public.txn_rack_utilization ru\n"
        + "INNER JOIN manifest.txn_mawb m ON ru.txn_mawb_id = m.id\n"
        + "INNER JOIN manifest.txn_hawb h ON ru.txn_hawb_id = h.id\n"
        + "INNER JOIN public.txn_acceptance a ON a.txn_mawb_id = ru.txn_mawb_id\n"
        + "INNER JOIN public.txn_cargo_release cr ON m.mawb_number = cr.mawb_number\n"
        + "INNER JOIN refs.ref_release_status rs ON rs.id = cr.released_type_id\n"
        + "INNER JOIN refs.ref_rack r ON r.id = ru.ref_rack_id")
public class ReleasingCargoEntity {
  @Id
  long id;
  
  @Column(name="mawb_number")
  String mawbNumber;
  
  @Column(name="hawb_number")
  String hawbNumber;
  
  @Column(name="actual_pcs")
  int actualPcs;
  
  @Column(name="release_status")
  String releaseStatus;
  
  @Column(name="paid_dt")
  Timestamp paidDt;
  
  @Column(name="rack_name")
  String rackName;
  
  @Column(name="status")
  String status;
  
  @Column(name="layer_name")
  String layerName;
}
