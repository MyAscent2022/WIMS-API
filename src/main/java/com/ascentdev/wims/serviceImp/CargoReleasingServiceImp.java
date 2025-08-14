/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.PushNotificationServices.PushNotificationService;
import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.CargoReleasingEntity;
import com.ascentdev.wims.entity.CargoStatusLogsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.HawbForPullOutEntity;
import com.ascentdev.wims.entity.HawbForReleasingEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.MawbForPullOutEntity;
import com.ascentdev.wims.entity.MawbForReleasingEntity;
import com.ascentdev.wims.entity.MawbTbEntity;
import com.ascentdev.wims.entity.RackUtilEntity;
import com.ascentdev.wims.entity.RackUtilHistoryEntity;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.ForCargoReleasingModel;
import com.ascentdev.wims.model.PullOutCargoModel;
import com.ascentdev.wims.model.PushNotificationRequest;
import com.ascentdev.wims.model.ReleasingModelForSaving;
import com.ascentdev.wims.model.TruckListModel;
import com.ascentdev.wims.repository.ApprovedReleasingDao;
import com.ascentdev.wims.repository.CargoActivityLogsRepository;
import com.ascentdev.wims.repository.CargoReleasingImagesRepository;
import com.ascentdev.wims.repository.CargoReleasingRepository;
import com.ascentdev.wims.repository.CargoStatusLogsRepository;
import com.ascentdev.wims.repository.HawbForPullOutRepository;
import com.ascentdev.wims.repository.HawbForReleasingRepository;
import com.ascentdev.wims.repository.HawbRepository;
import com.ascentdev.wims.repository.ImagesRepository;
import com.ascentdev.wims.repository.MawbForPullOutRepository;
import com.ascentdev.wims.repository.MawbForReleasingRepository;
import com.ascentdev.wims.repository.MawbRepository;
import com.ascentdev.wims.repository.MawbTbRepository;
import com.ascentdev.wims.repository.OlrsRepository;
import com.ascentdev.wims.repository.RackUtilHistoryRepository;
import com.ascentdev.wims.repository.RackUtilRepository;
import com.ascentdev.wims.repository.TruckListForReleasingDao;
import com.ascentdev.wims.repository.UserRepository;
import com.ascentdev.wims.service.CargoReleasingService;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class CargoReleasingServiceImp implements CargoReleasingService {

  @Autowired
  HawbRepository hRepo;

  @Autowired
  MawbTbRepository mtbRepo;

  @Autowired
  HawbForReleasingRepository hrRepo;

  @Autowired
  HawbForPullOutRepository hpRepo;

  @Autowired
  MawbForPullOutRepository mpRepo;

  @Autowired
  MawbForReleasingRepository mRepo;

  @Autowired
  MawbRepository m1Repo;

  @Autowired
  CargoReleasingRepository cRepo;

  @Autowired
  CargoReleasingImagesRepository iRepo;

  @Autowired
  ApprovedReleasingDao aRepo;

  @Autowired
  RackUtilRepository rRepo;

  @Autowired
  RackUtilHistoryRepository hiRepo;

  @Autowired
  CargoActivityLogsRepository cargoActivityRepo;

  @Autowired
  ImagesRepository imgRepo;

  @Autowired
  CargoStatusLogsRepository cargoLogsRepo;

  @Autowired
  OlrsRepository olrsRepo;

  @Autowired
  TruckListForReleasingDao truckListDao;

  @Autowired
  UserRepository userRepo;

  @Autowired
  private PushNotificationService pushNotifService;

  String filepath = "C:\\wms_paircargo\\WMS_SUPPORTING_DOCUMENTS\\images\\";

  List<ReleasingModelForSaving> listForReleasing = new ArrayList<>();

  @Override
  public ApiResponseModel getPullOutCargoList() {
    ApiResponseModel res = new ApiResponseModel();
    Connection conn = aRepo.connect();

    List<CargoStatusLogsEntity> approvedList = new ArrayList<>();
    List<CargoActivityLogsEntity> actLogsEntity = new ArrayList<>();
    List<ForCargoReleasingModel> list = new ArrayList<>();
    ForCargoReleasingModel item = new ForCargoReleasingModel();
    MawbForPullOutEntity mawb = new MawbForPullOutEntity();
    HawbForPullOutEntity hawb = new HawbForPullOutEntity();
    List<CargoActivityLogsEntity> actLogsForTransitEntity = new ArrayList<>();

    String message = "";
    int statusCode = 0;
    boolean isStatus = false;
    try {

      approvedList = cargoLogsRepo.getByStatus(Arrays.asList("approved_releasing", "released_on_hold"));
//      actLogsForTransitEntity = cargoActivityRepo.getTransitForPullOut();
      conn.close();
    } catch (SQLException ex) {
      Logger.getLogger(CargoReleasingServiceImp.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (approvedList.size() > 0) {
      for (CargoStatusLogsEntity m : approvedList) {

        if (m.isHawb()) {
          hawb = hpRepo.findById(m.getAwbId());
          if (hawb == null) {
            message = "No Data Found";
            statusCode = 404;
            isStatus = false;
          } else {
            actLogsEntity = cargoActivityRepo.getByHawbIdAndActivityStatus(m.getAwbId(), "STORED", false, false);

            if (actLogsEntity.size() > 0) {
              for (CargoActivityLogsEntity c : actLogsEntity) {
                item.setMawbId(hawb.getTxnMawbId());
                item.setHawbId(hawb.getId());
                item.setMawbNumber(hawb.getMawbNumber());
                item.setHawbNumber(hawb.getHawbNumber());
                item.setConsignee(hawb.getConsigneeName());
                item.setQty(hawb.getNumberOfPackages());
                item.setLocation(hawb.getLocation());
                item.setUom("PCS");
                item.setShawb(true);
                item.setUldId(c.getUldId());
                item.setCargoClass(hawb.getCargoClass());
                item.setIs_offload(c.isOffload());

                list.add(item);
                item = new ForCargoReleasingModel();

              }

            }

          }
        } else {
          mawb = mpRepo.findById(m.getAwbId());
          actLogsEntity = cargoActivityRepo.getByMawbIdAndActivityStatus(m.getAwbId(), "STORED", false, false);
          if (mawb == null) {
            message = "No Data Found";
            statusCode = 404;
            isStatus = false;
          } else {

            if (actLogsEntity.size() > 0) {
              for (CargoActivityLogsEntity c : actLogsEntity) {
                item.setMawbId(mawb.getId());
                item.setMawbNumber(mawb.getMawbNumber());
                item.setConsignee(mawb.getConsigneeName());
                item.setUom("PCS");
                item.setLocation(mawb.getLocation());
                item.setQty(mawb.getNumberOfPackages());
                item.setShawb(false);
                item.setUldId(c.getUldId());
                item.setCargoClass(mawb.getCargoClass());

                list.add(item);
                item = new ForCargoReleasingModel();
              }

            }

          }

        }

      }

    }

    if (actLogsForTransitEntity.size() > 0) {
      for (CargoActivityLogsEntity cal : actLogsForTransitEntity) {
        if (cal.getHawbId() == 0) {
          item = new ForCargoReleasingModel();
          mawb = new MawbForPullOutEntity();
          mawb = mpRepo.getTransitMawbForPullOut(cal.getMawbId());

          item.setMawbId(cal.getMawbId());
          item.setMawbNumber(mawb.getMawbNumber());
          item.setConsignee(mawb.getConsigneeName());
          item.setUom("PCS");
          item.setLocation(mawb.getLocation());
          item.setQty(mawb.getNumberOfPackages());
          item.setShawb(false);
          item.setUldId(cal.getUldId());
          item.setCargoClass(mawb.getCargoClass());

          list.add(item);

        } else {
          item = new ForCargoReleasingModel();
          hawb = new HawbForPullOutEntity();
          hawb = hpRepo.getTransitHawbForPullOut(cal.getHawbId());

          item.setMawbId(cal.getMawbId());
          item.setHawbId(cal.getHawbId());
          item.setMawbNumber(hawb.getMawbNumber());
          item.setHawbNumber(hawb.getHawbNumber());
          item.setConsignee(hawb.getConsigneeName());
          item.setQty(hawb.getNumberOfPackages());
          item.setLocation(hawb.getLocation());
          item.setUom("PCS");
          item.setShawb(true);
          item.setUldId(cal.getUldId());
          item.setCargoClass(hawb.getCargoClass());
          item.setIs_offload(cal.isOffload());

          list.add(item);
        }

      }

    }

    if (list.size() > 0) {
      message = "Found";
      statusCode = 200;
      isStatus = true;
    } else {
      message = "No Data Found";
      statusCode = 404;
      isStatus = false;
    }
    res.setData(list);
    res.setStatusCode(statusCode);
    res.setStatus(isStatus);
    res.setMessage(message);

    return res;

  }

  @Override
  public ApiResponseModel saveCargoReleasing(List<ReleasingModelForSaving> releasingModelForSaving, String trucker, String plateNo, int user_id) {

    ApiResponseModel res = new ApiResponseModel();
    HawbEntity hawb = new HawbEntity();
    MawbTbEntity mawb = new MawbTbEntity();
    List<CargoActivityLogsEntity> cargoLogs = new ArrayList<>();
    CargoActivityLogsEntity m = new CargoActivityLogsEntity();
    CargoReleasingEntity cargoEntity = new CargoReleasingEntity();
    CargoActivityLogsEntity histLogs = new CargoActivityLogsEntity();
    List<CargoActivityLogsEntity> respList = new ArrayList<>();

//    String stHawb = null;
//    String stMawb = null;
    LocalDateTime date = LocalDateTime.now();

    int data = 0;
    boolean status = false;
    int statusCode = 0;
    String message = "";

//    int mawb_id = 0;
//    int hawb_id = 0;
    if (releasingModelForSaving.size() > 0) {
      int count = 0;
      for (ReleasingModelForSaving r : releasingModelForSaving) {

        if (releasingModelForSaving.get(count).isShawb()) {
          cargoEntity.setAwb_id(releasingModelForSaving.get(count).getHawb_id());
        } else {
          cargoEntity.setAwb_id(releasingModelForSaving.get(count).getMawb_id());

        }

        cargoEntity.setHawb(releasingModelForSaving.get(count).isShawb());
        cargoEntity.setPlateNo(plateNo);
        cargoEntity.setTrucker(trucker);
        cargoEntity.setCreatedBy(user_id);
        cargoEntity.setCreatedDt(Timestamp.valueOf(date));

        cargoEntity = cRepo.save(cargoEntity);
        cargoEntity = new CargoReleasingEntity();

        if (r.isShawb()) {
          hawb = hRepo.findById(r.getHawb_id()).get();
          if (hawb.getCargoClassId() == 11) {
            cargoLogs = cargoActivityRepo.getByHawbIdAndActivityStatus(releasingModelForSaving.get(count).getHawb_id(), "RECEIVED", false, false);
          } else {
            cargoLogs = cargoActivityRepo.getByHawbIdAndActivityStatus(releasingModelForSaving.get(count).getHawb_id(), "PULL OUT", false, true);
          }

        } else {
          mawb = mtbRepo.findById(r.getMawb_id()).get();
          if (mawb.getCargoClassId() == 11) {
            cargoLogs = cargoActivityRepo.getByMawbIdAndActivityStatus(releasingModelForSaving.get(count).getMawb_id(), "RECEIVED", false, false);
          } else {
            cargoLogs = cargoActivityRepo.getByMawbIdAndActivityStatus(releasingModelForSaving.get(count).getMawb_id(), "PULL OUT", false, true);
          }
        }

//        if (cargoEntity == null) {
//          res.setData(0);
//          res.setStatus(false);
//          res.setMessage("Not saved");
//          res.setStatusCode(400);
//        } else {
//
//          if (r.isShawb()) {
//
//            hawb = hRepo.findById(releasingModelForSaving.get(count).getHawb_id()).get();
//            stHawb = hawb.getHawbNumber();
//            stMawb = hawb.getMawbNumber();
//            hawb_id = hawb.getId();
//          }
//
//          listForReleasing = releasingModelForSaving;
//        }
        if (cargoLogs.size() > 0) {

          int count1 = 0;
          for (CargoActivityLogsEntity cal : cargoLogs) {

            histLogs = cargoActivityRepo.findById(cal.getId());

            if (histLogs.getId() > 0) {
              histLogs.setReleased(true);
              cargoActivityRepo.save(histLogs);
            }

            m.setActivityStatus("RELEASED");
            m.setActualPcs(cargoLogs.get(count1).getActualPcs());
            m.setCreatedAt(cargoLogs.get(count1).getCreatedAt());
            m.setCreatedById(cargoLogs.get(count1).getCreatedById());
            m.setFlightId(cargoLogs.get(count1).getFlightId());
            m.setHandledById(cargoLogs.get(count1).getHandledById());
            m.setHawbId(cargoLogs.get(count1).getHawbId());
            m.setMawbId(cargoLogs.get(count1).getMawbId());
            m.setReceivedReleasedDate(Timestamp.valueOf(date));
            m.setLocation("RELEASING AREA");
            m.setUpdatedAt(Timestamp.valueOf(date));
            m.setUpdatedById(Long.valueOf(user_id));
            m.setReleased(true);
            m.setPullOut(true);

            cal.setReleased(true);
            cargoActivityRepo.save(cal);

            m = cargoActivityRepo.save(m);
            respList.add(m);
            m = new CargoActivityLogsEntity();
            count1++;
          }

          data = 1;
          status = true;
          statusCode = 200;
          message = "Successfully Released";

        } else {
          data = 0;
          status = false;
          statusCode = 404;
          message = "Saving of Cargo Logs was Unsuccessful";

        }

        count++;

      }

      res.setData(data);
      res.setLogList(respList);
      res.setStatus(status);
      res.setStatusCode(statusCode);
      res.setMessage(message);

    } else {

      res.setData(0);
      res.setStatus(false);
      res.setStatusCode(404);
      res.setMessage("Saving of Truck Info was Unsuccessful");

    }

    return res;
  }

  @Override
  public ApiResponseModel savePullOut(List<PullOutCargoModel> model, int user_id) {
    ApiResponseModel resp = new ApiResponseModel();

    List<RackUtilEntity> rEntity = new ArrayList<>();
    RackUtilHistoryEntity hisEntity = new RackUtilHistoryEntity();
    List<CargoActivityLogsEntity> cargoLogs = new ArrayList<>();
    CargoActivityLogsEntity cargo = new CargoActivityLogsEntity();
    HawbEntity hawb = new HawbEntity();
    MawbTbEntity mawb = new MawbTbEntity();
    List<CargoActivityLogsEntity> respList = new ArrayList<>();

    int count = 0;
    for (PullOutCargoModel m : model) {
      rEntity = model.get(count).isShawb() ? rRepo.findByTxnHawbId(model.get(count).getHawb_id()) : rRepo.findByTxnMawbId(model.get(count).getMawb_id());

      if (model.get(count).isShawb()) {
        hawb = hRepo.findById(model.get(count).getHawb_id()).get();
        if (hawb.getCargoClassId() == 9 || hawb.getCargoClassId() == 10) {
          cargoLogs = cargoActivityRepo.getByHawbIdAndActivityStatusAndUldId(model.get(count).getHawb_id(), "RECEIVED", false, false, model.get(count).getUld_id());
        } else {
          cargoLogs = cargoActivityRepo.getByHawbIdAndActivityStatusAndUldId(model.get(count).getHawb_id(), "STORED", false, false, model.get(count).getUld_id());
        }
      } else {
        mawb = mtbRepo.findById(model.get(count).getMawb_id()).get();
        if (mawb.getCargoClassId() == 9 || mawb.getCargoClassId() == 10) {
          cargoLogs = cargoActivityRepo.getByMawbIdAndActivityStatusAndUldId(model.get(count).getMawb_id(), "RECEIVED", false, false, model.get(count).getUld_id());
        } else {
          cargoLogs = cargoActivityRepo.getByMawbIdAndActivityStatusAndUldId(model.get(count).getMawb_id(), "STORED", false, false, model.get(count).getUld_id());
        }
      }

      if (!cargoLogs.isEmpty()) {

        int count1 = 0;
        for (CargoActivityLogsEntity c : cargoLogs) {

          LocalDateTime date = LocalDateTime.now();
          cargo.setActivityStatus("PULL OUT");
          cargo.setUldId(c.getUldId());
          cargo.setActualPcs(cargoLogs.get(count1).getActualPcs());
          cargo.setCreatedAt(cargoLogs.get(count1).getCreatedAt());
          cargo.setCreatedById(cargoLogs.get(count1).getCreatedById());
          cargo.setFlightId(cargoLogs.get(count1).getFlightId());
          cargo.setHandledById(cargoLogs.get(count1).getHandledById());
          cargo.setHawbId(cargoLogs.get(count1).getHawbId());
          cargo.setMawbId(cargoLogs.get(count1).getMawbId());
          cargo.setReceivedReleasedDate(Timestamp.valueOf(date));
          cargo.setStored(true);
          cargo.setPullOut(true);
          cargo.setLocation("BAY AREA");
          cargo.setUpdatedAt(Timestamp.valueOf(date));
          cargo.setUpdatedById(Long.valueOf(user_id));

          c.setPullOut(true);
          cargoActivityRepo.save(c);

          c = cargoActivityRepo.save(cargo);
          respList.add(c);
          cargo = new CargoActivityLogsEntity();
          count1++;
        }

        if (rEntity.size() > 0) {
          int count2 = 0;
          for (RackUtilEntity e : rEntity) {
            hisEntity.setCreatedAt(rEntity.get(count2).getCreatedAt());
            hisEntity.setCreatedById(rEntity.get(count2).getCreatedById());
            hisEntity.setLocation(rEntity.get(count2).getLocation());
            hisEntity.setModifiedAt(rEntity.get(count2).getModifiedAt());
            hisEntity.setModifiedById(rEntity.get(count2).getModifiedById());
            hisEntity.setNoOfPieces(rEntity.get(count2).getNoOfPieces());
            hisEntity.setRefRackId(rEntity.get(count2).getRefRackId());
            hisEntity.setStoredById(rEntity.get(count2).getStoredById());
            hisEntity.setStoredDt(rEntity.get(count2).getStoredDt());
            hisEntity.setTxnHawbId(rEntity.get(count2).getTxnHawbId());
            hisEntity.setTxnMawbId(rEntity.get(count2).getTxnMawbId());
            hisEntity.setTxnRackUtilizationId(rEntity.get(count2).getId());
            hisEntity.setVolume(rEntity.get(count2).getVolume());

            hiRepo.save(hisEntity);
            rRepo.delete(rEntity.get(count2));
            hisEntity = new RackUtilHistoryEntity();
            count2++;
          }
        }
//        else {
//          resp.setStatus(false);
//          resp.setStatusCode(404);
//          resp.setMessage("Saving of Pull-out was unsuccessful");
//          break;
//        }

      } else {
        resp.setStatus(false);
        resp.setStatusCode(404);
        resp.setMessage("Saving of Pull-out was unsuccessful");
        break;
      }

      List<String> list = userRepo.getNotificationTokenForStoring();
      for (String s : list) {
        System.out.println("NOTIFICATION TOKEN>>>>>>> " + s);
        getNotification(new PushNotificationRequest("WIMS MOBILE APP", "MAWB No. [" + mawb.getMawbNumber() + "] is Ready to RELEASE", "", s));

      }

      count++;
      resp.setLogList(respList);
      resp.setStatus(true);
      resp.setStatusCode(200);
      resp.setMessage("Successfully saved Pull-out cargo");

    }

    return resp;
  }

  @Override
  public ApiResponseModel getForReleasingCargo(String yellow_receipt) {
    ApiResponseModel res = new ApiResponseModel();
    Connection conn = aRepo.connect();

    List<CargoStatusLogsEntity> approvedList = new ArrayList<>();
    List<CargoActivityLogsEntity> actLogsEntity = new ArrayList<>();
    List<ForCargoReleasingModel> list = new ArrayList<>();
    ForCargoReleasingModel item = new ForCargoReleasingModel();
    MawbForReleasingEntity mawb = new MawbForReleasingEntity();
    MawbForReleasingEntity mawbForTrans = new MawbForReleasingEntity();
    HawbForReleasingEntity hawb = new HawbForReleasingEntity();
    List<CargoActivityLogsEntity> actLogsForTransitEntity = new ArrayList<>();

    String message = "";
    int statusCode = 0;
    boolean isStatus = false;
    try {
      approvedList = cargoLogsRepo.getByStatus(Arrays.asList("approved_releasing", "released_on_hold"));
      actLogsForTransitEntity = cargoActivityRepo.getTransitForReleasing();
      conn.close();
    } catch (SQLException ex) {
      Logger.getLogger(CargoReleasingServiceImp.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (approvedList.size() > 0) {

      for (CargoStatusLogsEntity m : approvedList) {
        item = new ForCargoReleasingModel();

        if (!m.isHawb()) {
          mawb = mRepo.findByYellowReceipt(yellow_receipt);
          if (mawb == null) {
            message = "No Data Found";
            statusCode = 404;
            isStatus = false;
          } else {
            actLogsEntity = cargoActivityRepo.getByMawbIdAndActivityStatus(m.getAwbId(), "PULL OUT", false, true);
            if (mawb != null) {
              if (mawb.getId() == m.getAwbId()) {

                if (actLogsEntity.size() > 0) {
                  item.setMawbId(mawb.getId());
                  item.setMawbNumber(mawb.getMawbNumber());
                  item.setConsignee(mawb.getConsigneeName());
                  item.setUom("PCS");
                  item.setQty(mawb.getNumberOfPackages());
                  item.setCargoClass(mawb.getCargoClass());
                  item.setShawb(false);

                  list.add(item);
                }
              }

            }

          }
        } else {
          hawb = hrRepo.findByYellowReceipt(yellow_receipt);
          if (hawb == null) {
            message = "No Data Found";
            statusCode = 404;
            isStatus = false;
          } else {

            actLogsEntity = cargoActivityRepo.getByHawbIdAndActivityStatus(hawb.getId(), "PULL OUT", false, true);

            if (actLogsEntity.size() > 0) {

              if (hawb.getId() == m.getAwbId()) {
                item.setMawbId(hawb.getTxnMawbId());
                item.setHawbId(hawb.getId());
                item.setMawbNumber(hawb.getMawbNumber());
                item.setHawbNumber(hawb.getHawbNumber());
                item.setConsignee(hawb.getConsigneeName());
                item.setQty(hawb.getNumberOfPackages());
                item.setUom("PCS");
                item.setCargoClass(hawb.getCargoClass());
                item.setShawb(true);

                list.add(item);
              }

            }

          }

        }

      }

    }

//    if (actLogsForTransitEntity.size() > 0) {
//      for (CargoActivityLogsEntity cal : actLogsForTransitEntity) {
//        if (cal.getHawbId() == 0) {
//          item = new ForCargoReleasingModel();
//          mawb = new MawbForReleasingEntity();
//          mawbForTrans = new MawbForReleasingEntity();
//          mawbForTrans = mRepo.findByYellowReceipt(yellow_receipt);
//          mawb = mRepo.getTransitMawbForReleasing(cal.getMawbId());
//          actLogsEntity = cargoActivityRepo.getByMawbIdAndActivityStatus(cal.getMawbId(), "PULL OUT", false, true);
//          if (actLogsEntity.size() > 0) {
//            if(mawbForTrans != null){
//              
//              if (mawbForTrans.getId() == mawb.getId()) {
//                item.setMawbId(mawb.getId());
//                item.setMawbNumber(mawb.getMawbNumber());
//                item.setConsignee(mawb.getConsigneeName());
//                item.setUom("PCS");
//                item.setQty(mawb.getNumberOfPackages());
//                item.setCargoClass(mawb.getCargoClass());
//                item.setShawb(false);
//
//                list.add(item);
//              }
//            }
//
//          }
//
//        } else {
//          item = new ForCargoReleasingModel();
//          hawb = new HawbForReleasingEntity();
//          hawb = hrRepo.getTransitHawbForReleasing(cal.getHawbId());
//
//          if (hawb != null) {
//            actLogsEntity = cargoActivityRepo.getByHawbIdAndActivityStatus(hawb.getId(), "PULL OUT", false, true);
//            if (actLogsEntity.size() > 0) {
//              item.setMawbId(hawb.getTxnMawbId());
//              item.setHawbId(hawb.getId());
//              item.setMawbNumber(hawb.getMawbNumber());
//              item.setHawbNumber(hawb.getHawbNumber());
//              item.setConsignee(hawb.getConsigneeName());
//              item.setQty(hawb.getNumberOfPackages());
//              item.setUom("PCS");
//              item.setCargoClass(hawb.getCargoClass());
//              item.setShawb(true);
//
//              list.add(item);
//            }
//          }
//
//        }
//
//      }
//
//    }
    if (list.size() > 0) {
      message = "Found";
      statusCode = 200;
      isStatus = true;
    } else {
      message = "No Data Found";
      statusCode = 404;
      isStatus = false;
    }
    res.setData(list);
    res.setStatusCode(statusCode);
    res.setStatus(isStatus);
    res.setMessage(message);

    return res;

  }

  @Override
  public ApiResponseModel getTruckList() {
    ApiResponseModel res = new ApiResponseModel();
    Connection conn = truckListDao.connect();
    List<TruckListModel> truckList = new ArrayList<>();
    try {
      truckList = truckListDao.getTruckList(conn);
      if (truckList.size() > 0) {
        res.setTruckList(truckList);
        res.setMessage("Data Found");
        res.setStatus(true);
        res.setStatusCode(200);
      } else {
        res.setMessage("No Found");
        res.setStatus(false);
        res.setStatusCode(404);
      }

    } catch (Exception e) {
      res.setMessage(e.getMessage());
      res.setStatus(false);
      res.setStatusCode(404);
    }

    return res;

  }

  public void getNotification(PushNotificationRequest request) {
    pushNotifService.sendPushNotificationToToken(request);
    System.out.println("princr");
  }

}
