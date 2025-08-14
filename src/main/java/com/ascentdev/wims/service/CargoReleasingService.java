/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.service;

import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoReleaseRequestModel;
import com.ascentdev.wims.model.PullOutCargoModel;
import com.ascentdev.wims.model.ReleasingModelForSaving;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */
public interface CargoReleasingService {

  ApiResponseModel getPullOutCargoList();

  ApiResponseModel getForReleasingCargo(String yellow_receipt);

  ApiResponseModel saveCargoReleasing(List<ReleasingModelForSaving> releasingModelForSaving, String trucker, String plateNo, int user_id);

  ApiResponseModel savePullOut(List<PullOutCargoModel> model, int user_id);
  ApiResponseModel getTruckList();

//  ApiResponseModel saveCargoReleasingImage(MultipartFile[] files);

}
