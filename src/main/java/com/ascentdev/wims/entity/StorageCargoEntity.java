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
//@Subselect("SELECT cal.id, cal.uld_id, cal.mawb_id,\n"
//        + "       cal.actual_item_count, \n"
//        + "       m.mawb_number,\n"
//        + "       f.flight_number, \n"
//        + "       h.hawb_number,\n"
//        + "       cc.classdesc,\n"
//        + "       m.cargo_status,\n"
//        + "       cal.ref_shipment_status_code,\n"
//        + "       cal.actual_item_count AS rcv_pcs,\n"
//        + "       m.gross_mass AS total_weight,\n"
//        + "       m.consignee_name,\n"
//        + "       m.registry_number,\n"
//        + "       m.cargo_class_id\n"
//        + "FROM cargo_activity_logs cal\n"
//        + "INNER JOIN txn_mawb m ON m.id = cal.mawb_id\n"
//        + "LEFT JOIN txn_hawb h ON h.id = cal.hawb_id\n"
//        + "INNER JOIN flights f ON f.id = cal.flight_id\n"
//        + "INNER JOIN ref_cargo_class cc ON cc.id = m.cargo_class_id OR cc.id = h.cargo_class_id\n"
//        + "WHERE cal.activity_status IN ('RECEIVED', 'BAD ORDER')\n"
//        + "  AND NOT cal.ref_shipment_status_code = 'OH'\n"
//        + "  AND cal.is_stored = 'f'\n"
//        + "  AND (\n"
//        + "      (h.hawb_number IS NULL AND NOT m.cargo_class_id IN (9,10)) OR \n"
//        + "      (h.hawb_number IS NOT NULL AND NOT h.cargo_class_id IN (9,10))\n"
//        + "  )")

@Subselect("SELECT cal.id, cal.uld_id, cal.mawb_id,\n"
        + "       cal.actual_item_count, \n"
        + "       m.mawb_number,\n"
        + "       f.flight_number, \n"
        + "       h.hawb_number,\n"
        + "       cc.classdesc,\n"
        + "       m.cargo_status,\n"
        + "       cal.ref_shipment_status_code,\n"
        + "       cal.actual_item_count AS rcv_pcs,\n"
        + "       m.gross_mass AS total_weight,\n"
        + "       m.consignee_name,\n"
        + "       m.registry_number,\n"
        + "       m.cargo_class_id\n"
        + "FROM cargo_activity_logs cal\n"
        + "INNER JOIN txn_mawb m ON m.id = cal.mawb_id\n"
        + "LEFT JOIN txn_hawb h ON h.id = cal.hawb_id\n"
        + "INNER JOIN flights f ON f.id = cal.flight_id\n"
        + "INNER JOIN ref_cargo_class cc ON cc.id = m.cargo_class_id OR cc.id = h.cargo_class_id\n"
        + "WHERE cal.activity_status IN ('RECEIVED', 'BAD ORDER')\n"
        + "  AND NOT cal.ref_shipment_status_code = 'OH'\n"
        + "  AND cal.is_stored = 'f'")
public class StorageCargoEntity {

  @Id
  int id;

  @Column(name = "actual_item_count")
  int actualPcs;

  @Column(name = "mawb_id")
  int mawbId;

  @Column(name = "mawb_number")
  String mawbNumber;

  @Column(name = "flight_number")
  String flightNumber;

  @Column(name = "hawb_number")
  String hawbNumber;

  @Column(name = "classdesc")
  String classDesc;

  @Column(name = "cargo_status")
  String cargoStatus;

  @Column(name = "ref_shipment_status_code")
  String statusCode;

  @Column(name = "rcv_pcs")
  int rcvPcs;

  @Column(name = "total_weight")
  int totalWeight;

  @Column(name = "uld_id")
  Integer uldId;

  @Column(name = "consignee_name")
  String consigneeName;

  @Column(name = "registry_number")
  String registryNumber;
}
