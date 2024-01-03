/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.entity.CargoActivityLogsEntity;
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
import com.ascentdev.wims.entity.RefULDEntity;
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
import com.ascentdev.wims.repository.CargoActivityLogsRepository;
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
import com.ascentdev.wims.repository.RefULDRepository;
import com.ascentdev.wims.repository.StorageLogsRepository;
import com.ascentdev.wims.repository.UldTypeRepository;
import java.sql.Timestamp;
import com.ascentdev.wims.repository.UldImagesRepository;
import com.ascentdev.wims.utils.Dates;
import org.springframework.http.HttpStatus;

/**
 *
 * @author ASCENT
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
  CargoStatusRepository csRepo;

  @Autowired
  UldTypeRepository utRepo;

  @Autowired
  ReceivingLogsRepository rlRepo;

  @Autowired
  RefULDRepository refUldRepo;

  @Autowired
  CargoActivityLogsRepository cargoActivityRepo;

  @Override
  public ApiResponseModel searchFlights(String userId) {
    ApiResponseModel resp = new ApiResponseModel();
    SearchFlightsModel data = new SearchFlightsModel();

    List<FlightsEntity> flights = new ArrayList<>();
    List<UldsEntity> ulds = new ArrayList<>();

    try {
      flights = fRepo.findByUserIdContaining(userId);
      if (flights.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
      } else {
        resp.setMessage("Success!");
        resp.setStatus(true);
        resp.setStatusCode(200);
        data.setFlights(flights);
        resp.setData(data);
      }
    } catch (ErrorException e) {
      e.printStackTrace();
      message = "No Data to Show";
      status = false;
      statusCode = 404;
      return resp;
    }
    return resp;
  }

  @Override
  public ApiResponseModel getUlds(String flightNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    UldsModel data = new UldsModel();

//    List<UldsEntity> ulds = new ArrayList<>();
    List<RefULDEntity> ulds = new ArrayList<>();

    try {
      ulds = refUldRepo.findByFlightNumber(flightNumber);
      if (ulds.size() == 0) {
        resp.setMessage("No Data to Show");
        resp.setStatus(false);
        resp.setStatusCode(404);
      } else {
        data.setUldList(ulds);
        resp.setMessage("Success!");
        resp.setStatus(true);
        resp.setStatusCode(200);
      }
      resp.setData(data);
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
//      if (isUld) {
      mawb = mRepo.findByUldNumber(uldNumber);
      if (mawb != null) {
        data.setMawbs(mawb);
        resp.setData(data);
        resp.setMessage("Data found!");
        resp.setStatus(true);
        resp.setStatusCode(200);
      } else {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
        resp.setMessage(message);
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      }
//      } else {
//        mawb = mRepo.findByFlightNumber(flightNumber);
//        if (mawb.size() == 0) {
//          message = "No Data to Show";
//          status = false;
//          statusCode = 404;
//
//          resp.setMessage(message);
//          resp.setStatus(status);
//          resp.setStatusCode(statusCode);
//        } else {
//          data.setMawbs(mawb);
//          resp.setData(data);
//          resp.setMessage("Data found!");
//          resp.setStatus(true);
//          resp.setStatusCode(200);
//        }
//
//      }
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
        resp.setMessage(message);
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      } else {
        data.setHawbs(hawbs);
        resp.setData(data);
        resp.setMessage("Data Found");
        resp.setStatus(true);
        resp.setStatusCode(200);
      }

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
  public ApiResponseModel confirmCargo(CargoActivityLogsEntity cargoLogs, MawbEntity mawbDetails, HawbEntity hawbDetails, String mawb_number, String flightNumber, String hawb_number, int userId) {
    ErrorException ex1 = null;
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    List<HawbEntity> hawbs = new ArrayList<>();
    List<MawbEntity> mawbs = new ArrayList<>();
    List<RefRackEntity> refRack = new ArrayList<>();
    List<RackEntity> rackList = new ArrayList<>();

    CargoActivityLogsEntity cargoEntity = new CargoActivityLogsEntity();
    HawbEntity hawb1 = new HawbEntity();
//    StorageLogsEntity storage = new StorageLogsEntity();
    MawbEntity mawb1 = new MawbEntity();
    RackEntity rack = new RackEntity();
    RefRackEntity refRackDetail = new RefRackEntity();

    float tempV = 0;

    try {
      refRack = rrRepo.findAll();
      hawbs = hRepo.findByMawbNumberAndHawbNumber(mawb_number, hawb_number);
      mawb1 = mRepo.findByMawbNumber(mawb_number);

      if (mawb1.getId() > 0) {
        if (hawbs.size() > 0) {
          for (HawbEntity h : hawbs) {
            hawb1 = hRepo.findByHawbNumber(h.getHawbNumber());
            hawb1 = hRepo.save(hawb1);

            cargoEntity.setHawbId(h.getId());
            rack.setTxnHawbId(h.getId());
          }

        } else {
          cargoEntity.setHawbId(0);
        }

//      -- SAVE TO TXN MAWB TABLE (ADD DATA)
        mawb1.setActualPcs(mawbDetails.getActualPcs());
        mawb1.setActualVolume(mawbDetails.getVolume());
        mawb1.setActualWeight(mawbDetails.getActualWeight());
        mawb1.setCargoStatus(mawbDetails.getCargoStatus());
        mawb1.setCargoClassId(mawbDetails.getCargoClassId());
        mawb1.setCargoCategoryId(mawbDetails.getCargoCategoryId());
        mawb1.setLength(mawbDetails.getLength());
        mawb1.setWidth(mawbDetails.getWidth());
        mawb1.setHeight(mawbDetails.getHeight());

        mawb1 = mRepo.save(mawb1);
        mawbs.add(mawb1);

//      -- SAVE TO CARGO ACTIVITY LOGS TABLE (ADD DATA)
        cargoEntity.setHandledById(String.valueOf(userId));
        cargoEntity.setReceivedDatetime(Timestamp.valueOf(new Dates().getCurrentDateTime()));
        cargoEntity.setActualPcs(mawbDetails.getActualPcs());
        cargoEntity.setUpdatedAt(Timestamp.valueOf(new Dates().getCurrentDateTime()));
        cargoEntity.setUpdatedById(userId);
        cargoEntity.setLocation("STORING");
        //cargoEntity.setRemarks(cargoLogs.getRemarks());

        cargoEntity = cargoActivityRepo.save(cargoEntity);

       

      } else {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "No MAWB NUMBER: ['" + mawb_number + "'] Found", System.currentTimeMillis());
        throw ex1;
      }

      if (mawbs.size() > 0) {

        rack.setNoOfPieces(mawbDetails.getActualPcs());
//        rack.setTxnHawbId(acceptance.getTxnHawbId());
        rack.setTxnMawbId(mawb1.getId());
        rack.setStoredDt(Timestamp.valueOf(date));
        rack.setCreatedAt(Timestamp.valueOf(date));
        rack.setStoredById(userId);
        rackList.add(rack);
      } else {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "Failed to Assign Rack in this Cargo", System.currentTimeMillis());
        throw ex1;

      }

      for (RefRackEntity r : refRack) {
        tempV = r.getMaxVolume() - r.getVolume();
        if (tempV >= mawb1.getActualVolume()) {
          r.setVolume(r.getVolume() + mawb1.getActualVolume());
          refRackDetail = rrRepo.findById(r.getId());
          rack.setLocation(refRackDetail.getRackName() + " - " + refRackDetail.getLayerName());
          if (rackList.size() > 0) {
            for (RackEntity re : rackList) {
              re.setRefRackId(r.getId());
              re.setVolume(mawb1.getActualVolume());
              re = rRepo.save(re);
            }
          } else {
            refRackDetail = rrRepo.findById(r.getId());
            rack = new RackEntity();
            rack.setNoOfPieces(mawb1.getActualPcs());
            rack.setRefRackId(r.getId());
            rack.setVolume(mawb1.getActualVolume());
            rack.setLocation(refRackDetail.getRackName() + " - " + refRackDetail.getLayerName());
            rack = rRepo.save(rack);
          }

          r = rrRepo.save(r);
          status = true;
          statusCode = 200;
          message = "Saved Successfully";
          break;
        }

      }
      resp.setData(mawb1);
      resp.setMessage("Saved Successfully");
      resp.setStatus(true);
      resp.setStatusCode(200);

    } catch (ErrorException e) {
      if (ex1 == null) {
        ex1 = new ErrorException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Bad Request", System.currentTimeMillis());
      }
      throw ex1;
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

        resp.setMessage(message);
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      } else {
        condition.setCondition(condition1);
      }
      resp.setCondition(condition1);
      resp.setMessage("Cargo Condition Found");
      resp.setStatus(true);
      resp.setStatusCode(200);
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
      resp.setMessage("Successfully Saved Images");
      resp.setStatus(true);
      resp.setStatusCode(200);
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
          int cargoConditionId,
          String mawbNumber,
          String hawbNumber,
          String flightNumber,
          String remarks, 
          CargoActivityLogsEntity cargoLogs, 
          MawbEntity mawbDetails, 
          HawbEntity hawbDetails) {
    ApiResponseModel resp = new ApiResponseModel();

    try {

//      SAVE TO CARGO ACTIVITY LOGS 
      resp = confirmCargo(cargoLogs, mawbDetails, hawbDetails, mawbNumber, flightNumber, hawbNumber, 0);
      if (resp.isStatus()) {
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
      }

      resp.setMessage("Successfully Save Image");
      resp.setStatus(true);
      resp.setStatusCode(200);
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

        resp.setMessage(message);
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      } else {
        data.setCategory(category);
        resp.setData(data);
        resp.setMessage("Category found");
        resp.setStatus(true);
        resp.setStatusCode(200);
      }

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

        resp.setMessage(message);
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      } else {
        data.setCargoClass(cargoClass);
      }
      resp.setData(data);
      resp.setMessage("Cargo Class Found");
      resp.setStatus(true);
      resp.setStatusCode(200);
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
        resp.setMessage("Cargo Status Found");
        resp.setStatus(true);
        resp.setStatusCode(200);
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
        resp.setMessage("Uld Type Found");
        resp.setStatus(true);
        resp.setStatusCode(200);
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
        m.setUldNumber(u.getUldNumber());
        mRepo.save(m);

        resp.setMawbs(mawbs);

        if (u.getId() > 0) {
          resp.setData(u);
          resp.setMessage("SUCCESSFULLY ADDED ULD NO. [ " + u.getUldNumber() + " ]");
          resp.setStatus(true);
          resp.setStatusCode(200);

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
      resp.setStatus(true);
      resp.setStatusCode(200);
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
      u = uRepo.findByUldNumber(uldNumber);
      m = mRepo.findByUldNumber(uldNumber);

      for (int i = 0; i < m.size(); i++) {
        String mawbNumber = m.get(i).getMawbNumber();
        me = mRepo.findByMawbNumber(mawbNumber);
        me.setUldNumber(ulds.getUldNumber());
        mRepo.save(me);

      }

      if (u.getId() > 0) {
        u.setUldNumber(ulds.getUldNumber());
        u.setUldTypeId(ulds.getUldTypeId());
        uRepo.save(u);

        resp.setData(u);
        resp.setMessage("ULD NUMBER UPDATED");
        resp.setStatus(true);
        resp.setStatusCode(200);
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

  @Override
  public Integer uploadImage(MultipartFile[] file) {
    Integer resp = 0;

    try {
      for (MultipartFile f : file) {
        CargoImagesEntity images = new CargoImagesEntity();
          String filename = f.getOriginalFilename();
          images.setFilePath(fileUploadPath + "/" + filename);
          images.setFileName(filename);
          images.setCargoConditionId(1);
//          images.setFlightNumber(flightNumber);
//          images.setCargoConditionId(cargoConditionId);
//          images.setMawbNumber(mawbNumber);
//          images.setHawbNumber(hawbNumber);
//          images.setRemarks(remarks);
          ciRepo.save(images);
        saveImage(f);
      }
      resp = 1;
    } catch (Exception e) {
      resp = 0;
    }
    return resp;

  }

}
