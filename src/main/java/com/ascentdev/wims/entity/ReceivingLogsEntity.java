/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Time;
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
@Table(name="txn_receiving_logs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReceivingLogsEntity {
  @Id
  long id;
  
  @Column(name="receiver_status")
  String receiverStatus;
  
  @Column(name="registry_number")
  String registryNumber;
}
