/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.service;

import com.ascentdev.wims.entity.CargoManifestEntity;
import com.ascentdev.wims.entity.StorageLogsEntity;
import com.ascentdev.wims.entity.UldsEntity;
import com.ascentdev.wims.model.ApiResponseModel;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ASCENT
 */
public interface ReceiveCargoService {
  ApiResponseModel searchFlights(String userId);
  ApiResponseModel getCargoCondition();
  ApiResponseModel getUlds(String flightNumber);
  ApiResponseModel getMawbs(String uldNo);
  ApiResponseModel getHawbs(String mawbNumber);
  ApiResponseModel saveUldImage(MultipartFile[] file,
          long cargoConditionId,
          long uldTypeId,
          String remarks);
  ApiResponseModel saveHawbImage(MultipartFile[] file,
          long cargoConditionId,
          long txnCargoManifestDetailsId,
          String remarks);
  ApiResponseModel confirmCargo(CargoManifestEntity cargoManifest);
}
