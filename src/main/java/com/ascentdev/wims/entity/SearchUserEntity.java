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
 * @author ASCENT
 */
@Data
@Entity
@Subselect("SELECT u.id AS user_id, u.username, u.role_id, up.firstname, up.lastname FROM public.users u\n"
        + "INNER JOIN public.user_profile up ON up.user_id = u.id")
public class SearchUserEntity {
  @Id
  @Column(name="user_id")
  long userId;
  
  
  @Column(name="username")
  String username;
  
  @Column(name="role_id")
  long roleId;
  
  @Column(name="firstname")
  String firstname;
  
  @Column(name="lastname")
  String lastname;
}
