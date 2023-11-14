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
@Table(name="txn_cargo_manifest_details", schema="manifest")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoManifestDetailsEntity {
  @Id
  long id;
  
  @Column(name="txn_cargo_manifest_id")
  long txnCargoManifestId;
  
  @Column(name="hawb_number")
  String hawbNumber;
  
  Long pieces;
  
  Long weight;
}
