/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.CargoConditionEntity;
import com.ascentdev.wims.entity.CargoImagesEntity;
import com.ascentdev.wims.entity.FlightStorageEntity;
import com.ascentdev.wims.entity.FlightsEntity;
import com.ascentdev.wims.entity.GetRacksEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.ImagesEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.RackDetailsEntity;
import com.ascentdev.wims.entity.RackUtilEntity;
import com.ascentdev.wims.entity.RefRackEntity;
import com.ascentdev.wims.entity.RefRackLayerEntity;
import com.ascentdev.wims.entity.ReleasingCargoEntity;
import com.ascentdev.wims.entity.StorageCargoEntity;
import com.ascentdev.wims.entity.TxnCargoVideosEntity;
import com.ascentdev.wims.error.ErrorException;
import com.ascentdev.wims.model.AddedRackModel;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoImagesModel;
import com.ascentdev.wims.model.RackDetailsModel;
import com.ascentdev.wims.model.RefRackModel;
import com.ascentdev.wims.model.ReleaseCargoModel;
import com.ascentdev.wims.model.StorageCargoModel;
import com.ascentdev.wims.repository.CargoActivityLogsRepository;
import com.ascentdev.wims.repository.CargoConditionRepository;
import com.ascentdev.wims.repository.CargoImagesRepository;
import com.ascentdev.wims.repository.FlightStorageRepository;
import com.ascentdev.wims.repository.GetRacksRepository;
import com.ascentdev.wims.repository.HawbRepository;
import com.ascentdev.wims.repository.ImagesRepository;
import com.ascentdev.wims.repository.JobAssignmentRepository;
import com.ascentdev.wims.repository.MawbRepository;
import com.ascentdev.wims.repository.RackDetailsRepository;
import com.ascentdev.wims.repository.ReleasingCargoRepository;
import com.ascentdev.wims.repository.StorageCargoRepository;
import com.ascentdev.wims.service.StoreCargoService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ascentdev.wims.repository.RackUtilRepository;
import com.ascentdev.wims.repository.RefShipmentStatusRepository;
import com.ascentdev.wims.repository.RefRackLayerRepository;
import com.ascentdev.wims.repository.RefRackRepository;
import com.ascentdev.wims.repository.TxnCargoVideosRepository;
import java.io.File;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ASCENT
 */
@Service
public class StoreCargoServiceImp implements StoreCargoService {

  boolean status = true;
  String message = "Success!";
  int statusCode = 200;

  String fileUploadPath = "C:\\wms_paircargo\\WMS_SUPPORTING_DOCUMENTS\\images\\";
  private static final String fileUploadPathVideo = "C:\\wms_paircargo\\WMS_SUPPORTING_DOCUMENTS\\videos\\" + ((DateTimeFormatter.ofPattern("yyyy-MM")).format(LocalDateTime.now()));

  @Autowired
  RackUtilRepository rRepo;

  @Autowired
  GetRacksRepository grRepo;

  @Autowired
  CargoActivityLogsRepository cargoActivityRepo;

  @Autowired
  ImagesRepository imgRepo;

  @Autowired
  TxnCargoVideosRepository vidRepo;

  @Autowired
  CargoImagesRepository ciRepo;

  @Autowired
  RefRackLayerRepository rrRepo;

  @Autowired
  RefRackRepository refRackRepo;

  @Autowired
  FlightStorageRepository fRepo;

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

