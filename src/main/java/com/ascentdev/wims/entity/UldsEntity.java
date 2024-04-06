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
        + "ru.uld_type,\n"
        + "ut.type, \n"
        + "tu.id AS uld_id, \n"
        + "tu.total_mawb,\n"
        + "tu.total_count,\n"
        + "tu.total_expected,\n"
        + "tu.uld_status\n"
        + "FROM ref_uld ru\n"
        + "INNER JOIN ref_uld_container_type ut ON ut.id = ru.uld_type\n"
        + "INNER JOIN txn_ulds tu ON tu.uld_number = ru.uld_no")
public class UldsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  
  @Column(name = "uld_id")
  int uldId;

  @Column(name = "uld_no")
  String uldNumber;

  @Column(name = "total_count")
  int totalCount;

  @Column(name = "flight_number")
  String flightNumber;
  
  @Column(name = "mawb_number")
  String mawbNumber;

  @Column(name = "uld_type")
  long uldTypeId;

  @Column(name = "total_expected")
  int totalExpected;

  @Column(name = "type")
  String type;

  @Column(name = "total_mawb")
  int totalMawb;

  @Column(name = "uld_status")
  int uldStatus;
}
