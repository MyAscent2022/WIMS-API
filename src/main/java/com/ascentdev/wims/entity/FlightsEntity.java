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
@Subselect("WITH rellogs as (SELECT unnest (string_to_array(user_id, ',')) as rel_handler, * FROM public.txn_receiving_logs)\n"
        + "SELECT DISTINCT (rel.*), f.travel_status, f.flight_status, ra.description FROM rellogs rel\n"
        + "INNER JOIN public.flights f ON f.flight_number = rel.flight_number\n"
        + "INNER JOIN refs.ref_airline ra ON ra.code = f.ref_airline_code\n"
        + "LEFT JOIN manifest.txn_mawb m ON m.flight_number = rel.flight_number")
public class FlightsEntity {

  @Id
  long id;

  @Column(name = "user_id")
  String userId;

  @Column(name = "flight_id")
  Long flightId;

  String status;

  @Column(name = "cargo_status")
  String cargoStatus;

  @Column(name = "created_at")
  Timestamp createdAt;

  @Column(name = "created_by_id")
  Long createdById;

  @Column(name = "modified_at")
  Timestamp modifiedAt;

  @Column(name = "modified_by_id")
  Long modifiedById;

  @Column(name = "uld_id")
  Long uld_id;

  @Column(name = "mawb_number")
  String mawbNumber;

  @Column(name = "flight_number")
  String flightNumber;

  @Column(name = "registry_number")
  String registryNumber;

  @Column(name = "travel_status")
  String travelStatus;

  @Column(name = "flight_status")
  String flightStatus;
}
