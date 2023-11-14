/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.controller;

import com.ascentdev.wims.entity.CargoManifestEntity;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoManifestModel;
import com.ascentdev.wims.repository.CargoConditionRepository;
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
  public ApiResponseModel getMawbs(@RequestParam("uld_no") String uld_no) {
    return receiveCargoServiceImp.getMawbs(uld_no);
  }

  @GetMapping("hawbs_per_mawb")
  public ApiResponseModel getHawbs(@RequestParam("mawb_number") String mawb_number) {
    return receiveCargoServiceImp.getHawbs(mawb_number);
  }

  @GetMapping("get_cargo_condition")
  public ApiResponseModel getCargoConditionList() {
    return receiveCargoServiceImp.getCargoCondition();
  }

  @PostMapping("save_uld_image")
  public ApiResponseModel saveUldImage(@RequestParam("file_name") MultipartFile[] file,
          @RequestParam("cargo_condition_id") long cargo_condition_id,
          @RequestParam("uld_type_id") long uld_type_id,
          @RequestParam("remarks") String remarks) {
    return receiveCargoServiceImp.saveUldImage(file, cargo_condition_id, uld_type_id, remarks);
  }
  
  @PostMapping("save_hawb_image")
  public ApiResponseModel saveHawbImage(@RequestParam("file_name") MultipartFile[] file,
          @RequestParam("cargo_condition_id") long cargo_condition_id,
          @RequestParam("txn_cargo_manifest_details_id") long txn_cargo_manifest_details_id,
          @RequestParam("remarks") String remarks) {
    return receiveCargoServiceImp.saveHawbImage(file, cargo_condition_id, txn_cargo_manifest_details_id, remarks);
  }
  
  @PostMapping("confirm_cargo")
  public ApiResponseModel confirmCargo(@RequestBody CargoManifestModel cargoManifest){
    return receiveCargoServiceImp.confirmCargo(cargoManifest.getCargoManifest());
  }
}
