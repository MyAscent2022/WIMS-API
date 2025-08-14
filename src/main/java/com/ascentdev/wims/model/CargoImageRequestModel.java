/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.model;

import com.ascentdev.wims.entity.ImagesEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlo Dela Peña <carlo090899@gmail.com>
 */
public class CargoImageRequestModel {
  
   List<ImagesEntity> imagesEntity;
   List<ImagesEntity> addedLocation;

  public List<ImagesEntity> getAddedLocation() {
    return addedLocation;
  }

  public void setAddedLocation(List<ImagesEntity> addedLocation) {
    this.addedLocation = addedLocation;
  }

      
  public List<ImagesEntity> getImagesEntity() {
    return imagesEntity;
  }

  public void setImagesEntity(List<ImagesEntity> imagesEntity) {
    this.imagesEntity = imagesEntity;
  }
   

  
}
