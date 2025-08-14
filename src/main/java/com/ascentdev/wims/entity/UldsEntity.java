/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Subselect;

/**
 *
 * @author ASCENT
 */
@Data
@Entity
@Subselect("SELECT ru.id,\n"
        + "ru.uld_no,\n"
        + "ru.flight_number,\n"
        + "tm.registry_number,\n"
        + "ru.uld_type,\n"
        + "rcut.description AS unit_type,\n"
        + "rut.type,\n"
        + "rus.status AS uld_status,\n"
        + "ru.is_uld,\n"
        + "COUNT(DISTINCT tm.id) AS mawb_count,\n"
        + "COUNT(DISTINCT th.id) AS hawb_count\n"
        + "FROM ref_uld ru\n"
        + "INNER JOIN flights f ON f.flight_number = ru.flight_number\n"
        + "LEFT JOIN ref_uld_type rut ON rut.id = ru.uld_type\n"
        + "LEFT JOIN ref_cargo_unit_type rcut ON rcut.id = ru.unit_type_id\n"
        + "INNER JOIN ref_uld_status rus ON rus.id = ru.uld_status\n"
        + "INNER JOIN txn_ulds tu ON tu.uld_number = ru.uld_no\n"
        + "LEFT JOIN txn_mawb tm ON tm.id = tu.mawb_id\n"
        + "LEFT JOIN txn_hawb th ON th.txn_mawb_id = tm.id\n"
        + "WHERE ru.uld_status IN (2,11)\n"
        + "AND tu.is_received = false\n"
        + "GROUP BY ru.id,\n"
        + "    ru.uld_no, \n"
        + "    ru.flight_number, \n"
        + "    ru.uld_type, \n"
        + "    rut.type, \n"
        + "    rus.status,\n"
        + "		tm.registry_number,\n"
        + "		rcut.description,\n"
        + "   ru.is_uld")

//@Subselect("		SELECT \n"
//        + "    ru.id, \n"
//        + "    tm.id AS mawb_id, \n"
//        + "    th.id AS hawb_id,\n"
//        + "    ru.uld_no,\n"
//        + "    ru.flight_number,\n"
//        + "    ru.uld_type,\n"
//        + "    rut.type,\n"
//        + "    rus.status AS uld_status,\n"
//        + "    COUNT(DISTINCT tm.id) AS mawb_count,\n"
//        + "    COUNT(DISTINCT th.id) AS hawb_count,\n"
//        + "    CASE \n"
//        + "        WHEN COUNT(*) OVER (PARTITION BY tm.id, th.id) > 1 THEN 'partial'\n"
//        + "        ELSE 'full'\n"
//        + "    END AS tag\n"
//        + "FROM ref_uld ru\n"
//        + "INNER JOIN ref_uld_type rut ON rut.id = ru.uld_type\n"
//        + "INNER JOIN ref_uld_status rus ON rus.id = ru.uld_status\n"
//        + "INNER JOIN txn_ulds tu ON tu.uld_number = ru.uld_no\n"
//        + "LEFT JOIN txn_mawb tm ON tm.mawb_number = tu.mawb_number\n"
//        + "LEFT JOIN txn_hawb th ON th.txn_mawb_id = tm.id\n"
//        + "WHERE ru.uld_status IN (2,11)\n"
//        + "AND tu.is_received = false\n"
//        + "GROUP BY \n"
//        + "    ru.id,\n"
//        + "    ru.uld_no, \n"
//        + "    ru.flight_number, \n"
//        + "    ru.uld_type, \n"
//        + "    rut.type, \n"
//        + "    rus.status,\n"
//        + "    tm.id, \n"
//        + "    th.id")
public class UldsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "uld_no")
  String uldNumber;

  @Column(name = "flight_number")
  String flightNumber;

  @Column(name = "uld_type")
  Long uldTypeId;

  @Column(name = "type")
  String type;

  @Column(name = "mawb_count")
  int totalMawb;

  @Column(name = "hawb_count")
  int totalHawb;

  @Column(name = "uld_status")
  String uldStatus;

  @Column(name = "registry_number")
  String registryNumber;

  @Column(name = "unit_type")
  String unitType;
  
  @Column(name = "is_uld")
  boolean isUld;

}
