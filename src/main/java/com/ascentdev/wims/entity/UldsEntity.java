/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
@Entity
@Table(name="txn_ulds")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UldsEntity {
  @Id
  long id;
  
  @Column(name="uld_no")
  String uldNo;
  
  @Column(name="uld_type")
  long uldType;
  
  @Column(name="flight_number")
  String flightNumber;
  
  @Column(name="total_count")
  Long totalCount;
  
  @Column(name="short_landed")
  Long shortLanded;
  
  @Column(name="total_expected")
  Long totalExpected;
}
