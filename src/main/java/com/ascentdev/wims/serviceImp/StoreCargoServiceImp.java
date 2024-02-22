/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.CargoConditionEntity;
import com.ascentdev.wims.entity.CargoImagesEntity;
import com.ascentdev.wims.entity.FlightsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.ImagesEntity;
import com.ascentdev.wims.entity.JobAssignmentEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.RackDetailsEntity;
import com.ascentdev.wims.entity.RackEntity;
import com.ascentdev.wims.entity.RefRackEntity;
import com.ascentdev.wims.entity.ReleasingCargoEntity;
import com.ascentdev.wims.entity.StorageCargoEntity;
import com.ascentdev.wims.error.ErrorException;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoImagesModel;
import com.ascentdev.wims.model.RackDetailsModel;
import com.ascentdev.wims.model.RefRackModel;
import com.ascentdev.wims.model.ReleaseCargoModel;
import com.ascentdev.wims.model.StorageCargoModel;
import com.ascentdev.wims.repository.CargoActivityLogsRepository;
import com.ascentdev.wims.repository.CargoConditionRepository;
import com.ascentdev.wims.repository.CargoImagesRepository;
import com.ascentdev.wims.repository.FlightsRepository;
import com.ascentdev.wims.repository.HawbRepository;
import com.ascentdev.wims.repository.ImagesRepository;
import com.ascentdev.wims.repository.JobAssignmentRepository;
import com.ascentdev.wims.repository.MawbRepository;
import com.ascentdev.wims.repository.RackDetailsRepository;
import com.ascentdev.wims.repository.RackRepository;
import com.ascentdev.wims.repository.RefRackRepository;
import com.ascentdev.wims.repository.ReleasingCargoRepository;
import com.ascentdev.wims.repository.StorageCargoRepository;
import com.ascentdev.wims.service.StoreCargoService;
import com.ascentdev.wims.utils.Dates;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ASCENT
 */
@Service
public class StoreCargoServiceImp implements StoreCargoService {

  boolean status = true;
  String message = "Success!";
  int statusCode = 200;

  String fileUploadPath = "C:\\wms_paircargo\\SUPPORTING_DOCUMENTS\\images\\";

  @Autowired
  RackRepository rRepo;

  @Autowired
  CargoActivityLogsRepository cargoActivityRepo;

  @Autowired
  ImagesRepository imgRepo;

  @Autowired
  CargoImagesRepository ciRepo;

  @Autowired
  RefRackRepository rrRepo;

  @Autowired
  FlightsRepository fRepo;

  @Autowired
  StorageCargoRepository scRepo;

  @Autowired
  RackDetailsRepository rdRepo;

  @Autowired
  ReleasingCargoRepository rcRepo;

  @Autowired
  CargoActivityLogsRepository cargoRepo;

  @Autowired
  MawbRepository mRepo;

  @Autowired
  HawbRepository hRepo;

  @Autowired
  ImagesRepository iRepo;

  @Autowired
  CargoConditionRepository ccRepo;

