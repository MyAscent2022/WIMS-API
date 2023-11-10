/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.entity.ImagesEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.RackEntity;
import com.ascentdev.wims.error.ErrorException;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.ImagesModel;
import com.ascentdev.wims.model.MawbModel;
import com.ascentdev.wims.model.RackModel;
import com.ascentdev.wims.repository.ImagesRepository;
import com.ascentdev.wims.repository.MawbRepository;
import com.ascentdev.wims.repository.RackRepository;
import com.ascentdev.wims.service.StoreCargoService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ASCENT
 */
@Service
public class StoreCargoServiceImp implements StoreCargoService {

  boolean status = true;
  String message = "Success!";
  int statusCode = 200;

  @Autowired
  MawbRepository mRepo;

  @Autowired
  RackRepository rRepo;

  @Autowired
  ImagesRepository iRepo;

  @Override
  public ApiResponseModel getStorageCargo() {
    ApiResponseModel resp = new ApiResponseModel();
    MawbModel data = new MawbModel();

    List<MawbEntity> mawbs = new ArrayList<>();

    try {
      mawbs = mRepo.getStoreCargo();
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
  public ApiResponseModel saveRack(long refRackId,
          String mawbNumber,
          String hawbNumber,
          long stored_by_id,
          long released_by_id) {
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    try {
      RackEntity racks = new RackEntity();
      racks.setRefRackId(refRackId);
      racks.setMawbNumber(mawbNumber);
      racks.setHawbNumber(hawbNumber);
      racks.setStoredById(stored_by_id);
      racks.setStoredDt(Timestamp.valueOf(date));
      racks.setReleasedById(released_by_id);
      racks.setReleasedDt(Timestamp.valueOf(date));
      racks = rRepo.save(racks);

      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
      resp.setData(1);
    } catch (ErrorException e) {
      resp.setMessage("Fail");
      resp.setStatus(false);
      resp.setStatusCode(404);
      resp.setData(0);
    }

    return resp;
  }

  @Override
  public ApiResponseModel getRacks() {
    ApiResponseModel resp = new ApiResponseModel();
    RackModel data = new RackModel();

    List<RackEntity> racks = new ArrayList<>();

    try {
      racks = rRepo.getRacks();
      if (racks.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setRacks(racks);
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
  public ApiResponseModel getImages(Long mawbId) {
    ApiResponseModel resp = new ApiResponseModel();
    ImagesModel data = new ImagesModel();

    List<ImagesEntity> images = new ArrayList<>();

    try {
      images = iRepo.getImages(mawbId);
      if (images.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setImages(images);
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

}
