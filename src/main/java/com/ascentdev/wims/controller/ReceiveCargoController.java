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
  public ApiResponseModel searchFlights() {
    return receiveCargoServiceImp.searchFlights();
  }

  @GetMapping("ulds_per_flight")
  public ApiResponseModel getUlds(@RequestParam("flight_number") String flight_number) {
    return receiveCargoServiceImp.getUlds(flight_number);
  }

  @GetMapping("mawbs_per_uld")
  public ApiResponseModel getMawbs(@RequestParam("registry_number") String registry_number) {
    return receiveCargoServiceImp.getMawbs(registry_number);
  }

  @GetMapping("hawbs_per_mawb")
  public ApiResponseModel getHawbs(@RequestParam("registry_number") String registry_number, @RequestParam("mawb_number") String mawb_number) {
    return receiveCargoServiceImp.getHawbs(registry_number, mawb_number);
  }

  @GetMapping("get_cargo_condition")
  public ApiResponseModel getCargoConditionList() {
    return receiveCargoServiceImp.getCargoCondition();
  }

  @PostMapping("save_image")
  public ApiResponseModel saveImage(@RequestParam("user_id") long user_id,
          @RequestParam("mawb_id") long mawb_id,
          @RequestParam("registry_number") String registry_number,
          @RequestParam("file_name") MultipartFile[] file,
          @RequestParam("file_type") long file_type,
          @RequestParam("cargo_conditon_id") long cargo_condition_id,
          @RequestParam("uld_type_id") long uld_type_id) {
    return receiveCargoServiceImp.saveImage(user_id, mawb_id, registry_number, file, file_type, cargo_condition_id, uld_type_id);
  }
  
  @PostMapping("confirm_cargo")
  public ApiResponseModel confirmCargo(@RequestBody CargoManifestModel cargoManifest){
    return receiveCargoServiceImp.confirmCargo(cargoManifest.getCargoManifest(), cargoManifest.getStorageLogs());
  }
}
