/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.service;

import com.ascentdev.wims.entity.Acceptance;
import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.UldsEntity;
import com.ascentdev.wims.model.ApiResponseModel;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ASCENT
 */
public interface ReceiveCargoService {
  ApiResponseModel searchFlights(String userId);
  ApiResponseModel getCargoCondition();
  ApiResponseModel getCargoCategory();
  ApiResponseModel getCargoClass();
  ApiResponseModel getCargoStatus();
  ApiResponseModel getUldType();
  ApiResponseModel getUlds(String flightNumber);
  ApiResponseModel getMawbs(boolean isUld, String uldNumber, String flightNumber);
  ApiResponseModel getHawbs(String mawbNumber);
  ApiResponseModel saveUldImage(MultipartFile[] file,
          long uldConditionId,
          String flightNumber,
          String uldNumber,
          String remarks);
  ApiResponseModel saveHawbImage(MultipartFile[] file,
          int cargoConditionId,
          String mawbNumber,
          String hawbNumber,
          String flightNumber,
          String remarks, CargoActivityLogsEntity cargoLogs, MawbEntity mawbDetails, HawbEntity hawbDetails);
  ApiResponseModel confirmCargo(CargoActivityLogsEntity cargoLogs, MawbEntity mawbDetails, HawbEntity hawbDetails, String mawb_number, String flightNumber, String hawb_number);
  ApiResponseModel saveUldNumber(UldsEntity ulds, String[] mawbs);
  ApiResponseModel updateUldNumber(UldsEntity ulds, String uldNumber);
  ApiResponseModel updateReceivingStatus(String registryNumber, boolean isConfirmed);
  
}
