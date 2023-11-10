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
  ApiResponseModel saveRack(long refRackId,
          String mawbNumber,
          String hawbNumber,
          long stored_by_id,
          long released_by_id);
  ApiResponseModel getRacks();
  ApiResponseModel getImages(Long mawbId);
}
