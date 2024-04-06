/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.model;

import lombok.Data;

/**
 *
 * @author User
 */

@Data
public class CargoReleaseRequestModel {
  int awb_id;
  String trucker;
  String plateNo;
  String filenames;
  boolean shawb;
  long user_id;
}
