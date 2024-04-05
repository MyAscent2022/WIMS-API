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
  String originCode;
  String registryNumber;
  String uldNumber;
  String cargoStatus;
  int numberOfPackages;
  float length;
  float width;
  float height;
  int actualPcs;
  int hawbCount;
  long numberOfContainers;
  int uldContainerTypeId;
  Long cargoCategoryId;
  Long cargoClassId;
  int flightId;
  Time timeOfArrival;
  float volume;
  float actualWeight;
  Float actualVolume;
}
