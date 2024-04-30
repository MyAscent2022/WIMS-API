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
import com.ascentdev.wims.entity.HawbForReceivingEntity;
import com.ascentdev.wims.entity.ImagesEntity;
import com.ascentdev.wims.entity.JobAssignmentEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.MawbTbEntity;
import com.ascentdev.wims.entity.RackUtilEntity;
import com.ascentdev.wims.entity.ReceivingLogsEntity;
import com.ascentdev.wims.entity.RefRackEntity;
import com.ascentdev.wims.entity.RefShipmentStatusEntity;
import com.ascentdev.wims.entity.RefULDEntity;
import com.ascentdev.wims.entity.TxnUldsEntity;
import com.ascentdev.wims.entity.UldActivityLogsEntity;
import com.ascentdev.wims.entity.UldContainerTypeEntity;
import com.ascentdev.wims.entity.UldTypeEntity;
import com.ascentdev.wims.entity.UldsEntity;
import com.ascentdev.wims.error.ErrorException;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoActivityModel;
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
import com.ascentdev.wims.repository.HawbForReceivingRepository;
import com.ascentdev.wims.repository.HawbRepository;
import com.ascentdev.wims.repository.ImagesRepository;
import com.ascentdev.wims.repository.JobAssignmentRepository;
import com.ascentdev.wims.repository.MawbRepository;
import com.ascentdev.wims.repository.MawbTbRepository;
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
import com.ascentdev.wims.repository.ReceivingLogsRepository;
import com.ascentdev.wims.repository.RefRackRepository;
import com.ascentdev.wims.repository.RefULDRepository;
import com.ascentdev.wims.repository.StorageLogsRepository;
import com.ascentdev.wims.repository.TxnUldsRepository;
import com.ascentdev.wims.repository.UldContainerTypeRepository;
import com.ascentdev.wims.repository.UldTypeRepository;
import java.sql.Timestamp;
import com.ascentdev.wims.repository.UldImagesRepository;
import com.ascentdev.wims.utils.Dates;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import com.ascentdev.wims.repository.RackUtilRepository;
import com.ascentdev.wims.repository.RefShipmentStatusRepository;
import com.ascentdev.wims.repository.UldActivityLogsRepository;

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
  MawbTbRepository m3Repo;

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
  RackUtilRepository rRepo;

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
  TxnUldsRepository tuRepo;

  @Autowired
  CargoActivityLogsRepository cargoActivityRepo;

  @Autowired
  ImagesRepository imgRepo;

  @Autowired
  UldContainerTypeRepository ucRepo;

  @Autowired
  UldImagesRepository uiRepo;

  @Autowired
  JobAssignmentRepository jaRepo;

  @Autowired
  HawbForReceivingRepository hrRepo;

  @Autowired
  UldActivityLogsRepository uldLogsRepo;

  @Autowired
  RefShipmentStatusRepository shipmentRepo;

  @Override
  public ApiResponseModel searchFlights(long userId) {
    ApiResponseModel resp = new ApiResponseModel();
    SearchFlightsModel data = new SearchFlightsModel();

    List<FlightsEntity> flights = new ArrayList<>();
    List<UldsEntity> ulds = new ArrayList<>();

    try {
//      flights = fRepo.findByUserId(userId);
      flights = fRepo.findAll();
      if (flights.size() == 0) {
        resp.setMessage("No Data to Show");
        resp.setStatus(false);
        resp.setStatusCode(404);
      } else {
        resp.setMessage("Success!");
        resp.setStatus(true);
        resp.setStatusCode(200);
        data.setFlights(flights);
        resp.setData(data);
      }
    } catch (ErrorException e) {
      e.printStackTrace();
      resp.setMessage(e.getMessage());
      resp.setStatus(false);
      resp.setStatusCode(404);
      return resp;
    }
    return resp;
  }

  @Override
  public ApiResponseModel getUlds(String flightNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    UldsModel data = new UldsModel();

    List<UldsEntity> ulds = new ArrayList<>();
//    List<RefULDEntity> ulds = new ArrayList<>();

    try {
      ulds = uRepo.findByFlightNumber(flightNumber);
      if (ulds.size() == 0) {
        resp.setMessage("No Data to Show");
        resp.setStatus(false);
        resp.setStatusCode(404);
      } else {
        data.setUldList1(ulds);
        resp.setData(data);
        resp.setMessage("Success!");
        resp.setStatus(true);
        resp.setStatusCode(200);
      }
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
    List<MawbEntity> mawbCount = new ArrayList<>();
    List<HawbEntity> hawb = new ArrayList<>();
    List<HawbEntity> h1 = new ArrayList<>();
    List<HawbEntity> hawbCount = new ArrayList<>();
    List<MawbListModel> mawbList = new ArrayList<>();
    List<CargoActivityLogsEntity> cal = new ArrayList<>();
    FlightsEntity flight = new FlightsEntity();
    HawbForReceivingEntity checkHawbIfExist = new HawbForReceivingEntity();

    try {

      if (isUld) {
        mawb = mRepo.findByUldNumberAndUldStatusNot(uldNumber, 10);
        if (mawb.size() > 0) {
          hawb = hRepo.findByMawbNumber(mawb.get(0).getMawbNumber());
        }

//        int count = 0;
        for (HawbEntity h : hawb) {
          checkHawbIfExist = new HawbForReceivingEntity();
          checkHawbIfExist = hrRepo.findById(h.getId()).get();
          if (Boolean.valueOf(checkHawbIfExist.getResult().contains("t"))) {
            h1.add(h);
          }
        }

//        for (HawbEntity e : hawb) {
//          if (!mawb.isEmpty() && !hawb.isEmpty()) {
//            cal = cargoActivityRepo.findByMawbIdAndHawbId(mawb.get(0).getId(), hawb.get(count).getId());
//          } else {
//            cal = new ArrayList<>();
//          }
//          count++;
//        }
        if (mawb.size() == 0) {
          message = "No Data to Show";
          status = false;
          statusCode = 404;
        } else {
          if (h1.size() == 0) {
//            resp.setMessage("No New Data!");
//            resp.setStatus(false);
//            resp.setStatusCode(404);
            data.setMawbs(mawbMapper(mawb));
            resp.setData(data);
            resp.setMessage("New Data Found!");
            resp.setStatus(true);
            resp.setStatusCode(200);
          } else {
            data.setMawbs(mawbMapper(mawb));
            resp.setData(data);
            resp.setMessage("New Data Found!");
            resp.setStatus(true);
            resp.setStatusCode(200);
          }
        }

      } else {
        flight = fRepo.findByFlightNumber(flightNumber);
        mawb = mRepo.findByFlightId(flight.getId());
        mawbCount = mRepo.findByUldNumber(uldNumber);
        if (mawb.size() > 0) {
          hawb = hRepo.findByMawbNumber(mawb.get(0).getMawbNumber());
        }

        if (!mawb.isEmpty() && !hawb.isEmpty()) {
          cal = cargoActivityRepo.findByMawbIdAndHawbId(mawb.get(0).getId(), hawb.get(0).getId());
        } else {
          cal = new ArrayList<>();
        }

        if (mawb.size() == 0) {
          resp.setMessage("No Data to Show");
          resp.setStatus(false);
          resp.setStatusCode(404);

        } else {
          if (cal.size() != 0) {
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
    HawbForReceivingEntity checkHawbIfExist = new HawbForReceivingEntity();
    List<HawbEntity> hawbs = new ArrayList<>();
    List<HawbEntity> hawbs1 = new ArrayList<>();
    int count = 0;
    try {
      hawbs = hRepo.findByMawbNumber(mawbNumber);
      for (HawbEntity h : hawbs) {
        checkHawbIfExist = new HawbForReceivingEntity();
        checkHawbIfExist = hrRepo.findById(h.getId()).get();
        if (Boolean.valueOf(checkHawbIfExist.getResult().contains("t"))) {
          hawbs1.add(h);
        }
      }
      if (hawbs1.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 404;
        resp.setMessage(message);
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      } else {
        data.setHawbs(hawbs1);
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
  public ApiResponseModel confirmCargo(CargoActivityLogsEntity cargoLogs, MawbEntity mawbDetails, HawbEntity hawbDetails, String mawb_number, String flightNumber, String hawb_number, int userId, String cargoCategory, String cargoClass, String uld_number, boolean is_badOrder, String shipment_status) {
    ErrorException ex1 = null;
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    List<HawbEntity> hawbs = new ArrayList<>();
    List<HawbEntity> checkHawbSize = new ArrayList<>();
    List<HawbEntity> h1 = new ArrayList<>();
    List<MawbEntity> checkMawbSize = new ArrayList<>();
    List<MawbTbEntity> mawbs = new ArrayList<>();
    List<RefRackEntity> refRack = new ArrayList<>();
    List<RackUtilEntity> rackList = new ArrayList<>();
    List<JobAssignmentEntity> jobAssigns = new ArrayList<>();

    CargoActivityLogsEntity cargoEntity = new CargoActivityLogsEntity();
    HawbEntity hawb1 = new HawbEntity();
    CargoCategoryEntity category = new CargoCategoryEntity();
    CargoClassEntity cargoClass1 = new CargoClassEntity();
    MawbTbEntity mawb2 = new MawbTbEntity();
    RackUtilEntity rack = new RackUtilEntity();
    RefRackEntity refRackDetail = new RefRackEntity();
    FlightsEntity flights = new FlightsEntity();
    UldsEntity uld = new UldsEntity();
    RefULDEntity refUld = new RefULDEntity();
    HawbForReceivingEntity checkHawbIfExist = new HawbForReceivingEntity();

    RefShipmentStatusEntity shipmentStat = new RefShipmentStatusEntity();
    float tempV = 0;

    try {
      refUld = refUldRepo.findByUldNo(uld_number);
      refRack = rrRepo.findAll();
      flights = fRepo.findByFlightNumber(flightNumber);
      hawbs = hRepo.findByMawbNumberAndHawbNumber(mawb_number, hawb_number);
      mawb2 = m3Repo.findByMawbNumber(mawb_number);
      category = ccRepo.findByDescription(cargoCategory);
      cargoClass1 = classRepo.findByClassdesc(cargoClass);
      shipmentStat = shipmentRepo.findByName(shipment_status);

      if (refUld.getId() != 0) {

        checkMawbSize = mRepo.findByUldNumber(uld_number);
        if (checkMawbSize.size() <= 1) {
          checkHawbSize = hRepo.findByMawbNumber(mawb_number);

          for (HawbEntity h : checkHawbSize) {
            checkHawbIfExist = new HawbForReceivingEntity();
            checkHawbIfExist = hrRepo.findById(h.getId()).get();
            if (Boolean.valueOf(checkHawbIfExist.getResult().contains("t"))) {
              h1.add(h);
            }
          }

          if (h1.size() <= 1) {
            refUld.setUldStatus(10);
            refUldRepo.save(refUld);
          } else if (h1.size() == 0) {
            refUld.setUldStatus(10);
            refUldRepo.save(refUld);
          }

        }

        if (is_badOrder) {
          if (mawb2.getId() > 0) {
            if (hawbs.size() > 0) {
              for (HawbEntity h : hawbs) {
                hawb1 = hRepo.findByHawbNumber(h.getHawbNumber());
                hawb1 = hRepo.save(hawb1);

                cargoEntity.setHawbId(h.getId());
              }

            } else {
              cargoEntity.setHawbId(0);
            }

            if (hawb1 != null) {
              hawb1.setActualPcs(mawbDetails.getActualPcs());
              hawb1.setActualVolume(mawbDetails.getVolume());
              hawb1.setActualWeight(mawbDetails.getActualWeight());
              hawb1.setCargoClassId(cargoClass1.getId());
              hawb1.setCargoCategoryId(category.getId());
              hawb1.setLength(mawbDetails.getLength());
              hawb1.setWidth(mawbDetails.getWidth());
              hawb1.setHeight(mawbDetails.getHeight());

              hawb1 = hRepo.save(hawb1);
            } else {
              //      -- SAVE TO TXN MAWB TABLE (ADD DATA)
              mawb2.setActualPcs(mawbDetails.getActualPcs());
              mawb2.setActualVolume(mawbDetails.getVolume());
              mawb2.setActualWeight(mawbDetails.getActualWeight());
              mawb2.setCargoClassId(cargoClass1.getId());
              mawb2.setCargoCategoryId(category.getId());
              mawb2.setLength(mawbDetails.getLength());
              mawb2.setWidth(mawbDetails.getWidth());
              mawb2.setHeight(mawbDetails.getHeight());

              mawb2 = m3Repo.save(mawb2);
              mawbs.add(mawb2);
            }

            if (checkMawbSize.size() <= 1) {
              if (checkHawbSize.size() <= 1) {
                refUld.setUldStatus(3);
                refUldRepo.save(refUld);
              } else if (checkHawbSize.size() == 0) {
                refUld.setUldStatus(3);
                refUldRepo.save(refUld);
              }

            }

//      -- SAVE TO CARGO ACTIVITY LOGS TABLE (ADD DATA)
//        cargoEntity.setHandledById(jobAssigns.get(0).getId());
            cargoEntity.setReceivedReleasedDate(Timestamp.valueOf(new Dates().getCurrentDateTime()));
            cargoEntity.setActualPcs(mawbDetails.getActualPcs());
            cargoEntity.setLocation("RECEIVING AREA");
            cargoEntity.setMawbId(mawb2.getId());
            cargoEntity.setHawbId(hawb1.getId());
            cargoEntity.setFlightId(flights.getId());
            cargoEntity.setCreatedAt(Timestamp.valueOf(new Dates().getCurrentDateTime()));
            cargoEntity.setCreatedById(userId);
            cargoEntity.setActivityStatus("BAD ORDER");
            cargoEntity.setStatusCode(shipmentStat.getCode());
            //cargoEntity.setRemarks(cargoLogs.getRemarks());
            cargoActivityRepo.save(cargoEntity);

            resp.setData(mawb2);
            resp.setMessage("Saved Successfully");
            resp.setStatus(true);
            resp.setStatusCode(200);

          } else {
            ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "No MAWB NUMBER Found", System.currentTimeMillis());
            throw ex1;
          }

        } else {
          if (mawb2.getId() > 0) {
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

            if (hawb1 != null) {
              hawb1.setActualPcs(mawbDetails.getActualPcs());
              hawb1.setActualVolume(mawbDetails.getVolume());
              hawb1.setActualWeight(mawbDetails.getActualWeight());
              hawb1.setCargoClassId(cargoClass1.getId());
              hawb1.setCargoCategoryId(category.getId());
              hawb1.setLength(mawbDetails.getLength());
              hawb1.setWidth(mawbDetails.getWidth());
              hawb1.setHeight(mawbDetails.getHeight());

              hawb1 = hRepo.save(hawb1);
              hawbs.add(hawb1);
            } else {
              //      -- SAVE TO TXN MAWB TABLE (ADD DATA)
              mawb2.setActualPcs(mawbDetails.getActualPcs());
              mawb2.setActualVolume(mawbDetails.getVolume());
              mawb2.setActualWeight(mawbDetails.getActualWeight());
              mawb2.setCargoClassId(cargoClass1.getId());
              mawb2.setCargoCategoryId(category.getId());
              mawb2.setLength(mawbDetails.getLength());
              mawb2.setWidth(mawbDetails.getWidth());
              mawb2.setHeight(mawbDetails.getHeight());

              mawb2 = m3Repo.save(mawb2);
              mawbs.add(mawb2);
            }

            if (checkMawbSize.size() <= 1) {
              if (checkHawbSize.size() <= 1) {
                refUld.setUldStatus(3);
                refUldRepo.save(refUld);
              } else if (checkHawbSize.size() == 0) {
                refUld.setUldStatus(3);
                refUldRepo.save(refUld);
              }

            }

//      -- SAVE TO CARGO ACTIVITY LOGS TABLE (ADD DATA)
//        cargoEntity.setHandledById(jobAssigns.get(0).getId());
            cargoEntity.setReceivedReleasedDate(Timestamp.valueOf(new Dates().getCurrentDateTime()));
            cargoEntity.setActualPcs(mawbDetails.getActualPcs());
            cargoEntity.setLocation("RECEIVING AREA");
            cargoEntity.setMawbId(mawb2.getId());
            cargoEntity.setHawbId(hawb1.getId());
            cargoEntity.setFlightId(flights.getId());
            cargoEntity.setCreatedAt(Timestamp.valueOf(new Dates().getCurrentDateTime()));
            cargoEntity.setCreatedById(userId);
            cargoEntity.setActivityStatus("RECEIVED");
            cargoEntity.setStatusCode(shipmentStat.getCode());
            //cargoEntity.setRemarks(cargoLogs.getRemarks());
            cargoActivityRepo.save(cargoEntity);

          } else {
            ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "No MAWB NUMBER Found", System.currentTimeMillis());
            throw ex1;
          }

          if (mawbs.size() > 0) {
            rack.setNoOfPieces(mawbDetails.getActualPcs());
            rack.setTxnMawbId(mawb2.getId());
            rack.setStoredDt(Timestamp.valueOf(date));
            rack.setCreatedAt(Timestamp.valueOf(date));
            rack.setStoredById(userId);
            rackList.add(rack);
          } else if (hawbs.size() > 0) {
            rack.setNoOfPieces(mawbDetails.getActualPcs());
            rack.setTxnMawbId(mawb2.getId());
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
            if (hawbs.size() > 0) {
              if (tempV >= hawb1.getActualVolume()) {
                r.setVolume(r.getVolume() + hawb1.getActualVolume());
                refRackDetail = rrRepo.findById((int) r.getId());
                rack.setLocation(refRackDetail.getRackName() + " - " + refRackDetail.getLayerName());
                if (rackList.size() > 0) {
                  for (RackUtilEntity re : rackList) {
                    re.setRefRackId(r.getId());
                    re.setVolume(hawb1.getActualVolume());
                    re = rRepo.save(re);
                  }
                } else {
                  refRackDetail = rrRepo.findById((long) r.getId());
                  rack = new RackUtilEntity();
                  rack.setNoOfPieces(hawb1.getActualPcs());
                  rack.setRefRackId(r.getId());
                  rack.setVolume(hawb1.getActualVolume());
                  rack.setLocation(refRackDetail.getRackName() + " - " + refRackDetail.getLayerName());
                  rack = rRepo.save(rack);
                }
                r = rrRepo.save(r);
                status = true;
                statusCode = 200;
                message = "Saved Successfully";
                break;
              }
            } else {
              if (tempV >= mawb2.getActualVolume()) {
                r.setVolume(r.getVolume() + mawb2.getActualVolume());
                refRackDetail = rrRepo.findById((int) r.getId());
                rack.setLocation(refRackDetail.getRackName() + " - " + refRackDetail.getLayerName());
                if (rackList.size() > 0) {
                  for (RackUtilEntity re : rackList) {
                    re.setRefRackId(r.getId());
                    re.setVolume(mawb2.getActualVolume());
                    re = rRepo.save(re);
                  }
                } else {
                  refRackDetail = rrRepo.findById((long) r.getId());
                  rack = new RackUtilEntity();
                  rack.setNoOfPieces(mawb2.getActualPcs());
                  rack.setRefRackId(r.getId());
                  rack.setVolume(mawb2.getActualVolume());
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

          }

        }

        if (hawbs.size() > 0) {
          resp.setData(hawb1);
          resp.setMessage("Saved Successfully");
          resp.setStatus(true);
          resp.setStatusCode(200);
        } else {
          resp.setData(mawb2);
          resp.setMessage("Saved Successfully");
          resp.setStatus(true);
          resp.setStatusCode(200);
        }

      } else {
        ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "No record Found", System.currentTimeMillis());
        throw ex1;
      }

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
      condition1 = cargoRepo.findAllByOrderByIdDesc();
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
          String remarks2, int user_id) {
    ApiResponseModel resp = new ApiResponseModel();
    CargoConditionEntity condition = new CargoConditionEntity();
    FlightsEntity flights = new FlightsEntity();
    UldsEntity ulds = new UldsEntity();
    RefULDEntity refUlds = new RefULDEntity();
    UldActivityLogsEntity uldLogs = new UldActivityLogsEntity();

    LocalDateTime date = LocalDateTime.now();
    try {
      int count = 0;
      condition = cargoRepo.findByCondition(count == 0 ? uldCondition1 : uldCondition2);
      flights = fRepo.findByFlightNumber(flightNumber);
      ulds = uRepo.findByUldNumberAndFlightNumber(uldNumber, flightNumber);
      refUlds = refUldRepo.findByUldNo(uldNumber);
      if (refUlds.getId() != 0) {
        refUlds.setUldStatus(11);
        refUldRepo.save(refUlds);

        uldLogs.setFlightId(flights.getId());
        uldLogs.setUldId(refUlds.getId());
        uldLogs.setLocation("RECEIVING AREA");
        uldLogs.setActivityStatus("STRIPPED");
        uldLogs.setReceivedStrippedAt(Timestamp.valueOf(date));
        uldLogs.setReceivedStrippedById(user_id);
        uldLogsRepo.save(uldLogs);

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

      } else {
        resp.setMessage("Image Did Not Upload");
        resp.setStatus(false);
        resp.setStatusCode(404);
        resp.setData(0);
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
//      resp = confirmCargo(cargoLogs, mawbDetails, hawbDetails, mawbNumber, flightNumber, hawbNumber, 0, "", "", );
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
  public ApiResponseModel saveUldNumber(UldsEntity ulds, String[] mawbs, String uldNumber, String flightNumber, String uldType) {
    ApiResponseModel resp = new ApiResponseModel();
    TxnUldsEntity tu = new TxnUldsEntity();
    FlightsEntity flights = new FlightsEntity();
    UldContainerTypeEntity uc = new UldContainerTypeEntity();

    List<TxnUldsEntity> tuList = new ArrayList<>();

    try {
      flights = fRepo.findByFlightNumber(flightNumber);
      uc = ucRepo.findByType(uldType);
      tuList = tuRepo.findByFlightNumber(flightNumber);

      if (tuList.isEmpty()) {
        tu = new TxnUldsEntity();
      } else {
        tu = tuList.get(0);
      }

      //saving in txn_uld
      if (tu != null) {
        tu.setUldNumber(uldNumber);
        tu.setTotalMawb(mawbs.length);
        tu.setFlightNumber(flightNumber);
        tu.setUldType(ulds.getUldTypeId());
        tu = tuRepo.save(tu);
      } else {
        tu.setUldNumber(uldNumber);
        tu.setTotalMawb(mawbs.length);
        tu.setFlightNumber(flightNumber);
        tu.setUldType(ulds.getUldTypeId());
        tu = tuRepo.save(tu);
      }

//      ulds.setUldNumber(uldNumber);
//      UldsEntity u = uRepo.save(ulds);
//      u.setTotalMawb(mawbs.length);
      MawbEntity m;

      for (int i = 0; i < mawbs.length; i++) {
        String mawbNumber = mawbs[i];
        m = mRepo.findByMawbNumber(mawbNumber);
        m.setUldNumber(tu.getUldNumber());
        mRepo.save(m);

        resp.setMawbs(mawbs);

        if (tu.getId() > 0) {
          resp.setData(ulds);
          resp.setMessage("SUCCESSFULLY ADDED ULD NO. [ " + tu.getUldNumber() + " ]");
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
//    UldsEntity u = new UldsEntity();
    TxnUldsEntity u = new TxnUldsEntity();
    List<MawbEntity> m = new ArrayList<>();
    MawbEntity me = new MawbEntity();

    try {
//      u = uRepo.findByUldNumber(uldNumber);
      u = tuRepo.findByUldNumber(uldNumber);
      m = mRepo.findByUldNumberAndUldStatusNot(uldNumber, 10);

      for (int i = 0; i < m.size(); i++) {
        String mawbNumber = m.get(i).getMawbNumber();
        me = mRepo.findByMawbNumber(mawbNumber);
        me.setUldNumber(ulds.getUldNumber());
        mRepo.save(me);

      }

      if (u.getId() > 0) {
        u.setUldNumber(ulds.getUldNumber());
        u.setUldType(ulds.getUldTypeId());
        tuRepo.save(u);

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
  public Integer uploadImage(MultipartFile[] file, int hawbId, String mawbNumber, String cargoCondition1, String cargoCondition2, String cargoCondition3, String remarks1, String remarks2, int quantity, boolean is_badOrder) {
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
        if (is_badOrder) {
          cargoList = cargoActivityRepo.getByMawbIdAndActivityStatus(mawb.getId(), "BAD ORDER");
        } else {
          cargoList = cargoActivityRepo.getByMawbIdAndActivityStatus(mawb.getId(), "RECEIVED");
        }
      } else {
        if (is_badOrder) {
          cargoList = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatus(mawb.getId(), hawbId, "BAD ORDER");
        } else {
          cargoList = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatus(mawb.getId(), hawbId, "RECEIVED");
        }
      }
      cal = cargoList.get(cargoList.size() - 1);
      int count = 0;
      for (MultipartFile f : file) {
        if (count == 0) {
          condition = cargoRepo.findByCondition(cargoCondition1);
        } else if (count == 1) {
          condition = cargoRepo.findByCondition(cargoCondition2);
        } else {
          condition = cargoRepo.findByCondition(cargoCondition3);
        }

        ImagesEntity images = new ImagesEntity();
        String filename = f.getOriginalFilename();
        images.setFilePath(fileUploadPath + filename);
        images.setFileName(filename);
        images.setCargoConditionId(condition.getId());
        images.setCargoActivityLogId(cal.getId());
        if (count == 0) {
          images.setRemarks(remarks1);
        } else if (count == 1) {
          images.setRemarks(remarks2);
        } else {
          images.setRemarks(null);
        }

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
    HawbForReceivingEntity checkHawbIfExist = new HawbForReceivingEntity();
    for (MawbEntity m : mawb) {
      List<HawbEntity> hawbs = new ArrayList<>();
      List<HawbEntity> hawbs1 = new ArrayList<>();
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
      mawbList.setGrossMass(m.getGrossMass());
      mawbList.setConsigneeName(m.getConsigneeName());

      for (HawbEntity h : hawbs) {
        checkHawbIfExist = new HawbForReceivingEntity();
        checkHawbIfExist = hrRepo.findById(h.getId()).get();
        if (Boolean.valueOf(checkHawbIfExist.getResult().contains("t"))) {
          hawbs1.add(h);
        }
      }

      mawbList.setHawbCount(hawbs1.size());
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
    } catch (ErrorException e) {
      e.printStackTrace();
    }

    return resp;
  }

}
