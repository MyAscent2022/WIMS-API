/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
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
@Table(name="txn_mawb", schema="manifest")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MawbEntity {
  @Id
  long id;
  
  @Column(name="bol_nature")
  long bolNature;
  
  @Column(name="bol_type")
  String bolType;
  
  @Column(name="carrier_reference")
  String carrierReference;
  
  @Column(name="consignee_address")
  String consigneeAddress;
  
  @Column(name="consignee_code")
  String consigneeCode;
  
  @Column(name="consignee_name")
  String consigneeName;
  
  @Column(name="created_at")
  Timestamp createdAt;
  
  @Column(name="custom_currency")
  String customCurrency;
  
  @Column(name="custom_value")
  float customValue;
  
  @Column(name="customs_office")
  String customsOffice;
  
  @Column(name="date_of_arrival")
  Date dateOfArrival;
  
  @Column(name="destination_code")
  String destinationCode;
  
  @Column(name="exporter_code")
  String exporterCode;
  
  @Column(name="exporter_name")
  String exporterName;
  
  @Column(name="flight_number")
  String flightNumber;
  
  @Column(name="freight_currency")
  String freightCurrency;
  
  @Column(name="freight_indicator")
  String freightIndicator;
  
  @Column(name="freight_value")
  float freightValue;
  
  @Column(name="goods_description")
  String goodsDescription;
  
  @Column(name="gross_mass")
  float grossMass;
  
  @Column(name="hawb_number")
  String hawbNumber;
  
  @Column(name="information_part_a")
  String informationPartA;
  
  @Column(name="line_number")
  long lineNumber;
  
  @Column(name="marks_of_seals")
  String marksOfSeals;
  
  @Column(name="mawb_no")
  String mawbNo;
  
  @Column(name="mawb_number")
  String mawbNumber;
  
  @Column(name="modified_at")
  Timestamp modifiedAt;
  
  @Column(name="notify_party_address")
  String notifyPartyAddress;
  
  @Column(name="notify_party_code")
  String notifyPartyCode;
  
  @Column(name="notify_party_name")
  String notifyPartyName;
  
  @Column(name="number_of_containers")
  long numberOfContainers;
  
  @Column(name="number_of_packages")
  String numberOfPackages;
  
  @Column(name="number_of_seals")
  long numberOfSeals;
  
  @Column(name="operation_location")
  String operationLocation;
  
  @Column(name="operation_location_information")
  String operationLocationInformation;
  
  @Column(name="origin_code")
  String originCode;
  
  @Column(name="package_type_code")
  String packageTypeCode;
  
  @Column(name="place_of_loading")
  String placeOfLoading;
  
  @Column(name="place_of_unloading")
  String placeOfUnloading;
  
  @Column(name="registry_number")
  String registryNumber;
  
  @Column(name="sealing_party_code")
  String sealingPartyCode;
  
  @Column(name="shipping_marks")
  String shippingMarks;
  
  @Column(name="time_of_arrival")
  Time timeOfArrival;
  
  @Column(name="transport_currency")
  String transportCurrency;
  
  @Column(name="transport_value")
  float transportValue;
  
  float volume;
  
  @Column(name="carrier_code")
  String carrierCode;
  
  @Column(name="entry_number")
  String entryNumber;
  
  @Column(name="flight_date")
  String flightDate;
  
  @Column(name="gross_tonnage")
  String grossTonnage;
  
  @Column(name="last_discharge")
  String lastDischarge;
  
  @Column(name="master_information")
  String masterInformation;
  
  @Column(name="mawb_supporting_docs_id")
  Long mawbSupportingDocsId;
  
  @Column(name="mode_of_transport")
  String modeOfTransport;
  
  @Column(name="name_of_transporter")
  String nameOfTransporter;
  
  @Column(name="nationality_of_transport")
  String nationalityOfTransport;
  
  @Column(name="net_tonnage")
  String netTonnage;
  
  @Column(name="number_of_bols")
  Long numberOfBols;
  
  @Column(name="place_of_transporter")
  String placeOfTransporter;
  
  @Column(name="registration_date")
  Date registrationDate;
  
  @Column(name="registration_number")
  String registrationNumber;
  
  @Column(name="cargo_status")
  String cargoStatus;
}
