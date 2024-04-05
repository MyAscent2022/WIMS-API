/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Subselect;

/**
 *
 * @author ASCENT SOLUTIONS INC
 */
@Data
@Entity
@Subselect("SELECT DISTINCT rack_id, rack_name\n"
        + "FROM ref_rack\n"
        + "ORDER BY rack_id ASC")
public class GetRacksEntity {
  
  @Id
  @Column(name = "rack_id")
  int rackId;
  
  @Column(name = "rack_name")
  String rackName;    
}
