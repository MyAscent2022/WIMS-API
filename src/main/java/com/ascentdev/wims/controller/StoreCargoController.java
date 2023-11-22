/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.controller;

import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.serviceImp.StoreCargoServiceImp;
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
  public ApiResponseModel saveRack(@RequestParam("old_ref_rack_id") long old_ref_rack_id, 
          @RequestParam("new_ref_rack_id") long new_ref_rack_id,
          @RequestParam("id") long id) {
    return storeCargoServiceImp.saveRack(old_ref_rack_id, new_ref_rack_id, id);
  }
  
  @GetMapping("get_images")
  public ApiResponseModel getImages(@RequestParam("is_hawb") boolean is_hawb, @RequestParam("hawb_number") String hawb_number, @RequestParam("mawb_number") String mawb_number) {
    return storeCargoServiceImp.getImages(mawb_number, hawb_number, is_hawb);
  }
  
  @GetMapping("get_ref_rack")
  public ApiResponseModel getRackRacks() {
    return storeCargoServiceImp.getRefRacks();
  }
  
  @GetMapping("get_rack_details")
  public ApiResponseModel getRackDetails(@RequestParam("is_hawb") boolean is_hawb, @RequestParam("hawb_number") String hawb_number, @RequestParam("mawb_number") String mawb_number) {
    return storeCargoServiceImp.getRackDetails(is_hawb, hawb_number, mawb_number);
  }
  
  @GetMapping("releasing_cargo")
  public ApiResponseModel getReleasingCargo(){
    return storeCargoServiceImp.getReleaseCargo();
  }
  
  @PostMapping("update_storager_status")
  public ApiResponseModel updateStoragerStatus(@RequestParam("hawb_number") String hawb_number, @RequestParam("mawb_number") String mawb_number) {
    return storeCargoServiceImp.updateStoragerStatus(hawb_number, mawb_number);
  }
}
