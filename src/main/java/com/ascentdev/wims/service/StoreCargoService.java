/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.service;

import com.ascentdev.wims.model.ApiResponseModel;
import java.sql.Timestamp;

/**
 *
 * @author ASCENT
 */
public interface StoreCargoService {
  ApiResponseModel getStorageCargo();
  ApiResponseModel saveRack(long oldRefRackId, long newRefRackId, long rack_utilization_id);
  ApiResponseModel getImages(String mawbNumber, String hawbNumber, boolean isHawb);
  ApiResponseModel getRefRacks();
  ApiResponseModel getRackDetails(boolean isHawb, String hawbNumber, String mawbNumber);
  ApiResponseModel getReleaseCargo();
  ApiResponseModel updateStoragerStatus(String hawbNumber, String mawbNumber);
}
