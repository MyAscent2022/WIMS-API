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
import com.ascentdev.wims.entity.ImagesEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.RackEntity;
import com.ascentdev.wims.entity.ReceivingLogsEntity;
import com.ascentdev.wims.entity.RefRackEntity;
import com.ascentdev.wims.entity.RefULDEntity;
import com.ascentdev.wims.entity.UldContainerTypeEntity;
import com.ascentdev.wims.entity.UldTypeEntity;
import com.ascentdev.wims.entity.UldsEntity;
import com.ascentdev.wims.error.ErrorException;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoCategoryModel;
import com.ascentdev.wims.model.CargoClassModel;
import com.ascentdev.wims.model.CargoConditionModel;
import com.ascentdev.wims.model.CargoStatusModel;
import com.ascentdev.wims.model.HawbModel;
import com.ascentdev.wims.model.MawbListModel;
import com.ascentdev.wims.model.MawbModel;
import com.ascentdev.wims.model.SearchFlightsModel;
import com.ascentdev.wims.model.UldContainerTypeModel;
import com.ascentdev.wims.model.UldImagesModel;
import com.ascentdev.wims.model.UldTypeModel;
import com.ascentdev.wims.model.UldsModel;
import com.ascentdev.wims.repository.CargoActivityLogsRepository;
import com.ascentdev.wims.repository.CargoCategoryRepository;
import com.ascentdev.wims.repository.CargoClassRepository;
import com.ascentdev.wims.repository.CargoConditionRepository;
import com.ascentdev.wims.repository.CargoImagesRepository;
import com.ascentdev.wims.repository.CargoStatusRepository;
import com.ascentdev.wims.repository.FlightsRepository;
import com.ascentdev.wims.repository.HawbRepository;
import com.ascentdev.wims.repository.ImagesRepository;
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
import com.ascentdev.wims.repository.UldContainerTypeRepository;
import com.ascentdev.wims.repository.UldTypeRepository;
import java.sql.Timestamp;
import com.ascentdev.wims.repository.UldImagesRepository;
import com.ascentdev.wims.utils.Dates;
import java.util.Optional;
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

  String fileUploadPath = "C:\\wms_paircargo\\SUPPORTING_DOCUMENTS\\images\\";

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

  @Autowired
  ImagesRepository imgRepo;

  @Autowired
  UldContainerTypeRepository ucRepo;
  
  @Autowired
  UldImagesRepository uiRepo;

  @Override
  public ApiResponseModel searchFlights(long userId) {
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
    List<MawbListModel> mawbList = new ArrayList<>();
    FlightsEntity flight = new FlightsEntity();

    try {

      if (isUld) {
        mawb = mRepo.findByUldNumber(uldNumber);
        if (mawb != null) {
          data.setMawbs(mawbMapper(mawb));
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
      } else {
        flight = fRepo.findByFlightNumber(flightNumber);
        mawb = mRepo.findByFlightId(flight.getId());
        if (mawb.size() == 0) {
          message = "No Data to Show";
          status = false;
          statusCode = 404;

          resp.setMessage(message);
          resp.setStatus(status);
          resp.setStatusCode(statusCode);
        } else {
          data.setMawbs(mawbMapper(mawb));
          resp.setData(data);
          resp.setMessage("Data found!");
          resp.setStatus(true);
          resp.setStatusCode(200);
        }

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
      Path path = Paths.get(fileUploadPath + file.getOriginalFilename());
      Files.write(path, data);
    } catch (IOException ex) {
      Logger.getLogger(ReceiveCargoServiceImp.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public ApiResponseModel confirmCargo(CargoActivityLogsEntity cargoLogs, MawbEntity mawbDetails, HawbEntity hawbDetails, String mawb_number, String flightNumber, String hawb_number, int userId, String cargoCategory, String cargoClass) {
    ErrorException ex1 = null;
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    List<HawbEntity> hawbs = new ArrayList<>();
    List<MawbEntity> mawbs = new ArrayList<>();
    List<RefRackEntity> refRack = new ArrayList<>();
    List<RackEntity> rackList = new ArrayList<>();

    CargoActivityLogsEntity cargoEntity = new CargoActivityLogsEntity();
    HawbEntity hawb1 = new HawbEntity();
    CargoCategoryEntity category = new CargoCategoryEntity();
    CargoClassEntity cargoClass1 = new CargoClassEntity();
//    StorageLogsEntity storage = new StorageLogsEntity();
    MawbEntity mawb1 = new MawbEntity();
    RackEntity rack = new RackEntity();
    RefRackEntity refRackDetail = new RefRackEntity();
    FlightsEntity flights = new FlightsEntity();

    float tempV = 0;

    try {
      refRack = rrRepo.findAll();
      flights = fRepo.findByFlightNumber(flightNumber);
      hawbs = hRepo.findByMawbNumberAndHawbNumber(mawb_number, hawb_number);
      mawb1 = mRepo.findByMawbNumber(mawb_number);
      category = ccRepo.findByDescription(cargoCategory);
      cargoClass1 = classRepo.findByClassdesc(cargoClass);

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
        mawb1.setCargoClassId(cargoClass1.getId());
        mawb1.setCargoCategoryId(category.getId());
        mawb1.setLength(mawbDetails.getLength());
        mawb1.setWidth(mawbDetails.getWidth());
        mawb1.setHeight(mawbDetails.getHeight());

        mawb1 = mRepo.save(mawb1);
        mawbs.add(mawb1);

//      -- SAVE TO CARGO ACTIVITY LOGS TABLE (ADD DATA)
        cargoEntity.setHandledById(userId);
        cargoEntity.setReceivedReleasedDate(Timestamp.valueOf(new Dates().getCurrentDateTime()));
        cargoEntity.setActualPcs(mawbDetails.getActualPcs());
        cargoEntity.setUpdatedAt(Timestamp.valueOf(new Dates().getCurrentDateTime()));
        cargoEntity.setUpdatedById((long) userId);
        cargoEntity.setLocation("STORING AREA");
        cargoEntity.setMawbId(mawb1.getId());
        cargoEntity.setHawbId(hawb1.getId());
        cargoEntity.setFlightId(flights.getId());
        cargoEntity.setCreatedAt(Timestamp.valueOf(new Dates().getCurrentDateTime()));
        cargoEntity.setCreatedById(userId);
        cargoEntity.setActivityStatus("STORING");
        //cargoEntity.setRemarks(cargoLogs.getRemarks());

        cargoActivityRepo.save(cargoEntity);

      } else {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "No MAWB NUMBER Found", System.currentTimeMillis());
        throw ex1;
      }

      if (mawbs.size() > 0) {

        rack.setNoOfPieces(mawbDetails.getActualPcs());
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
          refRackDetail = rrRepo.findById((int) r.getId());
          rack.setLocation(refRackDetail.getRackName() + " - " + refRackDetail.getLayerName());
          if (rackList.size() > 0) {
            for (RackEntity re : rackList) {
              re.setRefRackId(r.getId());
              re.setVolume(mawb1.getActualVolume());
              re = rRepo.save(re);
            }
          } else {
            refRackDetail = rrRepo.findById((long) r.getId());
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
          String uldCondition1,
          String uldCondition2,
          String flightNumber,
          String uldNumber,
          String remarks1,
          String remarks2) {
    ApiResponseModel resp = new ApiResponseModel();
    CargoConditionEntity condition = new CargoConditionEntity();
    FlightsEntity flights = new FlightsEntity();
    UldsEntity ulds = new UldsEntity();

    try {
      int count = 0;
      condition = cargoRepo.findByCondition(count == 0 ? uldCondition1 : uldCondition2);
      flights = fRepo.findByFlightNumber(flightNumber);
      ulds = uRepo.findByUldNumber(uldNumber);
      for (MultipartFile f : file) {
        UldImagesEntity images = new UldImagesEntity();
        String filename = f.getOriginalFilename();
        images.setFilePath(fileUploadPath + filename);
        images.setFileName(filename);
        images.setRemarks(count == 0 ? remarks1 : remarks2);
        images.setUldNumber(ulds.getUldNumber());
        images.setUldConditionId(condition.getId());
        images.setFlightNumber(flights.getFlightNumber());
        System.out.println("filename " + filename);
        System.out.println("images " + images);
        iRepo.save(images);
        saveImage(f);
        condition = new CargoConditionEntity();
        count++;
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
      resp = confirmCargo(cargoLogs, mawbDetails, hawbDetails, mawbNumber, flightNumber, hawbNumber, 0, "", "");
      if (resp.isStatus()) {
        for (MultipartFile f : file) {
          CargoImagesEntity images = new CargoImagesEntity();
          String filename = f.getOriginalFilename();
          images.setFilePath(fileUploadPath + filename);
          images.setFileName(filename);
          images.setCargoConditionId(cargoConditionId);
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
  public ApiResponseModel getContainerType() {
    ApiResponseModel resp = new ApiResponseModel();
    UldContainerTypeModel data = new UldContainerTypeModel();

    List<UldContainerTypeEntity> containers = new ArrayList<>();

    try {
      containers = ucRepo.findAll();
      if (containers.size() > 0) {
        data.setContainers(containers);
        resp.setData(data);
        resp.setMessage("Uld Container Type Found");
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
  public ApiResponseModel saveUldNumber(UldsEntity ulds, String[] mawbs, String uldNumber) {
    ApiResponseModel resp = new ApiResponseModel();

    try {
      ulds.setUldNumber(uldNumber);
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
  public Integer uploadImage(MultipartFile[] file, long hawbId, String mawbNumber, String cargoCondition1, String cargoCondition2, String remarks1, String remarks2) {
    Integer resp = 0;
    long id = 0;

    MawbEntity mawb = new MawbEntity();
    CargoActivityLogsEntity cal = new CargoActivityLogsEntity();
    List<CargoActivityLogsEntity> cargoList = new ArrayList<>();
    CargoConditionEntity condition = new CargoConditionEntity();
    ImagesEntity images1 = new ImagesEntity();

    try {
      mawb = mRepo.findByMawbNumber(mawbNumber);
      if (hawbId == 0) {
        cargoList = cargoActivityRepo.findByMawbIdAndActivityStatus(mawb.getId(), "RECEIVING");
      } else {
        cargoList = cargoActivityRepo.findByMawbIdAndHawbIdAndActivityStatus(mawb.getId(), hawbId, "RECEIVING");
      }
      cal = cargoList.get(cargoList.size() - 1);
      int count = 0;
      for (MultipartFile f : file) {
        condition = cargoRepo.findByCondition(count == 0 ? cargoCondition1 : cargoCondition2);
        ImagesEntity images = new ImagesEntity();
        String filename = f.getOriginalFilename();
        images.setFilePath(fileUploadPath + filename);
        images.setFileName(filename);
        images.setCargoConditionId(condition.getId());
        images.setCargoActivityLogId(cal.getId());
        images.setRemarks(count == 0 ? remarks1 : remarks2);
        imgRepo.save(images);
        saveImage(f);
        condition = new CargoConditionEntity();
        count++;
      }
      resp = 1;
    } catch (Exception e) {
      resp = 0;
    }
    return resp;

  }

  private List<MawbListModel> mawbMapper(List<MawbEntity> mawb) {
    List<MawbListModel> mawbs = new ArrayList<>();
    for (MawbEntity m : mawb) {
      List<HawbEntity> hawbs = new ArrayList<>();
      hawbs = hRepo.findByMawbNumber(m.getMawbNumber());
      MawbListModel mawbList = new MawbListModel();
      mawbList.setId(m.getId());
      mawbList.setDateOfArrival(m.getDateOfArrival());
      mawbList.setDestinationCode(m.getDestinationCode());
      mawbList.setMawbNumber(m.getMawbNumber());
      mawbList.setNumberOfContainers(m.getNumberOfContainers());
      mawbList.setNumberOfPackages(m.getNumberOfPackages());
      mawbList.setOriginCode(m.getOriginCode());
      mawbList.setRegistryNumber(m.getRegistryNumber());
      mawbList.setTimeOfArrival(m.getTimeOfArrival());
      mawbList.setVolume(m.getVolume());
      mawbList.setUldNumber(m.getUldNumber());
      mawbList.setUldContainerTypeId(m.getUldContainerTypeId());
      mawbList.setCargoStatus(m.getCargoStatus());
      mawbList.setLength(m.getLength());
      mawbList.setWidth(m.getWidth());
      mawbList.setHeight(m.getHeight());
      mawbList.setActualWeight(m.getActualWeight());
      mawbList.setActualVolume(m.getActualVolume());
      mawbList.setActualPcs(m.getActualPcs());
      mawbList.setCargoCategoryId(m.getCargoCategoryId());
      mawbList.setCargoClassId(m.getCargoClassId());
      mawbList.setFlightId(m.getFlightId());
      mawbList.setHawbCount(hawbs.size());
      mawbs.add(mawbList);
    }
    return mawbs;
  }

  @Override
  public ApiResponseModel getUldImages(String flightNumber, String uldNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    UldImagesModel data = new UldImagesModel();
    
    List<UldImagesEntity> images = new ArrayList<>();
    
    try {
      images = uiRepo.findByFlightNumberAndUldNumber(flightNumber, uldNumber);
      if (images.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 400;
      } else {
        resp.setMessage("Images found!");
        resp.setStatus(true);
        resp.setStatusCode(200);
        resp.setData(data);
        data.setImages(images);
      }
    } catch (ErrorException e){
      e.printStackTrace();
    }
    
    return resp;
  }

}
