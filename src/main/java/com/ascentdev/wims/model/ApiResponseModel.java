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
  Object searchMawb;
  Object cargoEntity;
  List<?> condition; 
  List<?> layers;
  List<?> partialHawbs;
  List<?> logList;
  List<?> truckList;
  List<?> addedRackModel;
  Object rackUtil;
  String[] mawbs;
  long cargoActivityLogsId;
  boolean lastHawbToReceive;
  String token;
}
