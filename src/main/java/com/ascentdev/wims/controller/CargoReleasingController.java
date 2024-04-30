/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.controller;

import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoReleaseRequestModel;
import com.ascentdev.wims.serviceImp.CargoReleasingServiceImp;
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
  
  String fileUploadPath="";
  
  @GetMapping("get_for_cargo_releasing")
  public ApiResponseModel getForCargoReleasing() {
    return cargoServiceImp.getCargoReleasingList();
  }
  
  @PostMapping("save_cargo_releasing")
  public ApiResponseModel saveCargoReleasing(@RequestBody CargoReleaseRequestModel req) {
    return cargoServiceImp.saveCargoReleasing(req);
  }
  
  @PostMapping("save_cargo_releasing_image")
  public ApiResponseModel saveCargoReleasing(@RequestParam("files") MultipartFile[] files) {
    return cargoServiceImp.saveCargoReleasingImage(files);
  }
  
   
  
}
