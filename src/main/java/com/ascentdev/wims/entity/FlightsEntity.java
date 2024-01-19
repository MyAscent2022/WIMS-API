/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import java.sql.Date;
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
@Subselect("SELECT f.id,\n"
        + "ja.assigned_user_id AS user_id,\n"
        + "ra.id AS airline_id,\n"
        + "ra.description AS airline,\n"
        + "f.flight_number,\n"
        + "f.registry_number,\n"
        + "f.travel_status,\n"
        + "f.flight_status,\n"
        + "f.estimated_arrival_dt::DATE AS date_of_arrival\n"
        + "FROM flights f\n"
        + "INNER JOIN job_assignments ja ON ja.flight_id = f.id\n"
        + "INNER JOIN ref_airline ra ON ra.code = f.ref_airline_code\n"
        + "WHERE f.travel_status = 'Done'")
public class FlightsEntity {

  @Id
  long id;

  @Column(name = "user_id")
  long userId;

  @Column(name = "airline_id")
  Long airlineId;

  @Column(name = "airline")
  String airline;

  @Column(name = "flight_number")
  String flightNumber;

  @Column(name = "registry_number")
  String registryNumber;

  @Column(name = "travel_status")
  String travelStatus;

  @Column(name = "flight_status")
  String flightStatus;

  @Column(name = "date_of_arrival")
  Date arrivalDate;
}
