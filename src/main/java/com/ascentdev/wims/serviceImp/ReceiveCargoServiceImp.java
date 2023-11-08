/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.entity.ImagesEntity;
import com.ascentdev.wims.entity.FlightsEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.UldsEntity;
import com.ascentdev.wims.error.ErrorException;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.MawbModel;
import com.ascentdev.wims.model.SearchFlightsModel;
import com.ascentdev.wims.model.UldsModel;
import com.ascentdev.wims.repository.FlightsRepository;
import com.ascentdev.wims.repository.MawbRepository;
import com.ascentdev.wims.repository.UldsRepository;
import com.ascentdev.wims.service.ReceiveCargoService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ascentdev.wims.repository.ImagesRepository;
import java.sql.Timestamp;

/**
 *
 * @author ASCENT
 */
@Service
public class ReceiveCargoServiceImp implements ReceiveCargoService {
  
  boolean status = true;
  String message = "Success!";
  int statusCode = 200;
  
  String fileUploadPath = "C:\\WIMS\\DOCUMENTS";
  
  @Autowired
  UldsRepository uRepo;
  
  @Autowired
  MawbRepository mRepo;
  
  @Autowired
  FlightsRepository fRepo;
  
  @Autowired
  ImagesRepository iRepo;

  @Override
  public ApiResponseModel searchFlights() {
    ApiResponseModel resp = new ApiResponseModel();
    SearchFlightsModel data = new SearchFlightsModel();

    List<FlightsEntity> flights = new ArrayList<>();
    List<UldsEntity> ulds = new ArrayList<>();

    try {
      flights = fRepo.getFlights();
      if (flights.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setFlights(flights);
      }
      resp.setData(data);
      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
    } catch (ErrorException e) {
      e.printStackTrace();
    }
    return resp;
  }

  @Override
  public ApiResponseModel getUlds(String flightNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    UldsModel data = new UldsModel();
    
    List<UldsEntity> ulds = new ArrayList<>();
    
    try{
      ulds = uRepo.getUlds(flightNumber);
      if (ulds.size() == 0){
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setUlds(ulds);
      }
      resp.setData(data);
      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
    } catch (ErrorException e){
      e.printStackTrace();
    }
    
    return resp;
  }

  @Override
  public ApiResponseModel getMawbs(String registryNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    MawbModel data = new MawbModel();
    
    List<MawbEntity> mawbs = new ArrayList<>();
    
    try {
      mawbs = mRepo.getMawbs(registryNumber);
      if (mawbs.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setMawbs(mawbs);
      }
      resp.setData(data);
      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
    }catch(ErrorException e){
      e.printStackTrace();
    }
    
    return resp;
  }

  @Override
  public ApiResponseModel getHawbs(String registryNumber, String mawbNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    MawbModel data = new MawbModel();
    
    List<MawbEntity> mawbs = new ArrayList<>();
    
    try {
      mawbs = mRepo.getHawbs(registryNumber, mawbNumber);
      if (mawbs.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setMawbs(mawbs);
      }
      resp.setData(data);
      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
    } catch (ErrorException e) {
      e.printStackTrace();
    }
    return resp;
  }

  @Override
  public ApiResponseModel saveImage(long userId,
          long mawbId,
          String registryNumber,
          MultipartFile[] file,
          long fileType,
          long cargoConditionId,
          long uldTypeId) {
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();
    
    
    
    try {
      for (MultipartFile f : file) {
        ImagesEntity images = new ImagesEntity();
        String filename = f.getOriginalFilename();
        images.setMawbId(mawbId);
        images.setRegistryNumber(registryNumber);
        images.setFilePath(fileUploadPath + "/" + filename);
        images.setFileName(filename);
        images.setUserId(userId);
        iRepo.save(images);
        saveImage(f);
      }
      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
      resp.setData(1);
    } catch (ErrorException e) {
      resp.setMessage("Image Did Not Upload");
      resp.setStatus(false);
      resp.setStatusCode(404);
      resp.setData(0);
    }
    return resp;
  }
  
  private void saveImage(MultipartFile file) {

    try {
      byte[] data = file.getBytes();
      Path path = Paths.get(fileUploadPath + "/" + file.getOriginalFilename());
      Files.write(path, data);
    } catch (IOException ex) {
      Logger.getLogger(ReceiveCargoServiceImp.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}
