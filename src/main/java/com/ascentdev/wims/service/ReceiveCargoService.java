/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.service;

import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.UldsEntity;
import com.ascentdev.wims.model.AddedRackModel;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.HawbModel;
import com.ascentdev.wims.model.UnmanifestedMawbModel;
import java.util.List;
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

  ApiResponseModel checkHawb(int uld_id, int hawb_id, long mawb_id);

  ApiResponseModel getUldType();

  ApiResponseModel getContainerType();

  ApiResponseModel getUlds(String flightNumber, String registryNumber);

  ApiResponseModel getMawbs(boolean isUld, String uldNumber, String flightNumber, String registry_number);

  ApiResponseModel getHawbs(String mawbNumber);

  ApiResponseModel saveUldImage(MultipartFile[] file,
          String flightNumber,
          String uldNumber,
          String remarks[],
          String uldCondition[],
          int user_id);

  ApiResponseModel saveHawbImage(MultipartFile[] file,
          int cargoConditionId,
          String mawbNumber,
          String hawbNumber,
          String flightNumber,
          String remarks, CargoActivityLogsEntity cargoLogs, MawbEntity mawbDetails, HawbEntity hawbDetails);

  Integer uploadImage(MultipartFile[] file, int cargoConditionId[], String cargoCondition[], String remarks[], boolean is_badOrder[], int hawbId[], String mawbNumber[], int quantity[], int uld_id, String registry_number);

  Integer uploadVideo(MultipartFile[] file, boolean is_badOrder, int hawbId, String mawbNumber, int uld_id, String registry_number);

  ApiResponseModel confirmCargo(CargoActivityLogsEntity cargoLogs, MawbEntity mawbDetails, HawbEntity hawbDetails, String mawb_number, String flightNumber, String hawb_number, int userId, String cargoCategory, String cargoClass, String uld_number, boolean is_badOrder, String shipment_status, boolean is_partial, int uld_id, String registry_number, boolean is_offload, String received_registry_no, String gate_releasing);

//  ApiResponseModel saveUldNumber(UldsEntity ulds, String[] mawbs, String uldNumber, String flightNumber, String uldType);
//  ApiResponseModel updateUldNumber(UldsEntity ulds, String uldNumber);
  ApiResponseModel updateReceivingStatus(String registryNumber, boolean isConfirmed);

  ApiResponseModel getUldImages(String flightNumber, String uldNumber);

  ApiResponseModel saveMawbHawb(List<HawbModel> hawbModel, String mawb_number, String flight_number, String uld_number, String registry_number, int quantity, int userId);

  ApiResponseModel getPartialHawbs(int hawb_id, int mawb_id, String flightNumber);

  ApiResponseModel searchMawb(String mawb_number);

  ApiResponseModel saveEditedContainer(String container_number, String mawb_number, String flight_number, int mawb_id, String current_cont_number);
}
