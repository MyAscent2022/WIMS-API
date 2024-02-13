/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.service;

import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.model.ApiResponseModel;
import java.sql.Timestamp;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ASCENT
 */
public interface StoreCargoService {
  ApiResponseModel getStorageCargo();
  ApiResponseModel saveRack(CargoActivityLogsEntity cargoLogs, 
          String mawbNumber,
          String flightNumber,
          String hawb_number,
          String rackName, 
          String layerName, 
          long rackUtilId, 
          long user_id);
  ApiResponseModel getImages(String mawbNumber, String hawbNumber, boolean isHawb);
  ApiResponseModel getRefRacks(boolean is_layer, String rackName);
  ApiResponseModel getRackDetails(boolean isHawb, String hawbNumber, String mawbNumber);
  ApiResponseModel getReleaseCargo();
  ApiResponseModel saveReleaseCargo(String mawbNumber, String hawbNumber, String flightNumber, long userId);
  ApiResponseModel updateStoragerStatus(String hawbNumber, String mawbNumber, long user_id);
  ApiResponseModel getCargoImages(long cargoActivityLogId);
  ApiResponseModel saveStorageImages(MultipartFile[] file, 
          int cargoConditionId, 
          long userId,
          String rackName,
          String layerName,
          int storedPcs,
          String remarks,
          String flightNumber,
          CargoActivityLogsEntity cargoLogs, 
          MawbEntity mawbDetails, 
          HawbEntity hawbDetails);
  Integer uploadImage(MultipartFile[] file, long hawbId, String mawbNumber, String cargoCondition1, String cargoCondition2, String remarks1, String remarks2);
}
