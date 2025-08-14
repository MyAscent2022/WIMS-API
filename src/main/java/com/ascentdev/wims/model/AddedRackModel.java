/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.model;

/**
 *
 * @author ASCENT SOLUTIONS INC
 */
public class AddedRackModel {
  
  String rack_name;
  String layer_name;
  String rack_code;
  int actual_pcs;
  int storedPcs;

  public String getRack_name() {
    return rack_name;
  }

  public void setRack_name(String rack_name) {
    this.rack_name = rack_name;
  }

  public String getLayer_name() {
    return layer_name;
  }

  public void setLayer_name(String layer_name) {
    this.layer_name = layer_name;
  }

  public int getActual_pcs() {
    return actual_pcs;
  }

  public void setActual_pcs(int actual_pcs) {
    this.actual_pcs = actual_pcs;
  }

  public String getRack_code() {
    return rack_code;
  }

  public void setRack_code(String rack_code) {
    this.rack_code = rack_code;
  }

  public int getStoredPcs() {
    return storedPcs;
  }

  public void setStoredPcs(int storedPcs) {
    this.storedPcs = storedPcs;
  }
  
  
  
  
}
