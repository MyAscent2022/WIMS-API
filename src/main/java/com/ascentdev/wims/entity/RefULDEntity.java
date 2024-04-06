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
 * @author ASCENT
 */
@Data
@Entity
@Table(name = "ref_uld")
public class RefULDEntity {

  @Id
  long id;

  @Column(name = "uld_no")
  String uldNo;

  @Column(name = "flight_number")
  String flightNumber;
  
  @Column(name = "uld_status")
  int uldStatus;

}
