/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.controller;

import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoReleaseRequestModel;
import com.ascentdev.wims.model.PullOutCargoModel;
import com.ascentdev.wims.model.ReleasingModelForSaving;
import com.ascentdev.wims.serviceImp.CargoReleasingServiceImp;
import java.util.List;
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
 * @author User
 */
@RestController
@RequestMapping("/wims_api/")
public class CargoReleasingController {

  @Autowired
  CargoReleasingServiceImp cargoServiceImp;

  String fileUploadPath = "";

  @GetMapping("get_for_pull_out_cargo")
  public ApiResponseModel getPullOutCargoList() {
    return cargoServiceImp.getPullOutCargoList();
  }

  @GetMapping("get_truck_list")
  public ApiResponseModel getTruckList() {
    return cargoServiceImp.getTruckList();
  }

  @GetMapping("get_for_cargo_releasing")
  public ApiResponseModel getForReleasingCargo(@RequestParam("yellow_receipt") String yellow_receipt) {
    return cargoServiceImp.getForReleasingCargo(yellow_receipt);
  }

  @PostMapping("save_cargo_releasing")
  public ApiResponseModel saveCargoReleasing(@RequestBody List<ReleasingModelForSaving> releasingModelForSaving,
          @RequestParam("trucker") String trucker, @RequestParam("plateNo") String plateNo, @RequestParam("user_id") int user_id) {
    return cargoServiceImp.saveCargoReleasing(releasingModelForSaving, trucker, plateNo, user_id);
  }

  @PostMapping("save_pull_out")
  public ApiResponseModel savePullOutCargo(@RequestBody List<PullOutCargoModel> model, @RequestParam("user_id") int user_id) {
    return cargoServiceImp.savePullOut(model, user_id);
  }

//  @PostMapping("save_cargo_releasing_image_details")
//  public ApiResponseModel saveCargoReleasing(@RequestParam("files") MultipartFile[] files) {
//    return cargoServiceImp.saveCargoReleasingImage(files);
//  }
}
