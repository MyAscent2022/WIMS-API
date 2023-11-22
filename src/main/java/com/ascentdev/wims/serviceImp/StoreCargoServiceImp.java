/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.entity.CargoImagesEntity;
import com.ascentdev.wims.entity.RackDetailsEntity;
import com.ascentdev.wims.entity.RackEntity;
import com.ascentdev.wims.entity.RefRackEntity;
import com.ascentdev.wims.entity.ReleasingCargoEntity;
import com.ascentdev.wims.entity.StorageCargoEntity;
import com.ascentdev.wims.entity.StorageLogsEntity;
import com.ascentdev.wims.error.ErrorException;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoImagesModel;
import com.ascentdev.wims.model.RackDetailsModel;
import com.ascentdev.wims.model.RefRackModel;
import com.ascentdev.wims.model.ReleaseCargoModel;
import com.ascentdev.wims.model.StorageCargoModel;
import com.ascentdev.wims.repository.CargoImagesRepository;
import com.ascentdev.wims.repository.RackDetailsRepository;
import com.ascentdev.wims.repository.RackRepository;
import com.ascentdev.wims.repository.RefRackRepository;
import com.ascentdev.wims.repository.ReleasingCargoRepository;
import com.ascentdev.wims.repository.StorageCargoRepository;
import com.ascentdev.wims.repository.StorageLogsRepository;
import com.ascentdev.wims.service.StoreCargoService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
  RackRepository rRepo;

  @Autowired
  CargoImagesRepository ciRepo;

  @Autowired
  RefRackRepository rrRepo;

  @Autowired
  StorageCargoRepository scRepo;

  @Autowired
  RackDetailsRepository rdRepo;

  @Autowired
  ReleasingCargoRepository rcRepo;

  @Autowired
  StorageLogsRepository storageRepo;

  @Override
  public ApiResponseModel getStorageCargo() {
    ApiResponseModel resp = new ApiResponseModel();
    StorageCargoModel data = new StorageCargoModel();

    List<StorageCargoEntity> storages = new ArrayList<>();

    try {
      storages = scRepo.findByCargoStatus(3);
      if (storages.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setStorages(storages);
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
  public ApiResponseModel saveRack(long oldRefRackId, long newRefRackId, long rack_utilization_id) {
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    RackEntity racks = new RackEntity();
    RefRackEntity refRack = new RefRackEntity();

    float tempV = 0;
    float volume = 0;

    try {
      Optional Optracks = rRepo.findById(rack_utilization_id);
      if (Optracks.isPresent()) {
        racks = (RackEntity) Optracks.get();
      }

      if (racks == null) {
        resp.setData(0);
        resp.setMessage("Failed");
        resp.setStatus(false);
        resp.setStatusCode(404);
        return resp;
      }

      Optional OptrefRack = rrRepo.findById(oldRefRackId);

      if (OptrefRack.isPresent()) {
        refRack = (RefRackEntity) OptrefRack.get();
      }

      tempV = refRack.getVolume() - racks.getVolume();
      refRack.setVolume(tempV);
      refRack = rrRepo.save(refRack);

      if (refRack == null) {
        resp.setData(0);
        resp.setMessage("Failed");
        resp.setStatus(false);
        resp.setStatusCode(404);
        return resp;
      }

      Optional newRefRack = rrRepo.findById(newRefRackId);
      if (newRefRack.isPresent()) {
        refRack = (RefRackEntity) newRefRack.get();
      }
      volume = refRack.getVolume() + racks.getVolume();
      refRack.setVolume(volume);
      refRack = rrRepo.save(refRack);
      if (refRack == null) {
        resp.setData(0);
        resp.setMessage("Failed");
        resp.setStatus(false);
        resp.setStatusCode(404);
        return resp;
      }
      racks.setRefRackId(newRefRackId);
      racks = rRepo.save(racks);

      if (racks == null) {
        resp.setData(0);
        resp.setMessage("Failed");
        resp.setStatus(false);
        resp.setStatusCode(404);
        return resp;
      }

      resp.setData(1);
      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
    } catch (ErrorException e) {
      resp.setData(0);
      resp.setMessage("Something went wrong");
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
    }

    return resp;
  }

  @Override
  public ApiResponseModel getImages(String mawbNumber, String hawbNumber, boolean isHawb) {
    ApiResponseModel resp = new ApiResponseModel();
    CargoImagesModel data = new CargoImagesModel();

    List<CargoImagesEntity> images = new ArrayList<>();

    try {
      if (isHawb) {
        images = ciRepo.findByHawbNumber(hawbNumber);
        if (images.size() != 0) {
          data.setImages(images);

          resp.setData(data);
          resp.setMessage(message);
          resp.setStatus(status);
          resp.setStatusCode(statusCode);
        } else {
          message = "No Data to Show";
          status = false;
          statusCode = 404;
        }
      } else {
        images = ciRepo.findByMawbNumber(mawbNumber);
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
      }
    } catch (ErrorException e) {
      e.printStackTrace();
    }
    return resp;
  }

  @Override
  public ApiResponseModel getRefRacks() {
    ApiResponseModel resp = new ApiResponseModel();
    RefRackModel data = new RefRackModel();

    List<RefRackEntity> refRacks = new ArrayList<>();

    try {
      refRacks = rrRepo.findAll();
      if (refRacks.size() > 0) {
        data.setRefRacks(refRacks);
        resp.setData(data);
        resp.setMessage(message);
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      } else {
        resp.setMessage("NO DATA FOUND");
        resp.setStatus(false);
        resp.setStatusCode(404);
      }
    } catch (ErrorException e) {
      e.printStackTrace();
      resp.setMessage("NO DATA FOUND");
      resp.setStatus(false);
      resp.setStatusCode(404);
    }
    return resp;
  }

  @Override
  public ApiResponseModel getRackDetails(boolean isHawb, String hawbNumber, String mawbNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    RackDetailsModel data = new RackDetailsModel();

    RackDetailsEntity rackDetails = new RackDetailsEntity();

    try {
      if (isHawb) {
        rackDetails = rdRepo.findByHawbNumber(hawbNumber);
        if (rackDetails != null) {
          data.setRackDetails(rackDetails);
          resp.setData(data);
          resp.setMessage(message);
          resp.setStatus(status);
          resp.setStatusCode(statusCode);
        } else {
          message = "NO DATA FOUND";
          status = false;
          statusCode = 404;
        }
      } else {
        rackDetails = rdRepo.findByMawbNumber(mawbNumber);
        if (rackDetails != null) {
          data.setRackDetails(rackDetails);
          resp.setData(data);
          resp.setMessage(message);
          resp.setStatus(status);
          resp.setStatusCode(statusCode);
        } else {
          message = "NO DATA FOUND";
          status = false;
          statusCode = 404;
        }
      }

    } catch (ErrorException e) {
      e.printStackTrace();
    }

    return resp;
  }

  @Override
  public ApiResponseModel getReleaseCargo() {
    ApiResponseModel resp = new ApiResponseModel();
    ReleaseCargoModel data = new ReleaseCargoModel();

    List<ReleasingCargoEntity> releaseCargo = new ArrayList<>();

    try {
      releaseCargo = rcRepo.findByStatus("PAID");
      if (releaseCargo.size() > 0) {
        data.setReleaseCargo(releaseCargo);
        resp.setData(data);
        resp.setMessage(message);
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      } else {
        message = "NO DATA TO SHOW";
        status = false;
        statusCode = 404;
      }
    } catch (ErrorException e) {
      e.printStackTrace();
    }
    return resp;
  }

  @Override
  public ApiResponseModel updateStoragerStatus(String hawbNumber, String mawbNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    StorageLogsEntity storageLogs = new StorageLogsEntity();
    try {
      storageLogs = storageRepo.findByMawbNumber(mawbNumber);
      if (storageLogs.getHawbNumber() == null) {
        storageLogs.setStoragerStatus("Done");
        storageRepo.save(storageLogs);
        resp.setMessage("STORAGER STATUS UPDATED");
        resp.setStatus(true);
        resp.setStatusCode(200);
      } else {
        storageLogs = storageRepo.findByHawbNumber(hawbNumber);
        if (storageLogs != null) {
          storageLogs.setStoragerStatus("Done");
          storageRepo.save(storageLogs);
          resp.setMessage("STORAGER STATUS UPDATED");
          resp.setStatus(true);
          resp.setStatusCode(200);
        } else {
          status = false;
          statusCode = 404;
          resp.setMessage("StorageLogsEntity with mawbNumber not found");
          resp.setStatus(status);
          resp.setStatusCode(statusCode);
        }

      }

    } catch (ErrorException e) {
      e.printStackTrace();
      message = "STORAGER STATUS FAILED TO UPDATE";
      status = false;
      statusCode = 404;

      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
    }

    return resp;
  }

}
