/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.controller;

import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.ImagesEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoActivityModel;
import com.ascentdev.wims.model.CargoImageRequestModel;
import com.ascentdev.wims.serviceImp.StoreCargoServiceImp;
import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ASCENT
 */
@RestController
@RequestMapping("/wims_api/")
public class StoreCargoController {
  
  @Autowired
  StoreCargoServiceImp storeCargoServiceImp;
  
  @GetMapping("store_cargo_mawbs")
  public ApiResponseModel getStoreCargo() {
    return storeCargoServiceImp.getStorageCargo();
  }
  
  @PostMapping("assign_rack")
  public ApiResponseModel saveRack(@RequestBody CargoActivityModel cargoLogs,
          @RequestParam("mawb_number") String mawb_number,
          @RequestParam("flight_number") String flight_number,
          @RequestParam("hawb_number") String hawb_number,
          @RequestParam("rack_name") String rack_name, 
          @RequestParam("layer_name") String layer_name,
          @RequestParam("rack_util_id") int rack_util_id,
          @RequestParam("user_id") int user_id) {
    return storeCargoServiceImp.saveRack(cargoLogs.getCargoActivity(), mawb_number, flight_number, hawb_number, rack_name, layer_name, rack_util_id, user_id);
  }
  
  @PostMapping("save_storage_image")
  public ApiResponseModel saveStorageImage(@RequestParam("file[]") MultipartFile[] file,
          @RequestParam("cargo_condition_id") int cargo_conditon_id,
          @RequestParam("user_id") long user_id,
          @RequestParam("rack_name") String rack_name,
          @RequestParam("layer_name") String layer_name,
          @RequestParam("stored_pcs") int stored_pcs,
          @RequestParam("remarks") String remarks,
          @RequestParam("hawb_number") String hawb_number,
          @RequestParam("mawb_number") String mawb_number) {
    
    LocalDateTime date = LocalDateTime.now();
    
    CargoActivityLogsEntity cargoLogs = new CargoActivityLogsEntity();
    cargoLogs.setHandledById(user_id);
    cargoLogs.setActualPcs(stored_pcs);
    cargoLogs.setReceivedReleasedDate(Timestamp.valueOf(date));
    cargoLogs.setUpdatedAt(Timestamp.valueOf(date));
    cargoLogs.setUpdatedById(user_id);
    cargoLogs.setCreatedAt(Timestamp.valueOf(date));
    cargoLogs.setCreatedById(user_id);
    
    MawbEntity mawbDetails = new MawbEntity();
    
    HawbEntity hawbDetails = new HawbEntity();
    
    return storeCargoServiceImp.saveStorageImages(file, cargo_conditon_id, user_id, rack_name, layer_name, stored_pcs, remarks, mawb_number, cargoLogs, mawbDetails, hawbDetails);
  }
  
  @GetMapping("get_images")
  public ApiResponseModel getImages(@RequestParam("is_hawb") boolean is_hawb, @RequestParam("hawb_number") String hawb_number, @RequestParam("mawb_number") String mawb_number) {
    return storeCargoServiceImp.getImages(mawb_number, hawb_number, is_hawb);
  }
  
  @GetMapping("get_ref_rack")
  public ApiResponseModel getRackRacks(boolean is_layer, String rack_name) {
    return storeCargoServiceImp.getRefRacks(is_layer,rack_name);
//    public ApiResponseModel getRackRacks() {
//    return storeCargoServiceImp.getAllRefRacks();
  }
  
  @GetMapping("get_rack_details")
  public ApiResponseModel getRackDetails(@RequestParam("is_hawb") boolean is_hawb, @RequestParam("hawb_number") String hawb_number, @RequestParam("mawb_number") String mawb_number) {
    return storeCargoServiceImp.getRackDetails(is_hawb, hawb_number, mawb_number);
  }
  
  @GetMapping("releasing_cargo")
  public ApiResponseModel getReleasingCargo(){
    return storeCargoServiceImp.getReleaseCargo();
  }
  
  @PostMapping("update_storager_status")
  public ApiResponseModel updateStoragerStatus(@RequestParam("hawb_number") String hawb_number, @RequestParam("mawb_number") String mawb_number, @RequestParam("user_id") long user_id) {
    return storeCargoServiceImp.updateStoragerStatus(hawb_number, mawb_number, user_id);
  }
  
  @GetMapping("get_cargo_images")
  public ApiResponseModel getCargoImages(@RequestParam("cargo_activity_log_id") long cargo_activity_log_id){
    return storeCargoServiceImp.getCargoImages(cargo_activity_log_id);
  }
  
  @PostMapping("upload_storage_image")
  public Integer uploadImage(@RequestParam("file[]") MultipartFile[] file, @RequestParam("hawb_id") int hawb_id, @RequestParam("mawb_number") String mawb_number, @RequestParam("list_image_details") String list_image_details) {
    Gson gson = new Gson();
    List<ImagesEntity> imagesEntity = new ArrayList<>();
    
    CargoImageRequestModel req = new CargoImageRequestModel();
    req.setImagesEntity(imagesEntity);
    req = gson.fromJson(list_image_details,CargoImageRequestModel.class);
    
    
    return storeCargoServiceImp.uploadImage(file, hawb_id, mawb_number, req.getImagesEntity());
  }
  
  @PostMapping("save_releasing_cargo")
  public ApiResponseModel saveReleaseCargo(@RequestParam("mawb_number") String mawb_number, @RequestParam("hawb_number") String hawb_number, @RequestParam("flight_number") String flight_number, @RequestParam("user_id") long user_id) {
    return storeCargoServiceImp.saveReleaseCargo(mawb_number, hawb_number, flight_number, user_id);
  }
  
  @RequestMapping(value = "view_checklist_image")
  public @ResponseBody
  void viewCheckListImage(@RequestParam("file_path") String file_path, HttpServletRequest request, HttpServletResponse response) throws Exception {

    try {
      File file = null;

      file = new File(file_path);

      Files.copy(file.toPath(), response.getOutputStream());
      response.getOutputStream().close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
