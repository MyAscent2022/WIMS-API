/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.entity;

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
@Subselect("SELECT DISTINCT th.id,CASE WHEN cal.hawb_id IS NULL THEN true ELSE false END AS result FROM txn_hawb th\n"
        + "		LEFT JOIN cargo_activity_logs cal ON th.id=cal.hawb_id\n"
        + "		ORDER BY th.id DESC")
public class HawbForReceivingEntity {

  @Id
  int id;
  
  String result;
}
