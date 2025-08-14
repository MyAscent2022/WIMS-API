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
import org.hibernate.annotations.Subselect;

/**
 *
 * @author ASCENT
 */
@Data
@Entity
@Subselect("SELECT u.id,\n"
        + "u.email,\n"
        + "u.passkey,\n"
        + "u.username,\n"
        + "u.role_id,\n"
        + "u.user_type_id,\n"
        + "rw.description\n"
        + "FROM users u\n"
        + "INNER JOIN ref_warehouses rw ON rw.id = u.warehouse_id")
public class UserPerWarehouseEntity {

  @Id
  long id;

  String email;

  String passkey;

  String username;

  @Column(name = "role_id")
  Long roleId;

  @Column(name = "user_type_id")
  Long userTypeId;

  @Column(name = "description")
  String warehouse;
}
