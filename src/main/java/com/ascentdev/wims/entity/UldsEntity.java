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
@Subselect("SELECT ru.id, \n"
        + "ru.uld_no,\n"
        + "ru.flight_number,\n"
        + "tu.mawb_number,\n"
        + "COUNT(DISTINCT tu.mawb_number) AS mawb_count,\n"
        + "COUNT(DISTINCT th.hawb_number) AS hawb_count,\n"
        + "ru.uld_type,\n"
        + "ut.type, \n"
        + "tu.id AS uld_id,\n"
        + "rus.status AS uld_status\n"
        + "FROM ref_uld ru\n"
        + "INNER JOIN ref_uld_type ut ON ut.id = ru.uld_type\n"
        + "INNER JOIN txn_ulds tu ON tu.uld_number = ru.uld_no\n"
        + "INNER JOIN ref_uld_status rus ON rus.id = ru.uld_status\n"
        + "INNER JOIN txn_mawb tm ON tm.mawb_number = tu.mawb_number\n"
        + "LEFT JOIN txn_hawb th ON th.mawb_number = tm.mawb_number\n"
        + "WHERE ru.uld_status IN (2, 10, 11)\n"
        + "GROUP BY ru.id, ut.type, tu.id, rus.status, tu.mawb_number")
public class UldsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "uld_id")
  int uldId;

  @Column(name = "uld_no")
  String uldNumber;

  @Column(name = "flight_number")
  String flightNumber;

  @Column(name = "mawb_number")
  String mawbNumber;

  @Column(name = "uld_type")
  long uldTypeId;

//  @Column(name = "total_expected")
//  int totalExpected;
  @Column(name = "type")
  String type;

  @Column(name = "mawb_count")
  int totalMawb;

  @Column(name = "hawb_count")
  int totalHawb;

  @Column(name = "uld_status")
  String uldStatus;
}
