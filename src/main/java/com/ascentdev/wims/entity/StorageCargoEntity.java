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
@Subselect("SELECT m.id, m.flight_number, m.mawb_number, h.hawb_number, cc.classdesc, h.number_of_packages, a.actual_pcs, a.cargo_status FROM public.txn_acceptance a\n"
        + "INNER JOIN manifest.txn_mawb m ON m.id = a.txn_mawb_id\n"
        + "INNER JOIN manifest.txn_hawb h ON h.id = a.txn_hawb_id\n"
        + "INNER JOIN refs.ref_cargo_class cc ON cc.id = a.cargo_class")
public class StorageCargoEntity {

  @Column(name = "flight_number")
  String flightNumber;

  @Column(name = "mawb_number")
  String mawbNumber;

  @Column(name = "hawb_number")
  String hawbNumber;

  @Column(name = "classdesc")
  String classdesc;

  @Column(name = "number_of_packages")
  int numberOfPackages;

  @Column(name = "cargo_status")
  int cargoStatus;

  @Column(name = "actual_pcs")
  int actualPcs;

  @Id
  int id;
}