  @Autowired
  JobAssignmentRepository jaRepo;

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
  public ApiResponseModel saveRack(CargoActivityLogsEntity cargoLogs,
          String mawbNumber,
          String flightNumber,
          String hawb_number,
          String rackName,
          String layerName,
          long rack_util_id,
          long user_id) {
    ErrorException ex1 = null;
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    RackEntity rackUtil = new RackEntity();
    RefRackEntity refRack = new RefRackEntity();
    RefRackEntity newRefRack = new RefRackEntity();
    CargoActivityLogsEntity logs = new CargoActivityLogsEntity();
    MawbEntity mawb = new MawbEntity();
    HawbEntity hawb = new HawbEntity();
    FlightsEntity flights = new FlightsEntity();
    List<JobAssignmentEntity> jobAssigns = new ArrayList<>();

    List<HawbEntity> hawbs = new ArrayList<>();

    float tempV = 0;
    float volume = 0;

    try {
      rackUtil = rRepo.findById(rack_util_id);
      flights = fRepo.findByFlightNumber(flightNumber);
      mawb = mRepo.findByMawbNumber(mawbNumber);
      hawbs = hRepo.findByMawbNumberAndHawbNumber(mawbNumber, hawb_number);
      jobAssigns = jaRepo.findByAssignedUserIdAndFlightId(user_id, flights.getId());

//      logs = cargoRepo.getByMawbIdAndHawbId(rackUtil.getTxnMawbId(), rackUtil.getTxnHawbId()).get(0);
      logs.setReceivedReleasedDate(Timestamp.valueOf(date));
      logs.setHandledById(jobAssigns.get(0).getId());
      logs.setFlightId(flights.getId());
      logs.setMawbId(mawb.getId());
      if (hawbs.size() != 0) {
        logs.setHawbId(hawbs.get(0).getId());
      } else {
        logs.setHawbId(0);
      }

      logs.setUpdatedAt(Timestamp.valueOf(date));
      logs.setUpdatedById(user_id);
      logs.setCreatedAt(Timestamp.valueOf(date));
      logs.setCreatedById(user_id);
      logs.setLocation("STORING AREA");
      logs.setActivityStatus("STORING");
      cargoRepo.save(logs);

      if (rackUtil == null) {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Data not save", System.currentTimeMillis());
        throw ex1;
      }

//      Optional OptrefRack = rrRepo.findById(rackUtil.getRefRackId());
      RefRackEntity OptrefRack = rrRepo.findById(rackUtil.getRefRackId());

      if (OptrefRack != null) {
        refRack = OptrefRack;
      }

      tempV = refRack.getVolume() - rackUtil.getVolume();
      refRack.setVolume(tempV);
      refRack = rrRepo.save(refRack);

      if (refRack == null) {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Data not save", System.currentTimeMillis());
        throw ex1;
      }

      newRefRack = rrRepo.findByLayerNameAndRackName(layerName, rackName);

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
    HawbEntity hawb = new HawbEntity();
    MawbEntity mawb = new MawbEntity();

    try {
      hawb = hRepo.findByHawbNumber(hawbNumber);
      mawb = mRepo.findByMawbNumber(mawbNumber);
      if (isHawb) {
        images = ciRepo.searchHawbId(hawb.getId());
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
        images = ciRepo.searchMawbId(mawb.getId());
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
  public ApiResponseModel updateStoragerStatus(String hawbNumber, String mawbNumber, long user_id) {
    ErrorException ex1 = null;
    ApiResponseModel resp = new ApiResponseModel();
//    StorageLogsEntity storageLogs = new StorageLogsEntity();
//    CargoReleaseEntity cargoRelease = new CargoReleaseEntity();
    MawbEntity mawbDetails = new MawbEntity();
    HawbEntity hawbDetails = new HawbEntity();
    CargoActivityLogsEntity logs = new CargoActivityLogsEntity();
    LocalDateTime date = LocalDateTime.now();

    List<JobAssignmentEntity> jobAssigns = new ArrayList<>();
    List<HawbEntity> hawbs = new ArrayList<>();

    MawbEntity mawb1 = new MawbEntity();
    FlightsEntity flights = new FlightsEntity();

    try {
      if (hawbNumber.equals("null") || hawbNumber.equals("")) {
        mawb1 = mRepo.findByMawbNumber(mawbNumber);
        hawbs = hRepo.findByMawbNumberAndHawbNumber(mawbNumber, hawbNumber);
//        logs = cargoRepo.findByMawbIdAndLocationAndReceivedReleasedDateNull(mawbDetails.getId(), "RELEASING AREA");

        logs.setReceivedReleasedDate(Timestamp.valueOf(date));
        logs.setHandledById(jobAssigns.get(0).getId());
        logs.setFlightId(mawb1.getFlightId());
        logs.setMawbId(mawb1.getId());
        logs.setHawbId(hawbs.get(0).getId());
        logs.setUpdatedAt(Timestamp.valueOf(date));
        logs.setUpdatedById(user_id);
        logs.setCreatedAt(Timestamp.valueOf(date));
        logs.setCreatedById(user_id);
        logs.setLocation("RELEASING AREA");
        logs.setActivityStatus("RELEASING");
        cargoRepo.save(logs);

        resp.setMessage("Successfully Released");
        resp.setStatus(true);
        resp.setStatusCode(200);

      } else {
        hawbDetails = hRepo.findByHawbNumber(hawbNumber);
        logs = cargoRepo.findByHawbIdAndLocationAndReceivedReleasedDateNull(hawbDetails.getId(), "RELEASING AREA");
        if (!logs.equals(null) || logs != null) {
          if (logs.getId() > 0) {
            logs.setReceivedReleasedDate(Timestamp.valueOf(date));
            logs.setUpdatedAt(Timestamp.valueOf(date));
            logs.setHandledById(user_id);
            logs.setUpdatedById((long) user_id);
            logs.setActivityStatus("RELEASING");
            cargoRepo.save(logs);

            resp.setMessage("Successfully Released");
            resp.setStatus(true);
            resp.setStatusCode(200);
          } else {
            resp.setMessage("Failed to RELEASE this cargo");
            resp.setStatus(false);
            resp.setStatusCode(404);
          }
        } else {
          resp.setMessage("Failed to RELEASE this cargo");
          resp.setStatus(false);
          resp.setStatusCode(404);
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

  @Override
  public ApiResponseModel getCargoImages(long cargoActivityLogId) {
    ApiResponseModel resp = new ApiResponseModel();
    CargoImagesModel data = new CargoImagesModel();

    List<CargoImagesEntity> images = new ArrayList<>();

    try {
      images = ciRepo.findByCargoActivityLogId(cargoActivityLogId);
      if (images.size() == 0) {
        message = "No Data to show";
        status = false;
        statusCode = 400;
      } else {
        resp.setMessage("Images found!");
        resp.setStatus(true);
        resp.setStatusCode(200);
        resp.setData(data);
        data.setImages(images);
      }
    } catch (ErrorException e) {
      e.printStackTrace();
    }

    return resp;
  }

  @Override
  public ApiResponseModel saveStorageImages(MultipartFile[] file,
          int cargoConditionId,
          long userId,
          String rackName,
          String layerName,
          int storedPcs,
          String remarks,
          String flightNumber,
          CargoActivityLogsEntity cargoLogs,
          MawbEntity mawbDetails,
          HawbEntity hawbDetails) {
    ApiResponseModel resp = new ApiResponseModel();

    resp = saveRack(cargoLogs, mawbDetails.getMawbNumber(), flightNumber, hawbDetails.getHawbNumber(), rackName, layerName, 0, 0);
    if (resp.isStatus()) {
      for (MultipartFile f : file) {
        ImagesEntity images = new ImagesEntity();
        String filename = f.getOriginalFilename();
        images.setFilePath(fileUploadPath + filename);
        images.setFileName(filename);
        images.setCargoActivityLogId(cargoConditionId);
        images.setRemarks(remarks);
        iRepo.save(images);
        saveImage(f);
      }
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
  public Integer uploadImage(MultipartFile[] file, long hawbId, String mawbNumber, List<ImagesEntity> imagesEntity) {
    Integer resp = 0;
    long id = 0;

    MawbEntity mawb = new MawbEntity();
    CargoActivityLogsEntity cal = new CargoActivityLogsEntity();
    List<CargoActivityLogsEntity> cargoList = new ArrayList<>();
    CargoConditionEntity condition1 = new CargoConditionEntity();
    ImagesEntity images = new ImagesEntity();
    List<ImagesEntity> imageList = new ArrayList<>();

    try {
      mawb = mRepo.findByMawbNumber(mawbNumber);
      if (hawbId == 0) {
        cargoList = cargoActivityRepo.findByMawbIdAndActivityStatus(mawb.getId(), "STORING");
      } else {
        cargoList = cargoActivityRepo.findByMawbIdAndHawbIdAndActivityStatus(mawb.getId(), hawbId, "STORING");
      }
      cal = cargoList.get(cargoList.size() - 1);
      int count = 0;

      for (MultipartFile f : file) {

        String filename = f.getOriginalFilename();
        images.setFilePath(fileUploadPath + "\\" + filename);
        images.setFileName(filename);
        images.setCargoActivityLogId(cal.getId());
        images.setCargoConditionId(imagesEntity.get(count).getCargoConditionId());
        images.setRemarks(imagesEntity.get(count).getRemarks());
        imgRepo.save(images);
        saveImage(f);
        images = new ImagesEntity();
        count++;
      }

//      for (int i = 0; i < imagesEntity.size(); i++) {
//
////        condition1 = ccRepo.findByCondition(imagesEntity.get(i).);
//        images.setCargoConditionId(imagesEntity.get(i).getCargoConditionId());
//        images.setRemarks(imagesEntity.get(i).getRemarks());
//        imgRepo.save(images);
//        images = new ImagesEntity();
//      }
      resp = 1;
    } catch (Exception e) {
      resp = 0;
    }
    return resp;

  }

  @Override
  public ApiResponseModel saveReleaseCargo(String mawbNumber, String hawbNumber, String flightNumber, long userId) {
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    List<JobAssignmentEntity> jobAssigns = new ArrayList<>();
    List<HawbEntity> hawbs = new ArrayList<>();

    MawbEntity mawb1 = new MawbEntity();
    FlightsEntity flights = new FlightsEntity();
    CargoActivityLogsEntity logs = new CargoActivityLogsEntity();

    try {
      flights = fRepo.findByFlightNumber(flightNumber);
      mawb1 = mRepo.findByMawbNumber(mawbNumber);
      hawbs = hRepo.findByMawbNumberAndHawbNumber(mawbNumber, hawbNumber);

      logs.setReceivedReleasedDate(Timestamp.valueOf(date));
      logs.setHandledById(jobAssigns.get(0).getId());
      logs.setFlightId(flights.getId());
      logs.setMawbId(mawb1.getId());
      logs.setHawbId(hawbs.get(0).getId());
      logs.setUpdatedAt(Timestamp.valueOf(date));
      logs.setUpdatedById(userId);
      logs.setCreatedAt(Timestamp.valueOf(date));
      logs.setCreatedById(userId);
      logs.setLocation("RELEASING AREA");
      logs.setActivityStatus("RELEASING");
      cargoRepo.save(logs);

    } catch (ErrorException e) {
      e.printStackTrace();
    }

    return resp;
  }

}
