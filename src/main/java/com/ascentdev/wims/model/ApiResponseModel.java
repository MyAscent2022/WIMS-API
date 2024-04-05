/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.model;

import java.util.List;
import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
public class ApiResponseModel {
  String message;
  boolean status;
  int statusCode;
  Object data;
  List<?> condition; 
  List<?> layers;
  String[] mawbs;
}
