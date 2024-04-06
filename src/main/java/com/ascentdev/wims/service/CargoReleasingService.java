/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.service;

import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoReleaseRequestModel;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */
public interface  CargoReleasingService {
  
  ApiResponseModel getCargoReleasingList();
  
  ApiResponseModel saveCargoReleasing(CargoReleaseRequestModel model);
  
  ApiResponseModel saveCargoReleasingImage(MultipartFile[] files);
  
}
