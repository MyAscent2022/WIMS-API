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
@Subselect("SELECT ul.id,\n"
        + "ru.id AS uld_id,\n"
        + "ru.uld_no,\n"
        + "ru.uld_type,\n"
        + "ul.flight_id,\n"
        + "f.flight_number,\n"
        + "ru.tare_weight\n"
        + "FROM uld_activity_logs ul\n"
        + "INNER JOIN ref_uld ru ON ru.id = ul.uld_id\n"
        + "INNER JOIN flights f ON f.id = ul.flight_id")
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
