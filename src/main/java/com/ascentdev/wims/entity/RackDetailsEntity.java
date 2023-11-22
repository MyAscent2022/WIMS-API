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
@Subselect("SELECT m.id, m.flight_number, m.mawb_number, h.hawb_number, a.weight, cc.classdesc, CONCAT(up.firstname, ' ', up.lastname) AS storage_personnel FROM public.txn_acceptance a\n"
        + "INNER JOIN manifest.txn_mawb m ON m.id = a.txn_mawb_id\n"
        + "INNER JOIN manifest.txn_hawb h ON h.id = a.txn_hawb_id\n"
        + "INNER JOIN refs.ref_cargo_class cc ON cc.id = a.cargo_class\n"
        + "INNER JOIN public.txn_storage_logs sl ON sl.txn_mawb_id = m.id\n"
        + "INNER JOIN  commons.user_profile up ON CAST(up.user_id AS TEXT) LIKE '%' || sl.user_id || '%'")
public class RackDetailsEntity {
  @Id
  int id;
  
  @Column(name="flight_number")
  String flightNumber;
  
  @Column(name="mawb_number")
  String mawbNumber;
  
  @Column(name="hawb_number")
  String hawbNumber;
  
  @Column(name="weight")
  int weight;
  
  @Column(name="classdesc")
  String classdesc;
  
  @Column(name="storage_personnel")
  String storagePersonnel;
}
