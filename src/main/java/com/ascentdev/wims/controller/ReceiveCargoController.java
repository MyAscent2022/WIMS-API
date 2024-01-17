/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.controller;

import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.ConfirmCargoModel;
import com.ascentdev.wims.model.SaveUldModel;
import com.ascentdev.wims.model.UldsModel;
import com.ascentdev.wims.serviceImp.ReceiveCargoServiceImp;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author
 * ASCENT
 */
@RestController
@RequestMapping("/wims_api/")
public class ReceiveCargoController {

  @Autowired
  ReceiveCargoServiceImp receiveCargoServiceImp;

  @GetMapping("flights")
  public ApiResponseModel searchFlights(@RequestParam("user_id") long user_id) {
    return receiveCargoServiceImp.searchFlights(user_id);
  }

  @GetMapping("ulds_per_flight")
  public ApiResponseModel getUlds(@RequestParam("flight_number") String flight_number) {
    return receiveCargoServiceImp.getUlds(flight_number);
  }

  @GetMapping("mawbs_per_uld")
  public ApiResponseModel getMawbs(@RequestParam("is_uld") boolean is_uld, @RequestParam("uld_number") String uld_number, @RequestParam("flight_number") String flight_number) {
    return receiveCargoServiceImp.getMawbs(is_uld, uld_number, flight_number);
  }

  @GetMapping("hawbs_per_mawb")
  public ApiResponseModel getHawbs(@RequestParam("mawb_number") String mawb_number) {
    return receiveCargoServiceImp.getHawbs(mawb_number);
  }

  @GetMapping("get_cargo_condition")
  public ApiResponseModel getCargoConditionList() {
    return receiveCargoServiceImp.getCargoCondition();
  }

  @GetMapping("get_cargo_category")
  public ApiResponseModel getCargoCategory() {
    return receiveCargoServiceImp.getCargoCategory();
  }

  @GetMapping("get_cargo_class")
  public ApiResponseModel getCargoClass() {
    return receiveCargoServiceImp.getCargoClass();
  }

  @PostMapping("save_uld_image")
  public ApiResponseModel saveUldImage(@RequestParam("file_name") MultipartFile[] file,
          @RequestParam("uld_condition_id") long uld_condition_id,
          @RequestParam("flight_number") String flight_number,
          @RequestParam("uld_number") String uld_number,
          @RequestParam("remarks") String remarks) {
    return receiveCargoServiceImp.saveUldImage(file, uld_condition_id, flight_number, uld_number, remarks);
  }

  @PostMapping("save_hawb_image")
  public ApiResponseModel saveHawbImage(@RequestParam("file[]") MultipartFile[] file,
          @RequestParam("user_id") int user_id,
          @RequestParam("mawb_number") String mawb_number,
          @RequestParam("hawb_number") String hawb_number,
          @RequestParam("flight_number") String flight_number,
          @RequestParam("remarks") String remarks,
          @RequestParam("actual_pcs") int actual_pcs,
          @RequestParam("actual_weight") float actual_weight,
          @RequestParam("actual_volume") Float actual_volume,
          @RequestParam("length") int length,
          @RequestParam("width") int width,
          @RequestParam("height") int height,
          @RequestParam("volume") String volume,
          @RequestParam("cargo_condition_id") int cargo_condition_id,
          @RequestParam("cargo_category_id") int cargo_category_id,
          @RequestParam("cargo_class_id") int cargo_class_id,
          @RequestParam("cargo_status") String cargo_status) {

    LocalDateTime date = LocalDateTime.now();

    CargoActivityLogsEntity cargoLogs = new CargoActivityLogsEntity();
    cargoLogs.setHandledById(user_id);
    cargoLogs.setActualPcs(actual_pcs);
    cargoLogs.setReceivedReleasedDate(Timestamp.valueOf(date));
    cargoLogs.setUpdatedAt(Timestamp.valueOf(date));
    cargoLogs.setUpdatedById((long)user_id);
    cargoLogs.setCreatedAt(Timestamp.valueOf(date));
    cargoLogs.setCreatedById(user_id);

    MawbEntity mawbDetails = new MawbEntity();
    mawbDetails.setCargoStatus(cargo_status);
    mawbDetails.setCargoCategoryId(cargo_category_id);
    mawbDetails.setCargoClassId(cargo_class_id);
    mawbDetails.setLength(length);
    mawbDetails.setWidth(width);
    mawbDetails.setHeight(height);
    mawbDetails.setActualVolume(actual_volume);
    mawbDetails.setActualWeight(actual_weight);
    mawbDetails.setActualPcs(actual_pcs);

    HawbEntity hawbDetails = new HawbEntity();

    return receiveCargoServiceImp.saveHawbImage(file, cargo_condition_id, mawb_number, hawb_number, flight_number, remarks, cargoLogs, mawbDetails, hawbDetails);
  }

  @PostMapping("confirm_cargo")
  public ApiResponseModel confirmCargo(@RequestBody ConfirmCargoModel confirmCargo, 
          @RequestParam("mawb_number") String mawbNumber, 
          @RequestParam("flight_number") String flightNumber, 
          @RequestParam("hawb_number") String hawbNumber, 
          @RequestParam("user_id") int userId,
          @RequestParam("cargo_category") String cargo_category,
          @RequestParam("cargo_class") String cargo_class) {
    return receiveCargoServiceImp.confirmCargo(confirmCargo.getCargoLogs(), confirmCargo.getMawbDetails(), confirmCargo.getHawbDetails(), mawbNumber, flightNumber, hawbNumber, userId, cargo_category, cargo_class);
  }

  @GetMapping("get_cargo_status")
  public ApiResponseModel getCargoStatus() {
    return receiveCargoServiceImp.getCargoStatus();
  }

  @GetMapping("get_uld_type")
  public ApiResponseModel getUldType() {
    return receiveCargoServiceImp.getUldType();
  }

  @PostMapping("save_uld_number")
  public ApiResponseModel saveUldNumber(@RequestBody SaveUldModel saveUld) {
    return receiveCargoServiceImp.saveUldNumber(saveUld.getUlds(), saveUld.getMawbs(), saveUld.getUldNumber());
  }

  @PostMapping("update_receiver_status")
  public ApiResponseModel updateReceivingStatus(@RequestParam("registry_number") String registry_number, @RequestParam("is_confirmed") boolean is_confirmed) {
    return receiveCargoServiceImp.updateReceivingStatus(registry_number, is_confirmed);
  }

  @PostMapping("update_uld_number")
  public ApiResponseModel updateUldNumber(@RequestBody UldsModel updateUld) {
    return receiveCargoServiceImp.updateUldNumber(updateUld.getUlds(), updateUld.getUld_number());
  }
  
  @PostMapping("upload_image")
  public Integer uploadImage(@RequestParam("file[]") MultipartFile[] file, @RequestParam("hawb_id") long hawb_id, @RequestParam("mawb_number") String mawb_number, @RequestParam("cargo_condition1") String cargo_condition1, @RequestParam("cargo_condition2") String cargo_condition2, @RequestParam("remarks1") String remarks1, @RequestParam("remarks2") String remarks2) {
    return receiveCargoServiceImp.uploadImage(file, hawb_id, mawb_number, cargo_condition1, cargo_condition2, remarks1, remarks2);
  }
  
  @GetMapping("get_uld_container_type")
  public ApiResponseModel getContainerType() {
    return receiveCargoServiceImp.getContainerType();
  }

}
