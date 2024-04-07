/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.model;

/**
 *
 * @author User
 */
public class ForCargoReleasingModel {
  int id;
  String awb;
  int qty;
  String uom;
  String consignee;
  boolean shawb;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getAwb() {
    return awb;
  }

  public void setAwb(String awb) {
    this.awb = awb;
  }

  public int getQty() {
    return qty;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }

  public String getUom() {
    return uom;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }

  public String getConsignee() {
    return consignee;
  }

  public void setConsignee(String consignee) {
    this.consignee = consignee;
  }

  public boolean isShawb() {
    return shawb;
  }

  public void setShawb(boolean shawb) {
    this.shawb = shawb;
  }

  
  
  
  
}
