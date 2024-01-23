/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
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
@Table(name="job_assignments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class JobAssignmentEntity {
  @Id
  long id;
  
  @Column(name="assigned_user_id")
  long assignedUserId;
  
  @Column(name="flight_id")
  long flightId;
  
  @Column(name="auth_role_id")
  long authRoleId;
  
  @Column(name="date_assigned")
  Date dateAssigned;
  
  @Column(name="assigned_by_id")
  long assignedById;
  
  @Column(name="created_at")
  Timestamp createdAt;
  
  @Column(name="modified_by_id")
  Long modifiedById;
  
  @Column(name="modified_at")
  Timestamp modifiedAt;
}
