/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.entity.Acceptance;
import com.ascentdev.wims.entity.CargoCategoryEntity;
import com.ascentdev.wims.entity.CargoClassEntity;
import com.ascentdev.wims.entity.CargoConditionEntity;
import com.ascentdev.wims.entity.CargoImagesEntity;
import com.ascentdev.wims.entity.CargoStatusEntity;
import com.ascentdev.wims.entity.UldImagesEntity;
import com.ascentdev.wims.entity.FlightsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.RackEntity;
import com.ascentdev.wims.entity.ReceivingLogsEntity;
import com.ascentdev.wims.entity.RefRackEntity;
import com.ascentdev.wims.entity.StorageLogsEntity;
import com.ascentdev.wims.entity.UldTypeEntity;
import com.ascentdev.wims.entity.UldsEntity;
import com.ascentdev.wims.error.ErrorException;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoCategoryModel;
import com.ascentdev.wims.model.CargoClassModel;
import com.ascentdev.wims.model.CargoConditionModel;
import com.ascentdev.wims.model.CargoStatusModel;
import com.ascentdev.wims.model.HawbModel;
import com.ascentdev.wims.model.MawbModel;
import com.ascentdev.wims.model.SearchFlightsModel;
import com.ascentdev.wims.model.UldTypeModel;
import com.ascentdev.wims.model.UldsModel;
import com.ascentdev.wims.repository.AcceptanceRepository;
import com.ascentdev.wims.repository.CargoCategoryRepository;
import com.ascentdev.wims.repository.CargoClassRepository;
import com.ascentdev.wims.repository.CargoConditionRepository;
import com.ascentdev.wims.repository.CargoImagesRepository;
import com.ascentdev.wims.repository.CargoStatusRepository;
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
import com.ascentdev.wims.repository.RackRepository;
import com.ascentdev.wims.repository.ReceivingLogsRepository;
import com.ascentdev.wims.repository.RefRackRepository;
import com.ascentdev.wims.repository.StorageLogsRepository;
import com.ascentdev.wims.repository.UldTypeRepository;
import java.sql.Timestamp;
import java.time.LocalTime;
import com.ascentdev.wims.repository.UldImagesRepository;

/**
 *
 * @author
 * ASCENT
 */
@Service
public class ReceiveCargoServiceImp implements ReceiveCargoService {

  boolean status = true;
  String message = "Success!";
  int statusCode = 200;

  String fileUploadPath = "C:\\wms_paircargo\\SUPPORTING_DOCUMENTS\\images";

  @Autowired
  UldsRepository uRepo;

  @Autowired
  MawbRepository mRepo;

  @Autowired
  FlightsRepository fRepo;

  @Autowired
  UldImagesRepository iRepo;

  @Autowired
  CargoImagesRepository ciRepo;

  @Autowired
  CargoConditionRepository cargoRepo;

  @Autowired
  StorageLogsRepository slRepo;

  @Autowired
  HawbRepository hRepo;

  @Autowired
  RefRackRepository rrRepo;

  @Autowired
  RackRepository rRepo;

  @Autowired
  CargoCategoryRepository ccRepo;

  @Autowired
  CargoClassRepository classRepo;

  @Autowired
  AcceptanceRepository aRepo;

  @Autowired
  CargoStatusRepository csRepo;

  @Autowired
  UldTypeRepository utRepo;

  @Autowired
  ReceivingLogsRepository rlRepo;

