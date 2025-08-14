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
@Subselect("WITH RankedFlights AS (\n"
        + "    SELECT \n"
        + "        f.id,\n"
        + "        ra.id AS airline_id,\n"
        + "        ra.description AS airline,\n"
        + "        f.flight_number,\n"
        + "        f.travel_status,\n"
        + "        f.flight_status,\n"
        + "        f.estimated_arrival_dt::DATE AS date_of_arrival,\n"
        + "        f.destination_code,\n"
        + "        f.origin_code,\n"
        + "        fs.registry_number,\n"
        + "        ROW_NUMBER() OVER (PARTITION BY f.flight_number ORDER BY f.estimated_arrival_dt DESC) AS rn\n"
        + "    FROM \n"
        + "        flights f\n"
        + "    INNER JOIN \n"
        + "        ref_airline ra ON ra.code = f.ref_airline_code\n"
        + "    INNER JOIN \n"
        + "        flight_schedules fs ON fs.id = f.flight_schedule_id\n"
        + "    INNER JOIN \n"
        + "        ref_uld ru ON ru.flight_number = f.flight_number\n"
        + "    WHERE \n"
        + "        f.estimated_arrival_dt::DATE IN (CURRENT_DATE, CURRENT_DATE + INTERVAL '1 day')\n"
        + "        AND ru.uld_status IN (2, 11)\n"
        + ")\n"
        + "SELECT \n"
        + "    id, airline_id, airline, flight_number, travel_status, flight_status, date_of_arrival, destination_code, origin_code, registry_number\n"
        + "FROM \n"
        + "    RankedFlights\n"
        + "WHERE \n"
        + "    rn = 1")
public class FlightsEntity {

  @Id
  int id;

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

  @Column(name = "destination_code")
  String destinationCode;

  @Column(name = "origin_code")
  String originCode;
}
