/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.controller;

import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.serviceImp.StoreCargoServiceImp;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASCENT
 */
@RestController
@RequestMapping("/wims_api/")
public class StoreCargoController {
  
  @Autowired
  StoreCargoServiceImp storeCargoServiceImp;
  
  @GetMapping("store_cargo_mawbs")
  public ApiResponseModel getStoreCargo() {
    return storeCargoServiceImp.getStorageCargo();
  }
  
  @PostMapping("assign_rack")
  public ApiResponseModel saveRack(@RequestParam("ref_rack_id") long ref_rack_id,
          @RequestParam("mawb_number") String mawb_number,
          @RequestParam("hawb_number") String hawb_number,
          @RequestParam("stored_by_id") long stored_by_id,
          @RequestParam("released_by_id") long released_by_id) {
    return storeCargoServiceImp.saveRack(ref_rack_id, mawb_number, hawb_number, stored_by_id, released_by_id);
  }
  
  @GetMapping("get_racks")
  public ApiResponseModel getRacks() {
    return storeCargoServiceImp.getRacks();
  }
  
  @GetMapping("get_images")
  public ApiResponseModel getImages(@RequestParam("mawb_id") Long mawb_id) {
    return storeCargoServiceImp.getImages(mawb_id);
  }
}
