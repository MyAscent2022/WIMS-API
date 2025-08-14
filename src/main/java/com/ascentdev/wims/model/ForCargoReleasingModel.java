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
  int hawbId;
  int mawbId;
  Integer uldId;
  String mawbNumber;
  String hawbNumber;
  int qty;
  String uom;
  String consignee;
  String cargoClass;
  boolean shawb;
  String location;
  boolean is_offload;

  public int getHawbId() {
    return hawbId;
  }

  public void setHawbId(int hawbId) {
    this.hawbId = hawbId;
  }

  public int getMawbId() {
    return mawbId;
  }

  public void setMawbId(int mawbId) {
    this.mawbId = mawbId;
  }

  public String getMawbNumber() {
    return mawbNumber;
  }

  public void setMawbNumber(String mawbNumber) {
    this.mawbNumber = mawbNumber;
  }

  public String getHawbNumber() {
    return hawbNumber;
  }

  public void setHawbNumber(String hawbNumber) {
    this.hawbNumber = hawbNumber;
  }

  public boolean isIs_offload() {
    return is_offload;
  }

  public void setIs_offload(boolean is_offload) {
    this.is_offload = is_offload;
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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Integer getUldId() {
    return uldId;
  }

  public void setUldId(Integer uldId) {
    this.uldId = uldId;
  }

  public String getCargoClass() {
    return cargoClass;
  }

  public void setCargoClass(String cargoClass) {
    this.cargoClass = cargoClass;
  }

  
  
  
  
  
}
