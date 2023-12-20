/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.entity.Acceptance;
import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.CargoImagesEntity;
import com.ascentdev.wims.entity.CargoReleaseEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.MawbEntity;
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
import com.ascentdev.wims.repository.AcceptanceRepository;
import com.ascentdev.wims.repository.CargoActivityLogsRepository;
import com.ascentdev.wims.repository.CargoImagesRepository;
import com.ascentdev.wims.repository.CargoReleaseRepository;
import com.ascentdev.wims.repository.HawbRepository;
import com.ascentdev.wims.repository.MawbRepository;
import com.ascentdev.wims.repository.RackDetailsRepository;
import com.ascentdev.wims.repository.RackRepository;
import com.ascentdev.wims.repository.RefRackRepository;
import com.ascentdev.wims.repository.ReleasingCargoRepository;
import com.ascentdev.wims.repository.StorageCargoRepository;
import com.ascentdev.wims.repository.StorageLogsRepository;
import com.ascentdev.wims.service.StoreCargoService;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author
 * ASCENT
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

  @Autowired
  CargoReleaseRepository releaseRepo;

  @Autowired
  AcceptanceRepository aRepo;

  @Autowired
  CargoActivityLogsRepository cargoRepo;

  @Autowired
  MawbRepository mRepo;

  @Autowired
  HawbRepository hRepo;

  @Override
  public ApiResponseModel getStorageCargo() {
    ApiResponseModel resp = new ApiResponseModel();
    StorageCargoModel data = new StorageCargoModel();

    List<StorageCargoEntity> storages = new ArrayList<>();

    try {
      storages = scRepo.findAll();
      if (storages.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
        resp.setMessage(message);
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      } else {
        resp.setMessage("Data Found");
        resp.setStatus(true);
        resp.setStatusCode(200);
        data.setStorages(storages);
        resp.setData(data);
      }

    } catch (ErrorException e) {
      e.printStackTrace();
    }

    return resp;
  }

  @Override
  public ApiResponseModel saveRack(String rackName, String layerName, long rack_util_id, int user_id) {
    ErrorException ex1 = null;
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    RackEntity rackUtil = new RackEntity();
    RefRackEntity refRack = new RefRackEntity();
    RefRackEntity newRefRack = new RefRackEntity();
    CargoActivityLogsEntity logs = new CargoActivityLogsEntity();

    float tempV = 0;
    float volume = 0;
    try {
      rackUtil = rRepo.findById(rack_util_id);

//      acceptances = aRepo.findByTxnHawbId(rackUtil.getTxnHawbId());
      logs = cargoRepo.findByMawbIdAndHawbId(rackUtil.getTxnMawbId(), rackUtil.getTxnHawbId());
      if (logs != null) {
        logs.setStoredDatetime(Timestamp.valueOf(date));
        logs.setHandledById(String.valueOf(user_id));
        logs.setUpdatedAt(Timestamp.valueOf(date));
        logs.setUpdatedById(user_id);
        cargoRepo.save(logs);
      } else {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Data not save", System.currentTimeMillis());
        throw ex1;
      }

//      if (Optracks.isPresent()) {
//        racks = (RackEntity) Optracks.get();
//      }
      if (rackUtil == null) {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Data not save", System.currentTimeMillis());
        throw ex1;
      }

      Optional OptrefRack = rrRepo.findById(rackUtil.getRefRackId());

      if (OptrefRack.isPresent()) {
        refRack = (RefRackEntity) OptrefRack.get();
      }

      tempV = refRack.getVolume() - rackUtil.getVolume();
      refRack.setVolume(tempV);
      refRack = rrRepo.save(refRack);

      if (refRack == null) {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Data not save", System.currentTimeMillis());
        throw ex1;
      }

      newRefRack = rrRepo.findByLayerNameAndRackName(layerName, rackName);
//      if (newRefRack.isPresent()) {
//        refRack = (RefRackEntity) newRefRack.get();
//      }
      volume = refRack.getVolume() + rackUtil.getVolume();
      refRack.setVolume(volume);
      refRack = rrRepo.save(refRack);
      if (refRack == null) {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Data not save", System.currentTimeMillis());
        throw ex1;
      }
      rackUtil.setRefRackId(newRefRack.getId());
      rackUtil = rRepo.save(rackUtil);

      if (rackUtil == null) {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Data not save", System.currentTimeMillis());
        throw ex1;
//        resp.setMessage("Failed");
//        resp.setStatus(false);
//        resp.setStatusCode(404);
//        return resp;
      }
      resp.setData(refRack);
      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
    } catch (ErrorException e) {
      if (ex1 == null) {
        ex1 = new ErrorException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Bad Request", System.currentTimeMillis());
      }
      throw ex1;
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
  public ApiResponseModel getRefRacks(boolean is_layer, String rackName) {
    ApiResponseModel resp = new ApiResponseModel();
    RefRackModel data = new RefRackModel();

    List<RefRackEntity> refRacks = new ArrayList<>();

    try {
      if (is_layer) {
        refRacks = rrRepo.findByRackName(rackName);
        if (refRacks.size() > 0) {
          data.setRefRacks(refRacks);
          resp.setData(data);
          resp.setMessage("Data Found");
          resp.setStatus(true);
          resp.setStatusCode(200);
        } else {
          resp.setMessage("NO DATA FOUND");
          resp.setStatus(false);
          resp.setStatusCode(404);
        }

      } else {
        refRacks = rrRepo.findAll();
        if (refRacks.size() > 0) {
          data.setRefRacks(refRacks);
          resp.setData(data);
          resp.setMessage("Data Found");
          resp.setStatus(true);
          resp.setStatusCode(200);
        } else {
          resp.setMessage("NO DATA FOUND");
          resp.setStatus(false);
          resp.setStatusCode(404);
        }
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
          resp.setMessage("Data Found");
          resp.setStatus(false);
          resp.setStatusCode(200);
        } else {
          message = "NO DATA FOUND";
          status = false;
          statusCode = 404;
          resp.setMessage(message);
          resp.setStatus(status);
          resp.setStatusCode(statusCode);
        }
      } else {
        rackDetails = rdRepo.findByMawbNumber(mawbNumber);
        if (rackDetails != null) {
          data.setRackDetails(rackDetails);
          resp.setData(data);
          resp.setMessage("Data Found");
          resp.setStatus(false);
          resp.setStatusCode(200);
        } else {
          message = "NO DATA FOUND1";
          status = false;
          statusCode = 404;
          resp.setMessage(message);
          resp.setStatus(status);
          resp.setStatusCode(statusCode);
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
      releaseCargo = rcRepo.findAll();
      if (releaseCargo.size() > 0) {
        data.setReleaseCargo(releaseCargo);
        resp.setData(data);
        resp.setMessage("DATA FOUND");
        resp.setStatus(true);
        resp.setStatusCode(200);
      } else {
        resp.setMessage("NO DATA FOUND");
        resp.setStatus(false);
        resp.setStatusCode(404);
      }
    } catch (ErrorException e) {
      e.printStackTrace();
    }
    return resp;
  }

  @Override
  public ApiResponseModel updateStoragerStatus(String hawbNumber, String mawbNumber, int user_id) {
    ErrorException ex1 = null;
    ApiResponseModel resp = new ApiResponseModel();
//    StorageLogsEntity storageLogs = new StorageLogsEntity();
//    CargoReleaseEntity cargoRelease = new CargoReleaseEntity();
    MawbEntity mawbDetails = new MawbEntity();
    HawbEntity hawbDetails = new HawbEntity();
    CargoActivityLogsEntity logs = new CargoActivityLogsEntity();
    LocalDateTime date = LocalDateTime.now();

    try {
      if (hawbNumber.equals("null") || hawbNumber.equals("")) {
        mawbDetails = mRepo.findByMawbNumber(mawbNumber);
        logs = cargoRepo.findByMawbIdAndLocationAndReleasedDatetimeNull(mawbDetails.getId(), "RELEASING");
        if (logs.getId() > 0) {
          logs.setReleasedDatetime(Timestamp.valueOf(date));
          logs.setUpdatedAt(Timestamp.valueOf(date));
          logs.setHandledById(String.valueOf(user_id));
          logs.setUpdatedById(user_id);
          cargoRepo.save(logs);

          resp.setMessage("Successfully Released");
          resp.setStatus(true);
          resp.setStatusCode(200);
        } else {
          ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Failed to RELEASE this cargo", System.currentTimeMillis());
          throw ex1;
        }

      } else {
        hawbDetails = hRepo.findByHawbNumber(hawbNumber);
        logs = cargoRepo.findByHawbIdAndLocationAndReleasedDatetimeNull(hawbDetails.getId(), "RELEASING");
        if (logs.getId() > 0) {
          logs.setReleasedDatetime(Timestamp.valueOf(date));
          logs.setUpdatedAt(Timestamp.valueOf(date));
          logs.setHandledById(String.valueOf(user_id));
          logs.setUpdatedById(user_id);
          cargoRepo.save(logs);

          resp.setMessage("Successfully Released");
          resp.setStatus(true);
          resp.setStatusCode(200);
        } else {
          ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Failed to RELEASE this cargo", System.currentTimeMillis());
          throw ex1;
        }

      }

    } catch (ErrorException e) {
      e.printStackTrace();
      if (ex1 == null) {
        ex1 = new ErrorException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Bad Request", System.currentTimeMillis());
      }
      throw ex1;
    }

    return resp;
  }

}
