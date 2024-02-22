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
@Subselect("SELECT u.id,\n"
        + "u.uld_number,\n"
        + "u.total_count,\n"
        + "u.flight_number,\n"
        + "u.uld_type,\n"
        + "u.total_expected,\n"
        + "ut.type,\n"
        + "u.total_mawb\n"
        + "FROM txn_ulds u\n"
        + "INNER JOIN ref_uld_container_type ut ON ut.id = u.uld_type")
public class UldsEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "uld_number")
  String uldNumber;

  @Column(name = "total_count")
  int totalCount;

  @Column(name = "flight_number")
  String flightNumber;

  @Column(name = "uld_type")
  long uldTypeId;

  @Column(name = "total_expected")
  int totalExpected;

  @Column(name = "type")
  String type;
  
  @Column(name = "total_mawb")
  int totalMawb;
}
