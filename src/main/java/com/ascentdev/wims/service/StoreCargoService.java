/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.service;

import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.ImagesEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.model.AddedRackModel;
import com.ascentdev.wims.model.ApiResponseModel;
import java.sql.Timestamp;
import java.util.List;
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
          String rackCode,
          //          long rackUtilId,
          long user_id,
          long cargo_activity_logs_id,
          int actual_pcs,
          String registry_number,
          int uld_id);

  ApiResponseModel getImages(String mawbNumber, String hawbNumber, boolean isHawb, String registry_number);

  ApiResponseModel getRefRacks(boolean is_layer, String rackName);

//  ApiResponseModel getAllRefRacks();
  ApiResponseModel getRackDetails(boolean isHawb, String hawbNumber, String mawbNumber);

  ApiResponseModel getReleaseCargo();

//  ApiResponseModel saveReleaseCargo(String mawbNumber, String hawbNumber, String flightNumber, long userId);
  ApiResponseModel updateStoragerStatus(String hawbNumber, String mawbNumber, long user_id, String registry_number);

  ApiResponseModel getCargoImages(long cargoActivityLogId);

  ApiResponseModel saveStorageImages(MultipartFile[] file,
          int cargoConditionId,
          long userId,
          String rackName,
          String layerName,
          String rackCode,
          int storedPcs,
          String remarks,
          String flightNumber,
          CargoActivityLogsEntity cargoLogs,
          MawbEntity mawbDetails,
          HawbEntity hawbDetails);

  Integer uploadImage(MultipartFile[] file, String hawbNumber, String mawbNumber, List<ImagesEntity> imagesEntity, int uld_id, String registry_number);

  Integer uploadVideo(MultipartFile[] file, String hawbNumber, String mawbNumber, int uld_id, String registry_number);

  Integer uploadAddedImage(MultipartFile[] file, String hawbNumber, String mawbNumber, List<ImagesEntity> imagesEntity, int uld_id, String registry_number);

  Integer uploadAddedVideo(MultipartFile[] file, String hawbNumber, String mawbNumber, int uld_id, String registry_number);

  ApiResponseModel saveAddedRack(
          List<AddedRackModel> addedRackModel,
          String mawbNumber,
          String flightNumber,
          String hawb_number,
          //          long rack_util_id,
          long user_id,
          long cargo_activity_logs_id,
          String registry_number,
          int uld_id,
          int actual_pcs
  );
}
