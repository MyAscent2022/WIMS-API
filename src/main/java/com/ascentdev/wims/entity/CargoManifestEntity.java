/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;
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
@Table(name="txn_cargo_manifest", schema="manifest")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CargoManifestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;
  
  @Column(name="location_no")
  Long locationNo;
  
  @Column(name="actual_pieces")
  Long actualPieces;
  
  Float weight;
  
  Float length;
  
  Float width;
  
  Float height;
  
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
  
  @Column(name="mawb_number")
  String mawbNumber;
  
  @Column(name="hawb_number")
  String hawbNumber;
  
  @Column(name="inbound_status")
  String inboundStatus;
  
  String commodity;
  
  @Column(name="created_at")
  Timestamp createdAt;
  
  @Column(name="date_of_arrival")
  Timestamp dateOfArrival;
  
  @Column(name="imp_id")
  String impId;
  
  @Column(name="modified_at")
  Timestamp modifiedAt;
  
  @Column(name="registry_number")
  String registryNumber;
  
  @Column(name="uld_no")
  String uldNo;
  
  @Column(name="uld_type_id")
  String uldTypeId;
  
  @Column(name="created_by_id")
  Long createdById;
  
  @Column(name="modified_by_id")
  Long modifiedById;
  
  @Column(name="flight_number")
  String flightNumber;
}
