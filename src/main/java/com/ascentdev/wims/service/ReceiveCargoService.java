/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.service;

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
  ApiResponseModel searchFlights(long userId);
  ApiResponseModel getCargoCondition();
  ApiResponseModel getCargoCategory();
  ApiResponseModel getCargoClass();
  ApiResponseModel getCargoStatus();
  ApiResponseModel getUldType();
  ApiResponseModel getContainerType();
  ApiResponseModel getUlds(String flightNumber);
  ApiResponseModel getMawbs(boolean isUld, String uldNumber, String flightNumber);
  ApiResponseModel getHawbs(String mawbNumber);
  ApiResponseModel saveUldImage(MultipartFile[] file,
          String uldCondition1,
          String uldCondition2,
          String flightNumber,
          String uldNumber,
          String remarks1,
          String remarks2);
  ApiResponseModel saveHawbImage(MultipartFile[] file,
          int cargoConditionId,
          String mawbNumber,
          String hawbNumber,
          String flightNumber,
          String remarks, CargoActivityLogsEntity cargoLogs, MawbEntity mawbDetails, HawbEntity hawbDetails);
  Integer uploadImage(MultipartFile[] file, int hawbId, String mawbNumber, String cargoCondition1, String cargoCondition2, String remarks1, String remarks2);
  ApiResponseModel confirmCargo(CargoActivityLogsEntity cargoLogs, MawbEntity mawbDetails, HawbEntity hawbDetails, String mawb_number, String flightNumber, String hawb_number, int userId, String cargoCategory, String cargoClass);
  ApiResponseModel saveUldNumber(UldsEntity ulds, String[] mawbs, String uldNumber, String flightNumber, String uldType);
  ApiResponseModel updateUldNumber(UldsEntity ulds, String uldNumber);
  ApiResponseModel updateReceivingStatus(String registryNumber, boolean isConfirmed);
  ApiResponseModel getUldImages(String flightNumber, String uldNumber);
}
