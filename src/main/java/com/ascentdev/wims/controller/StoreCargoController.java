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
  public ApiResponseModel saveRack(@RequestParam("rack_name") String rack_name, 
          @RequestParam("layer_name") String layer_name,
          @RequestParam("rack_util_id") int rack_util_id,
          @RequestParam("user_id") int user_id) {
    return storeCargoServiceImp.saveRack(rack_name, layer_name, rack_util_id, user_id);
  }
  
  @GetMapping("get_images")
  public ApiResponseModel getImages(@RequestParam("is_hawb") boolean is_hawb, @RequestParam("hawb_number") String hawb_number, @RequestParam("mawb_number") String mawb_number) {
    return storeCargoServiceImp.getImages(mawb_number, hawb_number, is_hawb);
  }
  
  @GetMapping("get_ref_rack")
  public ApiResponseModel getRackRacks(boolean is_layer, String rackName) {
    return storeCargoServiceImp.getRefRacks(is_layer,rackName);
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
  public ApiResponseModel updateStoragerStatus(@RequestParam("hawb_number") String hawb_number, @RequestParam("mawb_number") String mawb_number, @RequestParam("user_id") int user_id) {
    return storeCargoServiceImp.updateStoragerStatus(hawb_number, mawb_number, user_id);
  }
}
