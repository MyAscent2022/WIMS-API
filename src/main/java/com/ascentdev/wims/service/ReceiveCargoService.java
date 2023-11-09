/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.service;

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
  ApiResponseModel searchFlights();
  ApiResponseModel getCargoCondition();
  ApiResponseModel getUlds(String flightNumber);
  ApiResponseModel getMawbs(String registryNumber);
  ApiResponseModel getHawbs(String registryNumber, String mawbNumber);
  ApiResponseModel saveImage(long userId,
          long mawbId,
          String registryNumber,
          MultipartFile[] file,
          long fileType,
          long cargoConditionId,
          long uldTypeId);
}
