/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.error;

import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
public class ErrorObject {
  private int status_code;
  private String message;
  private long timestamp;

  public ErrorObject(int status_code, String message, long timestamp) {
    this.status_code = status_code;
    this.message = message;
    this.timestamp = timestamp;
  }
}
