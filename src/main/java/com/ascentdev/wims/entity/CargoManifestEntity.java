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
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
@Entity
@Table(name="cargo_manifest", schema="manifest")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoManifestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  
  @Column(name="txn_mawb_id")
  Long txnMawbId;
  
  @Column(name="location_no")
  Long locationNo;
  
  @Column(name="actual_pieces")
  Long actualPieces;
  
  float weight;
  
  float length;
  
  float width;
  
  float height;
  
  @Column(name="cargo_category_id")
  Long cargoCategoryId;
  
  @Column(name="excess_pieces")
  Long excessPieces;
  
  @Column(name="short_landed_pieces")
  Long shortLandedPieces;
  
  @Column(name="cargo_class_id")
  Long cargoClassId;
  
  @Column(name="cargo_status_id")
  Long cargoStatusId;
}