  @Override
  public ApiResponseModel searchFlights(String userId) {
    ApiResponseModel resp = new ApiResponseModel();
    SearchFlightsModel data = new SearchFlightsModel();

    List<FlightsEntity> flights = new ArrayList<>();
    List<UldsEntity> ulds = new ArrayList<>();

    try {
      flights = fRepo.findByUserId(userId);
      if (flights.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setFlights(flights);
      }

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
      ulds = uRepo.findByFlightNumber(flightNumber);
      if (ulds.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setUldList(ulds);
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
  public ApiResponseModel getMawbs(boolean isUld, String uldNumber, String flightNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    MawbModel data = new MawbModel();

    List<MawbEntity> mawb = new ArrayList<>();

    try {
      if (isUld) {
        mawb = mRepo.findByUldNumber(uldNumber);
        if (mawb != null) {
          data.setMawbs(mawb);
          resp.setData(data);
          resp.setMessage(message);
          resp.setStatus(status);
          resp.setStatusCode(statusCode);
        }
      } else {
        mawb = mRepo.findByFlightNumber(flightNumber);
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
      }
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
      hawbs = hRepo.findByMawbNumber(mawbNumber);
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

//  @Override
//  public ApiResponseModel saveImage(long userId,
//          long txn_cargo_manifest_details_id,
//          String registryNumber,
//          MultipartFile[] file,
//          long fileType,
//          long cargoConditionId,
//          long uldTypeId) {
//    ApiResponseModel resp = new ApiResponseModel();
//    LocalDateTime date = LocalDateTime.now();
//
//    try {
//      for (MultipartFile f : file) {
//        ImagesEntity images = new ImagesEntity();
//        String filename = f.getOriginalFilename();
//        images.setTxnCargoManifestId(txn_cargo_manifest_details_id);
//        images.setRegistryNumber(registryNumber);
//        images.setFilePath(fileUploadPath + "/" + filename);
//        images.setFileName(filename);
//        images.setUserId(userId);
//        iRepo.save(images);
//        saveImage(f);
//      }
//      resp.setMessage(message);
//      resp.setStatus(status);
//      resp.setStatusCode(statusCode);
//      resp.setData(1);
//    } catch (ErrorException e) {
//      resp.setMessage("Image Did Not Upload");
//      resp.setStatus(false);
//      resp.setStatusCode(404);
//      resp.setData(0);
//    }
//    return resp;
//  }
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
  public ApiResponseModel confirmCargo(Acceptance acceptance) {
    ApiResponseModel resp = new ApiResponseModel();
    String storager_status = "Ongoing";
    LocalTime time = LocalTime.now();
    LocalDateTime date = LocalDateTime.now();

    List<MawbEntity> mawbs = new ArrayList<>();
    List<HawbEntity> hawbs = new ArrayList<>();
    MawbEntity mawb = new MawbEntity();
    StorageLogsEntity storage = new StorageLogsEntity();
    List<RefRackEntity> refRack = new ArrayList<>();
    List<Acceptance> acceptances = new ArrayList<>();
    List<RackEntity> rackList = new ArrayList<>();

    RackEntity rack = new RackEntity();

    float tempV = 0;

    int data = 0;

    try {
      refRack = rrRepo.findAll();
      mawbs = mRepo.findAll();
      hawbs = hRepo.findByMawbNumber(mawbs.get(0).getMawbNumber());

      storage.setFlightNumber(mawbs.get(0).getFlightNumber());
      storage.setRegistryNumber(mawbs.get(0).getRegistryNumber());
      storage.setMawbNumber(mawbs.get(0).getMawbNumber());
      if (hawbs.size() > 0) {
        storage.setHawbNumber(hawbs.get(0).getHawbNumber());
      }
      storage.setStoragerStatus(storager_status);
      storage = slRepo.save(storage);

      acceptance.setBookedPcs(mawb.getNumberOfPackages());
      acceptance.setCargoStatus(3);
      acceptance = aRepo.save(acceptance);
      acceptances.add(acceptance);

      if (acceptances.size() > 0) {
        rack = new RackEntity();
        rack.setNoOfPieces(acceptance.getActualPcs());
        rack.setTxnHawbId(acceptance.getTxnHawbId());
        rack.setTxnMawbId(acceptance.getTxnMawbId());
        rack.setStoredDt(Timestamp.valueOf(date));
        rack.setCreatedAt(Timestamp.valueOf(date));
        rack.setStoredById(acceptance.getUserId());
        //set value of rack from txn_acceptance
        rackList.add(rack);
      } else {
        resp.setMessage("Failed");
        resp.setStatus(false);
        resp.setStatusCode(404);
        return resp;

      }
      for (RefRackEntity r : refRack) {
        tempV = r.getMaxVolume() - r.getVolume();
        if (tempV >= acceptance.getVolume()) {
          r.setVolume(r.getVolume() + acceptance.getVolume());

          if (rackList.size() > 0) {
            for (RackEntity re : rackList) {
              re.setRefRackId(r.getId());
              re.setVolume(acceptance.getVolume());
              re = rRepo.save(re);
            }
          } else {
            rack = new RackEntity();
            rack.setNoOfPieces(acceptance.getActualPcs());
            rack.setRefRackId(r.getId());
            rack.setVolume(acceptance.getVolume());
            rack = rRepo.save(rack);
          }

          r = rrRepo.save(r);
          data = 1;
          status = true;
          message = "Saved Successfully";
          break;
        }

      }
      resp.setData(data);
      resp.setMessage(message);
      resp.setStatus(status);
      resp.setStatusCode(statusCode);

    } catch (ErrorException e) {
      resp.setData(status);
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
          long uldConditionId,
          String flightNumber,
          String uldNumber,
          String remarks) {
    ApiResponseModel resp = new ApiResponseModel();

    try {
      for (MultipartFile f : file) {
        UldImagesEntity images = new UldImagesEntity();
        String filename = f.getOriginalFilename();
        images.setFilePath(fileUploadPath + "/" + filename);
        images.setFileName(filename);
        images.setRemarks(remarks);
        images.setUldConditionId(uldConditionId);
        images.setFlightNumber(flightNumber);
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
          String mawbNumber,
          String hawbNumber,
          String flightNumber,
          String remarks) {
    ApiResponseModel resp = new ApiResponseModel();

    try {
      for (MultipartFile f : file) {
        CargoImagesEntity images = new CargoImagesEntity();
        String filename = f.getOriginalFilename();
        images.setFilePath(fileUploadPath + "/" + filename);
        images.setFileName(filename);
        images.setFlightNumber(flightNumber);
        images.setCargoConditionId(cargoConditionId);
        images.setMawbNumber(mawbNumber);
        images.setHawbNumber(hawbNumber);
        images.setRemarks(remarks);
        ciRepo.save(images);
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

  @Override
  public ApiResponseModel getCargoCategory() {
    ApiResponseModel resp = new ApiResponseModel();
    CargoCategoryModel data = new CargoCategoryModel();

    List<CargoCategoryEntity> category = new ArrayList<>();

    try {
      category = ccRepo.findAll();
      if (category.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setCategory(category);
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
  public ApiResponseModel getCargoClass() {
    ApiResponseModel resp = new ApiResponseModel();
    CargoClassModel data = new CargoClassModel();

    List<CargoClassEntity> cargoClass = new ArrayList<>();

    try {
      cargoClass = classRepo.findAll();
      if (cargoClass.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        data.setCargoClass(cargoClass);
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
  public ApiResponseModel getCargoStatus() {
    ApiResponseModel resp = new ApiResponseModel();
    CargoStatusModel data = new CargoStatusModel();

    List<CargoStatusEntity> statuses = new ArrayList<>();

    try {
      statuses = csRepo.findAll();
      if (statuses.size() > 0) {
        data.setStatus(statuses);
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
  public ApiResponseModel getUldType() {
    ApiResponseModel resp = new ApiResponseModel();
    UldTypeModel data = new UldTypeModel();

    List<UldTypeEntity> types = new ArrayList<>();

    try {
      types = utRepo.findAll();
      if (types.size() > 0) {
        data.setTypes(types);
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
  public ApiResponseModel saveUldNumber(UldsEntity ulds, String[] mawbs) {
    ApiResponseModel resp = new ApiResponseModel();

    try {
      UldsEntity u = uRepo.save(ulds);
      u.setTotalMawb(mawbs.length);
      MawbEntity m;
      for (int i = 0; i < mawbs.length; i++) {
        String mawbNumber = mawbs[i];
        m = mRepo.findByMawbNumber(mawbNumber);
        m.setUldNumber(u.getUldNo());
        mRepo.save(m);

        resp.setMawbs(mawbs);

        if (u.getId() > 0) {
          resp.setData(u);
          resp.setMessage("SUCCESSFULLY ADDED ULD NO. [ " + u.getUldNo() + " ]");
          resp.setStatus(status);
          resp.setStatusCode(statusCode);

        } else {
          resp.setMessage("NOT SUCCESSFULLY ADDED");
          resp.setStatus(false);
          resp.setStatusCode(404);
        }
      }

    } catch (ErrorException e) {
      e.printStackTrace();
      resp.setMessage("NOT SUCCESSFULLY ADDED");
      resp.setStatus(false);
      resp.setStatusCode(404);
    }

    return resp;
  }

  @Override
  public ApiResponseModel updateReceivingStatus(String registryNumber, boolean isConfirmed) {
    ApiResponseModel resp = new ApiResponseModel();
    ReceivingLogsEntity receivingLogs = new ReceivingLogsEntity();

    try {
      receivingLogs = rlRepo.findByRegistryNumber(registryNumber);
      if (receivingLogs.getId() > 0) {
        if (isConfirmed) {
          receivingLogs.setReceiverStatus("Done");
          rlRepo.save(receivingLogs);
        } else {
          receivingLogs.setReceiverStatus("Ongoing");
          rlRepo.save(receivingLogs);
        }

      } else {
        resp.setMessage("FAILED TO UPDATE STATUS");
        resp.setStatus(false);
        resp.setStatusCode(404);
      }
      resp.setData(receivingLogs);
      resp.setMessage("RECEIVING STATUS UPDATED");
      resp.setStatus(status);
      resp.setStatusCode(statusCode);
    } catch (ErrorException e) {
      e.printStackTrace();
      resp.setMessage("FAILED TO UPDATE STATUS");
      resp.setStatus(false);
      resp.setStatusCode(404);
    }

    return resp;
  }

  @Override
  public ApiResponseModel updateUldNumber(UldsEntity ulds, String uldNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    UldsEntity u = new UldsEntity();
    List<MawbEntity> m = new ArrayList<>();
    MawbEntity me = new MawbEntity();

    try {
      u = uRepo.findByUldNo(uldNumber);
      m = mRepo.findByUldNumber(uldNumber);

      for (int i = 0; i < m.size(); i++) {
        String mawbNumber = m.get(i).getMawbNumber();
        me = mRepo.findByMawbNumber(mawbNumber);
        me.setUldNumber(ulds.getUldNo());
        mRepo.save(me);
       
      }

      if (u.getId() > 0) {
        u.setUldNo(ulds.getUldNo());
        u.setUldType(ulds.getUldType());
        uRepo.save(u);

        resp.setData(u);
        resp.setMessage("ULD NUMBER UPDATED");
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      } else {
        resp.setMessage("FAILED TO UPDATE ULD NUMBER");
        resp.setStatus(false);
        resp.setStatusCode(404);
      }

    } catch (ErrorException e) {
      e.printStackTrace();
      resp.setMessage("FAILED TO UPDATE ULD NUMBER");
      resp.setStatus(false);
      resp.setStatusCode(404);
    }

    return resp;
  }

}
