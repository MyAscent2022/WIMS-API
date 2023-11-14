/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.entity.CargoManifestEntity;
import com.ascentdev.wims.entity.CargoConditionEntity;
import com.ascentdev.wims.entity.CargoManifestDetailsEntity;
import com.ascentdev.wims.entity.ImagesEntity;
import com.ascentdev.wims.entity.FlightsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.StorageLogsEntity;
import com.ascentdev.wims.entity.UldsEntity;
import com.ascentdev.wims.error.ErrorException;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoConditionModel;
import com.ascentdev.wims.model.HawbModel;
import com.ascentdev.wims.model.MawbModel;
import com.ascentdev.wims.model.SearchFlightsModel;
import com.ascentdev.wims.model.UldsModel;
import com.ascentdev.wims.repository.CargoManifestRepository;
import com.ascentdev.wims.repository.CargoConditionRepository;
import com.ascentdev.wims.repository.CargoManifestDetailsRepository;
import com.ascentdev.wims.repository.FlightsRepository;
import com.ascentdev.wims.repository.HawbRepository;
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
import com.ascentdev.wims.repository.StorageLogsRepository;
import java.sql.Timestamp;
import java.util.Optional;

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

  @Autowired
  CargoManifestRepository cmRepo;

  @Autowired
  CargoConditionRepository cargoRepo;

  @Autowired
  StorageLogsRepository slRepo;

  @Autowired
  CargoManifestDetailsRepository cmdRepo;

  @Autowired
  HawbRepository hRepo;

  @Override
  public ApiResponseModel searchFlights(String userId) {
    ApiResponseModel resp = new ApiResponseModel();
    SearchFlightsModel data = new SearchFlightsModel();

    List<CargoManifestEntity> cargo = new ArrayList<>();
    List<UldsEntity> ulds = new ArrayList<>();

    try {
//      flights = fRepo.getFlights();
      cargo = cmRepo.getFlightsByUserId(userId);
      if (cargo.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setCargo(cargo);
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

    try {
      ulds = uRepo.getUlds(flightNumber);
      if (ulds.size() == 0) {
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
    } catch (ErrorException e) {
      e.printStackTrace();
    }

    return resp;
  }

  @Override
  public ApiResponseModel getMawbs(String uldNo) {
    ApiResponseModel resp = new ApiResponseModel();
    MawbModel data = new MawbModel();

    List<MawbEntity> mawb = new ArrayList<>();

    try {
      mawb = mRepo.getMawbs(uldNo);
      if (mawb.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setMawbs(mawb);
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
  public ApiResponseModel getHawbs(String mawbNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    HawbModel data = new HawbModel();

    List<HawbEntity> hawbs = new ArrayList<>();

    try {
      hawbs = hRepo.getHawbs(mawbNumber);
      if (hawbs.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setHawbs(hawbs);
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

  private void saveImage(MultipartFile file) {

    try {
      byte[] data = file.getBytes();
      Path path = Paths.get(fileUploadPath + "/" + file.getOriginalFilename());
      Files.write(path, data);
    } catch (IOException ex) {
      Logger.getLogger(ReceiveCargoServiceImp.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public ApiResponseModel confirmCargo(CargoManifestEntity cargoManifest) {
    ApiResponseModel resp = new ApiResponseModel();
    String inboundStatus = "For Storage";

    try {
      cargoManifest.setInboundStatus(inboundStatus);
      cargoManifest = cmRepo.save(cargoManifest);
      resp.setData(1);
      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
    } catch (ErrorException e) {
      resp.setData(0);
      message = "Error!";
      status = false;
      statusCode = 404;
    }

    return resp;
  }

  @Override
  public ApiResponseModel getCargoCondition() {
    ApiResponseModel resp = new ApiResponseModel();
    CargoConditionModel condition = new CargoConditionModel();

    List<CargoConditionEntity> condition1 = new ArrayList<>();

    try {
      condition1 = cargoRepo.findAll();
      if (condition1.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        condition.setCondition(condition1);
      }
      resp.setCondition(condition1);
      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
    } catch (ErrorException e) {
      e.printStackTrace();
    }
    return resp;
  }

  @Override
  public ApiResponseModel saveUldImage(MultipartFile[] file,
          long cargoConditionId,
          long uldTypeId,
          String remarks) {
    ApiResponseModel resp = new ApiResponseModel();

    int fileType = 1;

    try {
      for (MultipartFile f : file) {
        ImagesEntity images = new ImagesEntity();
        String filename = f.getOriginalFilename();
        images.setFilePath(fileUploadPath + "/" + filename);
        images.setFileName(filename);
        images.setFileType(fileType);

        images.setCargoConditionId(cargoConditionId);
        images.setUldTypeId(uldTypeId);
        images.setRemarks(remarks);
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

      e.printStackTrace();
    }
    return resp;
  }

  @Override
  public ApiResponseModel saveHawbImage(MultipartFile[] file,
          long cargoConditionId,
          long txnCargoManifestDetailsId,
          String remarks) {
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    int fileType = 2;

    try {
      for (MultipartFile f : file) {
        ImagesEntity images = new ImagesEntity();
        String filename = f.getOriginalFilename();
        images.setFilePath(fileUploadPath + "/" + filename);
        images.setFileName(filename);
        images.setFileType(fileType);

        images.setCargoConditionId(cargoConditionId);
        images.setTxnCargoManifestingDetailsId(txnCargoManifestDetailsId);
        images.setRemarks(remarks);
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

}
