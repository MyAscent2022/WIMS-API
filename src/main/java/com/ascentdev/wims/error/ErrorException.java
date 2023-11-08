/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ASCENT
 */
@Data
public class ErrorException extends RuntimeException {
  String message;
  HttpStatus status;
  int status_code;
  long timestamp;

  public ErrorException(String message, HttpStatus status, int status_code, long timestamp) {
    super();
    this.message = message;
    this.status = status;
    this.status_code = status_code;
    this.timestamp = timestamp;
  }
}
