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
@Subselect("SELECT DISTINCT rrl.rack_id, rr.rack_code, rrl.rack_name\n"
        + "FROM ref_rack_layer rrl\n"
        + "INNER JOIN ref_rack rr ON rr.id = rrl.rack_id\n"
        + "INNER JOIN rack_slot_inventories rsi ON rsi.ref_rack_id = rr.id\n"
        + "ORDER BY rack_code")
public class GetRacksEntity {

  @Id
  @Column(name = "rack_id")
  int rackId;

  @Column(name = "rack_code")
  String rackCode;

  @Column(name = "rack_name")
  String rackName;
}
