/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Subselect;

/**
 *
 * @author
 * ASCENT
 */
@Data
@Entity
@Subselect("SELECT tm.id, tm.uld_number, rut.type, tm.gross_mass, rut.id AS uld_container_type_id, f.flight_number FROM public.txn_mawb tm\n"
        + "INNER JOIN public.ref_uld_container_type rut ON rut.id = tm.uld_container_type_id\n"
        + "INNER JOIN public.flights f ON f.id = tm.flight_id")
public class RefULDEntity {

  @Id
  long id;

  @Column(name = "uld_number")
  String uldNo;

  @Column(name = "uld_container_type_id")
  long uldTypeId;

  @Column(name = "type")
  String uldType;

  @Column(name = "flight_number")
  String flightNumber;

  @Column(name = "gross_mass")
  int totalWeight;

}
