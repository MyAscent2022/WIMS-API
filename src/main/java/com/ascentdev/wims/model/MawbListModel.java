/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.model;

import java.sql.Date;
import java.sql.Time;
import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
public class MawbListModel {
  long id;
  Date dateOfArrival;
  String destinationCode;
  String mawbNumber;
  long numberOfContainers;
  int numberOfPackages;
  String originCode;
  String registryNumber;
  Time timeOfArrival;
  float volume;
  String uldNumber;
  Long uldContainerTypeId;
  String cargoStatus;
  int length;
  int width;
  int height;
  float actualWeight;
  Float actualVolume;
  int actualPcs;
  Long cargoCategoryId;
  long cargoClassId;
  long flightId;
  int hawbCount;
}