  @Autowired
  RefShipmentStatusRepository shipmentRepo;

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
          String rackCode,
          long user_id,
          long cargo_activity_logs_id,
          int actual_pcs,
          String registry_number,
          int uld_id) {
    ErrorException ex1 = null;
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    RackUtilEntity rackUtil = new RackUtilEntity();
    RefRackLayerEntity rackLayer = new RefRackLayerEntity();
    RefRackEntity refRack = new RefRackEntity();
    CargoActivityLogsEntity logs = new CargoActivityLogsEntity();
    List<CargoActivityLogsEntity> logsList = new ArrayList<>();
    CargoActivityLogsEntity histLogs = new CargoActivityLogsEntity();
    MawbEntity mawb = new MawbEntity();
    FlightStorageEntity flights = new FlightStorageEntity();

    List<HawbEntity> hawbs = new ArrayList<>();

    try {
      flights = fRepo.findByFlightNumberAndRegistryNumber(flightNumber, registry_number);
      mawb = mRepo.findByMawbNumberAndRegistryNumber(mawbNumber, registry_number);
      hawbs = hRepo.findByMawbNumberAndHawbNumber(mawbNumber, hawb_number);
      histLogs = cargoRepo.findById(cargo_activity_logs_id);

      if (histLogs.getId() > 0) {
        histLogs.setStored(true);
        cargoRepo.save(histLogs);
      }

      logs.setReceivedReleasedDate(Timestamp.valueOf(date));
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
      logs.setActivityStatus("STORED");
      logs.setActualPcs(actual_pcs);
      logs.setStored(true);
      logs.setUldId(uld_id);
      logs.setStatusCode(histLogs.getStatusCode());
      logs.setFull(true);
      logs = cargoRepo.save(logs);
      logsList.add(logs);

      rackLayer = rrRepo.findByLayerNameAndRackName(layerName, rackName);
      refRack = refRackRepo.findByRackCode(rackCode);

      if (rackLayer != null) {

        rackUtil.setRefRackId(refRack.getId());
        rackUtil.setLocation(rackName + " - " + layerName);
        rackUtil.setRefRackLayerId(rackLayer.getId());
        rackUtil.setTxnMawbId(mawb.getId());
        rackUtil.setStoredDt(Timestamp.valueOf(date));
        rackUtil.setCreatedAt(Timestamp.valueOf(date));
        rackUtil.setStoredById(user_id);
        if (hawbs.size() > 0) {

          rackLayer.setVolume(rackLayer.getVolume() + hawbs.get(0).getActualVolume());
          rackUtil.setNoOfPieces(actual_pcs);
          rackUtil.setVolume(hawbs.get(0).getActualVolume());
          rackUtil.setTxnHawbId(hawbs.get(0).getId());

          rRepo.save(rackUtil);
          rrRepo.save(rackLayer);
          status = true;
          statusCode = 200;
          message = "Saved Successfully";

        } else {

          rackLayer.setVolume(rackLayer.getVolume() + mawb.getActualVolume());
          rackUtil.setNoOfPieces(actual_pcs);
          rackUtil.setVolume(mawb.getActualVolume());
          rackUtil.setTxnHawbId(0);

          rackUtil = rRepo.save(rackUtil);
          rrRepo.save(rackLayer);

          status = true;
          statusCode = 200;
          message = "Saved Successfully";

        }

      } else {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Data not save", System.currentTimeMillis());
        throw ex1;
      }

      resp.setCargoActivityLogsId(logs.getId());
      resp.setLogList(logsList);
      resp.setData(rackLayer);
      resp.setRackUtil(rackUtil);
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
  public ApiResponseModel saveAddedRack(
          List<AddedRackModel> addedRackModel,
          String mawbNumber,
          String flightNumber,
          String hawb_number,
          long user_id,
          long cargo_activity_logs_id,
          String registry_number, int uld_id, int actual_pcs) {

    ErrorException ex1 = null;
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    RackUtilEntity rackUtil = new RackUtilEntity();
    RefRackLayerEntity rackLayer = new RefRackLayerEntity();
    RefRackEntity rack = new RefRackEntity();
    CargoActivityLogsEntity logs = new CargoActivityLogsEntity();
    CargoActivityLogsEntity histLogs = new CargoActivityLogsEntity();
    MawbEntity mawb = new MawbEntity();
    HawbEntity hawb = new HawbEntity();
    FlightStorageEntity flights = new FlightStorageEntity();
    List<CargoActivityLogsEntity> logsList = new ArrayList<>();

    List<HawbEntity> hawbs = new ArrayList<>();

    float tempV = 0;
    float volume = 0;

    try {
      flights = fRepo.findByFlightNumberAndRegistryNumber(flightNumber, registry_number);
      mawb = mRepo.findByMawbNumberAndRegistryNumber(mawbNumber, registry_number);
      hawbs = hRepo.findByMawbNumberAndHawbNumber(mawbNumber, hawb_number);
      histLogs = cargoRepo.findById(cargo_activity_logs_id);

      if (histLogs.getId() > 0) {
        histLogs.setStored(true);
        cargoRepo.save(histLogs);
      }

      for (AddedRackModel a : addedRackModel) {
        logs = new CargoActivityLogsEntity();

        logs.setReceivedReleasedDate(Timestamp.valueOf(date));
        logs.setFlightId(flights.getId());

        logs.setUpdatedAt(Timestamp.valueOf(date));
        logs.setUpdatedById(user_id);
        logs.setCreatedAt(Timestamp.valueOf(date));
        logs.setCreatedById(user_id);
        logs.setLocation("STORING AREA");
        logs.setActivityStatus("STORED");
        logs.setActualPcs(a.getStoredPcs());
        logs.setStored(true);
        logs.setUldId(uld_id);
        logs.setStatusCode(histLogs.getStatusCode());
        logs.setMawbId(mawb.getId());

        rackLayer = rrRepo.findByLayerNameAndRackName(a.getLayer_name(), a.getRack_name());

        if (rackLayer != null) {
          rackUtil = new RackUtilEntity();
          rackUtil.setRefRackId(rackLayer.getRackId());
          rackUtil.setLocation(a.getRack_code() + " - " + a.getLayer_name());
          rackUtil.setRefRackLayerId(rackLayer.getId());
          rackUtil.setTxnMawbId(mawb.getId());
          rackUtil.setStoredDt(Timestamp.valueOf(date));
          rackUtil.setCreatedAt(Timestamp.valueOf(date));

          if (hawbs.size() > 0) {

            logs.setHawbId(hawbs.get(0).getId());

            rackLayer.setVolume(rackLayer.getVolume() + hawbs.get(0).getActualVolume());
            rackUtil.setNoOfPieces(a.getStoredPcs());
            rackUtil.setVolume(hawbs.get(0).getActualVolume());
            rackUtil.setTxnHawbId(hawbs.get(0).getId());

          } else {

            logs.setHawbId(0);

            rackLayer.setVolume(rackLayer.getVolume() + mawb.getActualVolume());
            rackUtil.setNoOfPieces(a.getStoredPcs());
            rackUtil.setVolume(mawb.getActualVolume());
            rackUtil.setTxnHawbId(0);

          }

          logs = cargoRepo.save(logs);
          logsList.add(logs);
          rRepo.save(rackUtil);
          rrRepo.save(rackLayer);

          status = true;
          statusCode = 200;
          message = "Saved Successfully";
        }
      }

      resp.setCargoActivityLogsId(logs.getId());
      resp.setData(logs);
      resp.setLogList(logsList);
      resp.setAddedRackModel(addedRackModel);
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
  public ApiResponseModel getImages(String mawbNumber, String hawbNumber,
          boolean isHawb, String registry_number
  ) {
    ApiResponseModel resp = new ApiResponseModel();
    CargoImagesModel data = new CargoImagesModel();

    List<CargoImagesEntity> images = new ArrayList<>();
    HawbEntity hawb = new HawbEntity();
    MawbEntity mawb = new MawbEntity();

    try {
      hawb = hRepo.findByHawbNumber(hawbNumber);
      mawb = mRepo.findByMawbNumberAndRegistryNumber(mawbNumber, registry_number);
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
  public ApiResponseModel getRefRacks(boolean is_layer, String rack_name
  ) {
    ApiResponseModel resp = new ApiResponseModel();
    RefRackModel data = new RefRackModel();

    List<RefRackLayerEntity> layers = new ArrayList<>();
    List<GetRacksEntity> refRacks = new ArrayList<>();

    try {
      if (is_layer) {
        layers = rrRepo.findByRackName(rack_name);
        if (layers.size() > 0) {
//          data.setLayers(layers);
//          resp.setData(data);
          resp.setLayers(layers);
          resp.setMessage("Data Found");
          resp.setStatus(true);
          resp.setStatusCode(200);
        } else {
          resp.setMessage("NO DATA FOUND");
          resp.setStatus(false);
          resp.setStatusCode(404);
        }

      } else {
        refRacks = grRepo.findAll();
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
  public ApiResponseModel getRackDetails(boolean isHawb, String hawbNumber,
          String mawbNumber
  ) {
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
  public ApiResponseModel updateStoragerStatus(String hawbNumber, String mawbNumber,
          long user_id, String registry_number
  ) {
    ErrorException ex1 = null;
    ApiResponseModel resp = new ApiResponseModel();
    MawbEntity mawbDetails = new MawbEntity();
    HawbEntity hawbDetails = new HawbEntity();
    CargoActivityLogsEntity logs = new CargoActivityLogsEntity();
    LocalDateTime date = LocalDateTime.now();

    List<HawbEntity> hawbs = new ArrayList<>();

    MawbEntity mawb1 = new MawbEntity();
    FlightsEntity flights = new FlightsEntity();

    try {
      if (hawbNumber.equals("null") || hawbNumber.equals("")) {
        mawb1 = mRepo.findByMawbNumberAndRegistryNumber(mawbNumber, registry_number);
        hawbs = hRepo.findByMawbNumberAndHawbNumber(mawbNumber, hawbNumber);

        logs.setReceivedReleasedDate(Timestamp.valueOf(date));
        logs.setFlightId(mawb1.getFlightId());
        logs.setMawbId(mawb1.getId());
        logs.setHawbId(hawbs.get(0).getId());
        logs.setUpdatedAt(Timestamp.valueOf(date));
        logs.setUpdatedById(user_id);
        logs.setCreatedAt(Timestamp.valueOf(date));
        logs.setCreatedById(user_id);
        logs.setLocation("RELEASING AREA");
        logs.setActivityStatus("RELEASED");
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
  public ApiResponseModel getCargoImages(long cargoActivityLogId
  ) {
    ApiResponseModel resp = new ApiResponseModel();
    CargoImagesModel data = new CargoImagesModel();

    List<CargoImagesEntity> images = new ArrayList<>();

    try {
      images = ciRepo.findByCargoActivityLogId(cargoActivityLogId);
      if (images.size() == 0) {
        resp.setMessage("No Images found!");
        resp.setStatus(false);
        resp.setStatusCode(404);
      } else {
        resp.setMessage("Images found!");
        resp.setStatus(true);
        resp.setStatusCode(200);
        data.setImages(images);
        resp.setData(data);
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
          String rackCode,
          int storedPcs,
          String remarks,
          String flightNumber,
          CargoActivityLogsEntity cargoLogs,
          MawbEntity mawbDetails,
          HawbEntity hawbDetails
  ) {
    ApiResponseModel resp = new ApiResponseModel();

    resp = saveRack(cargoLogs, mawbDetails.getMawbNumber(), flightNumber, hawbDetails.getHawbNumber(), rackName, layerName, rackCode, 0, 0, 0, "", 0);
    if (resp.isStatus()) {
      for (MultipartFile f : file) {
        ImagesEntity images = new ImagesEntity();
        String filename = f.getOriginalFilename();
        images.setFilePath(fileUploadPath + filename);
        images.setFileName(filename);
        images.setCargoActivityLogId(cargoConditionId);
        images.setRemarks(remarks);
        iRepo.save(images);
      }
    }

    return resp;
  }

  @Override
  public Integer uploadImage(MultipartFile[] file, String hawbNumber, String mawbNumber, List<ImagesEntity> imagesEntity, int uld_id, String registry_number) {
    Integer resp = 0;
    long id = 0;

    MawbEntity mawb = new MawbEntity();
    HawbEntity hawb = new HawbEntity();
    CargoActivityLogsEntity cal = new CargoActivityLogsEntity();
    List<CargoActivityLogsEntity> cargoList = new ArrayList<>();
    CargoConditionEntity condition1 = new CargoConditionEntity();
    ImagesEntity images = new ImagesEntity();
    List<ImagesEntity> imageList = new ArrayList<>();

    try {
      mawb = mRepo.findByMawbNumberAndRegistryNumber(mawbNumber, registry_number);
      if (!hawbNumber.equals("")) {
        hawb = hRepo.findByHawbNumber(hawbNumber);
      }

      if (hawb.getId() == 0) {
        cargoList = cargoActivityRepo.getByMawbIdAndActivityStatusAndUldId(mawb.getId(), "STORED", false, false, uld_id);
      } else {
        cargoList = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatusAndUldId(mawb.getId(), hawb.getId(), "STORED", uld_id);
      }
      cal = cargoList.get(cargoList.size() - 1);
      int count = 0;

      if (cargoList.size() > 1) {
        for (MultipartFile f : file) {
          String filename = f.getOriginalFilename();
          images.setFilePath(fileUploadPath + "\\" + filename);
          images.setFileName(filename);
          images.setCargoActivityLogId(cal.getId());
          images.setCargoConditionId(imagesEntity.get(count).getCargoConditionId());
          if (imagesEntity.get(count).getRemarks() != null && !imagesEntity.get(count).getRemarks().equals("")) {
            images.setRemarks(imagesEntity.get(count).getRemarks());
          } else {
            images.setRemarks(null);
          }

          imgRepo.save(images);
          images = new ImagesEntity();
          count++;
        }
      } else {
        for (MultipartFile f : file) {

          String filename = f.getOriginalFilename();
          images.setFilePath(fileUploadPath + "\\" + filename);
          images.setFileName(filename);
          images.setCargoActivityLogId(cal.getId());
          images.setCargoConditionId(imagesEntity.get(0).getCargoConditionId());
          if (imagesEntity.get(0).getRemarks() != null && !imagesEntity.get(0).getRemarks().equals("")) {
            images.setRemarks(imagesEntity.get(0).getRemarks());
          } else {
            images.setRemarks(null);
          }

          imgRepo.save(images);
          images = new ImagesEntity();
          count++;
        }
      }

      resp = 1;
    } catch (Exception e) {
      resp = 0;
    }
    return resp;

  }

  @Override
  public Integer uploadVideo(MultipartFile[] file, String hawbNumber, String mawbNumber, int uld_id, String registry_number) {
    Integer resp = 0;
    long id = 0;

    MawbEntity mawb = new MawbEntity();
    HawbEntity hawb = new HawbEntity();
    List<CargoActivityLogsEntity> cargoList = new ArrayList<>();
    TxnCargoVideosEntity vids = new TxnCargoVideosEntity();

    try {
      mawb = mRepo.findByMawbNumberAndRegistryNumber(mawbNumber, registry_number);
      if (!hawbNumber.equals("")) {
        hawb = hRepo.findByHawbNumber(hawbNumber);
      }

      if (hawb.getId() == 0) {
        cargoList = cargoActivityRepo.getByMawbIdAndActivityStatusAndUldId(mawb.getId(), "STORED", false, false, uld_id);
      } else {
        cargoList = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatusAndUldId(mawb.getId(), hawb.getId(), "STORED", uld_id);
      }

      for (MultipartFile f : file) {
        String filename = f.getOriginalFilename();
        vids.setFilePath(fileUploadPathVideo + "\\" + filename);
        vids.setFileName(filename);
        vids.setCargoActivityLogId(cargoList.get(0).getId());

        vidRepo.save(vids);
        vids = new TxnCargoVideosEntity();
      }

      resp = 1;
    } catch (Exception e) {
      resp = 0;
    }
    return resp;

  }

  @Override
  public Integer uploadAddedImage(MultipartFile[] file, String hawbNumber, String mawbNumber, List<ImagesEntity> imagesEntity, int uld_id, String registry_number) {
    Integer resp = 0;
    long id = 0;

    MawbEntity mawb = new MawbEntity();
    HawbEntity hawb = new HawbEntity();
    CargoActivityLogsEntity cal = new CargoActivityLogsEntity();
    List<CargoActivityLogsEntity> cargoList = new ArrayList<>();
    CargoConditionEntity condition1 = new CargoConditionEntity();
    ImagesEntity images = new ImagesEntity();
    List<ImagesEntity> imageList = new ArrayList<>();

    imageList = imagesEntity;

    try {
      mawb = mRepo.findByMawbNumberAndRegistryNumber(mawbNumber, registry_number);
      if (!hawbNumber.equals("")) {
        hawb = hRepo.findByHawbNumber(hawbNumber);
      }

      if (hawb.getId() == 0) {
        cargoList = cargoActivityRepo.getByMawbIdAndActivityStatusAndUldId(mawb.getId(), "STORED", false, false, uld_id);
      } else {
        cargoList = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatusAndUldId(mawb.getId(), hawb.getId(), "STORED", uld_id);
      }
      int count = 0;

      for (MultipartFile f : file) {

        String filename = f.getOriginalFilename();
        images.setFilePath(fileUploadPath + "\\" + filename);
        images.setFileName(filename);
        images.setCargoActivityLogId(cargoList.get(count).getId());
        images.setCargoConditionId(imageList.get(count).getCargoConditionId());

        if (imageList.get(count).getRemarks() != null && !"".equals(imageList.get(count).getRemarks())) {
          images.setRemarks(imageList.get(count).getRemarks());

        } else {
          images.setRemarks(null);
        }

        imgRepo.save(images);
        images = new ImagesEntity();
        count++;
      }

      resp = 1;
    } catch (Exception e) {
      resp = 0;
    }
    return resp;

  }

  @Override
  public Integer uploadAddedVideo(MultipartFile[] file, String hawbNumber, String mawbNumber, int uld_id, String registry_number) {
    Integer resp = 0;
    long id = 0;

    MawbEntity mawb = new MawbEntity();
    HawbEntity hawb = new HawbEntity();
    List<CargoActivityLogsEntity> cargoList = new ArrayList<>();
    TxnCargoVideosEntity vids = new TxnCargoVideosEntity();

    try {
      mawb = mRepo.findByMawbNumberAndRegistryNumber(mawbNumber, registry_number);
      if (!hawbNumber.equals("")) {
        hawb = hRepo.findByHawbNumber(hawbNumber);
      }

      if (hawb.getId() == 0) {
        cargoList = cargoActivityRepo.getByMawbIdAndActivityStatusAndUldId(mawb.getId(), "STORED", false, false, uld_id);
      } else {
        cargoList = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatusAndUldId(mawb.getId(), hawb.getId(), "STORED", uld_id);
      }

      int count = 0;
      for (MultipartFile f : file) {
        String filename = f.getOriginalFilename();
        vids.setFilePath(fileUploadPathVideo + "\\" + filename);
        vids.setFileName(filename);
        vids.setCargoActivityLogId(cargoList.get(count).getId());

        vidRepo.save(vids);
        vids = new TxnCargoVideosEntity();
        count++;
      }

      resp = 1;
    } catch (Exception e) {
      resp = 0;
    }
    return resp;

  }
}
