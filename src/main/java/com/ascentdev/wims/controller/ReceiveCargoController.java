/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.controller;

import com.ascentdev.wims.entity.Acceptance;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.SaveUldModel;
import com.ascentdev.wims.model.UldsModel;
import com.ascentdev.wims.serviceImp.ReceiveCargoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author
 * ASCENT
 */
@RestController
@RequestMapping("/wims_api/")
public class ReceiveCargoController {

  @Autowired
  ReceiveCargoServiceImp receiveCargoServiceImp;

  @GetMapping("flights")
  public ApiResponseModel searchFlights(@RequestParam("user_id") int user_id) {
    return receiveCargoServiceImp.searchFlights(String.valueOf(user_id));
  }

  @GetMapping("ulds_per_flight")
  public ApiResponseModel getUlds(@RequestParam("flight_number") String flight_number) {
    return receiveCargoServiceImp.getUlds(flight_number);
  }

  @GetMapping("mawbs_per_uld")
  public ApiResponseModel getMawbs(@RequestParam("is_uld") boolean is_uld, @RequestParam("uld_number") String uld_number, @RequestParam("flight_number") String flight_number) {
    return receiveCargoServiceImp.getMawbs(is_uld, uld_number, flight_number);
  }

  @GetMapping("hawbs_per_mawb")
  public ApiResponseModel getHawbs(@RequestParam("mawb_number") String mawb_number) {
    return receiveCargoServiceImp.getHawbs(mawb_number);
  }

  @GetMapping("get_cargo_condition")
  public ApiResponseModel getCargoConditionList() {
    return receiveCargoServiceImp.getCargoCondition();
  }
  
  @GetMapping("get_cargo_category")
  public ApiResponseModel getCargoCategory() {
    return receiveCargoServiceImp.getCargoCategory();
  }
  
  @GetMapping("get_cargo_class")
  public ApiResponseModel getCargoClass() {
    return receiveCargoServiceImp.getCargoClass();
  }

  @PostMapping("save_uld_image")
  public ApiResponseModel saveUldImage(@RequestParam("file_name") MultipartFile[] file,
          @RequestParam("uld_condition_id") long uld_condition_id,
          @RequestParam("flight_number") String flight_number,
          @RequestParam("uld_number") String uld_number,
          @RequestParam("remarks") String remarks) {
    return receiveCargoServiceImp.saveUldImage(file, uld_condition_id, flight_number, uld_number, remarks);
  }
  
  @PostMapping("save_hawb_image")
  public ApiResponseModel saveHawbImage(@RequestParam("file_name") MultipartFile[] file,
          @RequestParam("cargo_condition_id") long cargo_condition_id,
          @RequestParam("mawb_number") String mawb_number,
          @RequestParam("hawb_number") String hawb_number,
          @RequestParam("flight_number") String flight_number,
          @RequestParam("remarks") String remarks) {
    return receiveCargoServiceImp.saveHawbImage(file, cargo_condition_id, mawb_number, hawb_number, flight_number, remarks);
  }
  
  @PostMapping("confirm_cargo")
  public ApiResponseModel confirmCargo(@RequestBody Acceptance acceptance){
    return receiveCargoServiceImp.confirmCargo(acceptance);
  }
  
  @GetMapping("get_cargo_status")
  public ApiResponseModel getCargoStatus() {
    return receiveCargoServiceImp.getCargoStatus();
  }
  
  @GetMapping("get_uld_type")
  public ApiResponseModel getUldType() {
    return receiveCargoServiceImp.getUldType();
  }
  
  @PostMapping("save_uld_number")
  public ApiResponseModel saveUldNumber(@RequestBody SaveUldModel saveUld){
    return receiveCargoServiceImp.saveUldNumber(saveUld.getUlds(), saveUld.getMawbs());
  }
  
  @PostMapping("update_receiver_status")
  public ApiResponseModel updateReceivingStatus(@RequestParam("registry_number") String registry_number, @RequestParam("is_confirmed") boolean is_confirmed) {
    return receiveCargoServiceImp.updateReceivingStatus(registry_number, is_confirmed);
  }
  
  @PostMapping("update_uld_number")
  public ApiResponseModel updateUldNumber(@RequestBody UldsModel uldsModel) {
    return receiveCargoServiceImp.updateUldNumber(uldsModel.getUlds(), uldsModel.getUld_number());
  }
  
}
