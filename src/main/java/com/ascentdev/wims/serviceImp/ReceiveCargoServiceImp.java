/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.PushNotificationServices.PushNotificationService;
import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.CargoCategoryEntity;
import com.ascentdev.wims.entity.CargoClassEntity;
import com.ascentdev.wims.entity.CargoConditionEntity;
import com.ascentdev.wims.entity.CargoImagesEntity;
import com.ascentdev.wims.entity.CargoStatusEntity;
import com.ascentdev.wims.entity.CheckOffloadedMawbEntity;
import com.ascentdev.wims.entity.UldImagesEntity;
import com.ascentdev.wims.entity.FlightsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.HawbForReceivingEntity;
import com.ascentdev.wims.entity.ImagesEntity;
import com.ascentdev.wims.entity.JobAssignmentEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.MawbTbEntity;
import com.ascentdev.wims.entity.PartialCargoPerULDEntity;
import com.ascentdev.wims.entity.RackUtilEntity;
import com.ascentdev.wims.entity.ReceivingLogsEntity;
import com.ascentdev.wims.entity.RefCargoReleaseGateEntity;
import com.ascentdev.wims.entity.RefRackLayerEntity;
import com.ascentdev.wims.entity.RefShipmentStatusEntity;
import com.ascentdev.wims.entity.RefULDEntity;
import com.ascentdev.wims.entity.TxnCargoVideosEntity;
import com.ascentdev.wims.entity.TxnUldsEntity;
import com.ascentdev.wims.entity.UldActivityLogsEntity;
import com.ascentdev.wims.entity.UldContainerTypeEntity;
import com.ascentdev.wims.entity.UldTypeEntity;
import com.ascentdev.wims.entity.UldsEntity;
import com.ascentdev.wims.entity.UnmanifestedEntity;
import com.ascentdev.wims.error.ErrorException;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.CargoCategoryModel;
import com.ascentdev.wims.model.CargoClassModel;
import com.ascentdev.wims.model.CargoConditionModel;
import com.ascentdev.wims.model.CargoStatusModel;
import com.ascentdev.wims.model.HawbModel;
import com.ascentdev.wims.model.MawbListModel;
import com.ascentdev.wims.model.MawbModel;
import com.ascentdev.wims.model.PushNotificationRequest;
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
import com.ascentdev.wims.repository.CheckOffloadedMawbRepository;
import com.ascentdev.wims.repository.FlightsRepository;
import com.ascentdev.wims.repository.HawbForReceivingRepository;
import com.ascentdev.wims.repository.HawbRepository;
import com.ascentdev.wims.repository.ImagesRepository;
import com.ascentdev.wims.repository.JobAssignmentRepository;
import com.ascentdev.wims.repository.MawbRepository;
import com.ascentdev.wims.repository.MawbTbRepository;
import com.ascentdev.wims.repository.PartialCargoPerULDRepository;
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
import com.ascentdev.wims.repository.RefCargoReleaseGateRepository;
import com.ascentdev.wims.repository.RefShipmentStatusRepository;
import com.ascentdev.wims.repository.UldActivityLogsRepository;
import com.ascentdev.wims.repository.RefRackLayerRepository;
import com.ascentdev.wims.repository.TxnCargoVideosRepository;
import com.ascentdev.wims.repository.UnmanifestedRepository;
import com.ascentdev.wims.repository.UserRepository;
import java.io.File;
import java.sql.Date;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ASCENT
 */
@Service
public class ReceiveCargoServiceImp implements ReceiveCargoService {

  boolean status = true;
  String message = "Success!";
  int statusCode = 200;

  String fileUploadPath = "C:\\wms_paircargo\\WMS_SUPPORTING_DOCUMENTS\\images\\";
  private static final String fileUploadPathVideo = "C:\\wms_paircargo\\WMS_SUPPORTING_DOCUMENTS\\videos\\" + ((DateTimeFormatter.ofPattern("yyyy-MM")).format(LocalDateTime.now()));

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
  RefRackLayerRepository rrRepo;

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
  TxnCargoVideosRepository vidRepo;

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

  @Autowired
  PartialCargoPerULDRepository partialRepo;

  @Autowired
  UnmanifestedRepository unmanifestedRepo;

  @Autowired
  CheckOffloadedMawbRepository offloadRepo;

  @Autowired
  RefCargoReleaseGateRepository releaseGateRepo;

  @Autowired
  UserRepository userRepo;

  @Autowired
  private PushNotificationService pushNotifService;

  @Override
  public ApiResponseModel searchFlights(long userId) {
    ApiResponseModel resp = new ApiResponseModel();
    SearchFlightsModel data = new SearchFlightsModel();

    List<FlightsEntity> flights = new ArrayList<>();
    List<UldsEntity> ulds = new ArrayList<>();

    try {
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
  public ApiResponseModel getUlds(String flightNumber, String registryNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    UldsModel data = new UldsModel();

    List<UldsEntity> ulds = new ArrayList<>();

    try {
      ulds = uRepo.findByFlightNumberAndRegistryNumber(flightNumber, registryNumber);
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
  public ApiResponseModel getMawbs(boolean isUld, String uldNumber, String flightNumber, String registry_number) {
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
    List<HawbForReceivingEntity> checkHawbIfExist = new ArrayList<>();

    try {

      if (isUld) {
        mawb = mRepo.findByUldNumberAndUldStatusNotAndIsReceivedAndRegistryNumber(uldNumber, 10, false, registry_number);
        if (mawb.size() > 0) {
          hawb = hRepo.findByMawbNumber(mawb.get(0).getMawbNumber());
        }

        for (HawbEntity h : hawb) {
          checkHawbIfExist = new ArrayList<>();
          checkHawbIfExist = hrRepo.findByCustomId(h.getId());
          for (HawbForReceivingEntity ch : checkHawbIfExist) {
            if (Boolean.valueOf(ch.getResult().contains("t"))) {
              h1.add(h);
              break;
            }
          }

        }

        if (mawb.size() == 0) {
          message = "No Data to Show";
          status = false;
          statusCode = 404;
        } else {
          if (h1.size() == 0) {
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
    List<HawbForReceivingEntity> checkHawbIfExist = new ArrayList<>();
    List<HawbEntity> hawbs = new ArrayList<>();
    List<HawbEntity> hawbs1 = new ArrayList<>();
    try {
      hawbs = hRepo.findByMawbNumber(mawbNumber);
      for (HawbEntity h : hawbs) {
        checkHawbIfExist = new ArrayList<>();
        checkHawbIfExist = hrRepo.findByCustomId(h.getId());
        for (HawbForReceivingEntity ch : checkHawbIfExist) {
          if (Boolean.valueOf(ch.getResult().contains("t"))) {
            hawbs1.add(h);
          }
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

  @Override
  public ApiResponseModel confirmCargo(CargoActivityLogsEntity cargoLogs, MawbEntity mawbDetails, HawbEntity hawbDetails, String mawb_number, String flightNumber, String hawb_number, int userId, String cargoCategory, String cargoClass, String uld_number, boolean is_badOrder, String shipment_status, boolean is_partial, int uld_id, String registry_number, boolean is_offload, String received_registry_no, String gate_releasing) {
    ErrorException ex1 = null;
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    List<HawbEntity> hawbs = new ArrayList<>();
    List<HawbEntity> checkHawbSize = new ArrayList<>();
    List<HawbEntity> h1 = new ArrayList<>();
    List<MawbEntity> checkMawbSize = new ArrayList<>();
    List<MawbTbEntity> mawbs = new ArrayList<>();
    List<RefRackLayerEntity> refRack = new ArrayList<>();
    List<RackUtilEntity> rackList = new ArrayList<>();
    List<JobAssignmentEntity> jobAssigns = new ArrayList<>();

    CargoActivityLogsEntity cargoEntity = new CargoActivityLogsEntity();
    List<CargoActivityLogsEntity> updateToFullLogs = new ArrayList<>();
    List<CargoActivityLogsEntity> logsList = new ArrayList<>();
    HawbEntity hawb1 = new HawbEntity();
    CargoCategoryEntity category = new CargoCategoryEntity();
    CargoClassEntity cargoClass1 = new CargoClassEntity();
    MawbTbEntity mawb2 = new MawbTbEntity();
    RackUtilEntity rack = new RackUtilEntity();
    FlightsEntity flights = new FlightsEntity();
    UldsEntity uld = new UldsEntity();
    List<TxnUldsEntity> txnUldEntity = new ArrayList<>();
    TxnUldsEntity updateUldIfReceived = new TxnUldsEntity();
    RefULDEntity refUld = new RefULDEntity();
    List<HawbForReceivingEntity> checkHawbIfExist = new ArrayList<>();

    RefCargoReleaseGateEntity releaseGateEntity = new RefCargoReleaseGateEntity();

    RefShipmentStatusEntity shipmentStat = new RefShipmentStatusEntity();
    float tempV = 0;

    int hawb_previous_act_pcs = 0;
    float hawb_prev_act_weight = 0;
    int mawb_previous_act_pcs = 0;
    float mawb_prev_act_weight = 0;

    try {
      refUld = refUldRepo.findByUldNo(uld_number);
      refRack = rrRepo.findAll();
      flights = fRepo.findByFlightNumber(flightNumber);
      hawbs = hRepo.findByMawbNumberAndHawbNumber(mawb_number, hawb_number);
      mawb2 = m3Repo.findByMawbNumberAndRegistryNumber(mawb_number, registry_number);
      category = ccRepo.findByDescription(cargoCategory);
      cargoClass1 = classRepo.findByClassdesc(cargoClass);

      if (!gate_releasing.equals("")) {
        releaseGateEntity = releaseGateRepo.findByName(gate_releasing);
        mawb2.setGateReleasingId(releaseGateEntity.getId());
      }

      if (shipment_status != null && !shipment_status.equals("")) {
        shipmentStat = shipmentRepo.findByName(shipment_status);
      }
      txnUldEntity = tuRepo.findByUldNumber(uld_number);

      if (refUld.getId() != 0) {

        checkMawbSize = mRepo.findByUldNumberAndIsReceived(uld_number, false);
        if (checkMawbSize.size() <= 1) {
          checkHawbSize = hRepo.findByMawbNumber(mawb_number);

          for (HawbEntity h : checkHawbSize) {
            checkHawbIfExist = new ArrayList<>();
            checkHawbIfExist = hrRepo.findByCustomId(h.getId());
            for (HawbForReceivingEntity ch : checkHawbIfExist) {
              if (Boolean.valueOf(ch.getResult().contains("t"))) {
                h1.add(h);
              }
            }

          }

          if (h1.size() <= 1) {
            refUld.setUldStatus(10);
            refUldRepo.save(refUld);

            updateUldIfReceived = new TxnUldsEntity();
            updateUldIfReceived = tuRepo.findById(mawbDetails.getUldId()).get();
            updateUldIfReceived.setIsReceived(true);
            tuRepo.save(updateUldIfReceived);

          }

        } else {

          checkHawbSize = hRepo.findByMawbNumber(mawb_number);

          for (HawbEntity h : checkHawbSize) {
            checkHawbIfExist = new ArrayList<>();
            checkHawbIfExist = hrRepo.findByCustomId(h.getId());
            for (HawbForReceivingEntity ch : checkHawbIfExist) {
              if (Boolean.valueOf(ch.getResult().contains("t"))) {
                h1.add(h);
              }
            }
          }

          if (h1.size() <= 1) {
            updateUldIfReceived = new TxnUldsEntity();
            updateUldIfReceived = tuRepo.findById(mawbDetails.getUldId()).get();
            updateUldIfReceived.setIsReceived(true);
            tuRepo.save(updateUldIfReceived);
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

            if (hawb1.getId() > 0) {

              if (hawb1.getActualPcs() != null && hawb1.getActualWeight() != null) {
                hawb_previous_act_pcs = hawb1.getActualPcs();
                hawb_prev_act_weight = hawb1.getActualWeight();
                mawb_previous_act_pcs = mawb2.getActualPcs();

                if (mawbDetails.getActualPcs() > mawb_previous_act_pcs) {
                  mawb2.setStatusId(14);
                }

                hawb1.setActualPcs(mawbDetails.getActualPcs() + hawb_previous_act_pcs);
                hawb1.setActualWeight(mawbDetails.getActualWeight() + hawb_prev_act_weight);

                mawb2.setActualPcs(mawbDetails.getActualPcs() + hawb_previous_act_pcs);
                mawb2.setActualWeight(mawbDetails.getActualWeight() + hawb_prev_act_weight);

                cargoEntity.setActualPcs(mawbDetails.getActualPcs());
              } else {
                hawb1.setActualPcs(mawbDetails.getActualPcs());
                hawb1.setActualWeight(mawbDetails.getActualWeight());

                mawb2.setActualPcs(mawbDetails.getActualPcs());
                mawb2.setActualWeight(mawbDetails.getActualWeight());

                cargoEntity.setActualPcs(mawbDetails.getActualPcs());
              }

//              mawb2.setCargoClassId(cargoClass1.getId());
//              mawb2.setCargoCategoryId(category.getId());
              hawb1.setCargoClassId(cargoClass1.getId());
              hawb1.setCargoCategoryId(category.getId());

              if (is_offload) {
                hawb1.setReceivedRegistryNo(received_registry_no);
                mawb2.setStatusId(17);
              }
              if (is_partial) {
                mawb2.setStatusId(11);
              }
              hawb1.setActualVolume(mawbDetails.getVolume() != null ? mawbDetails.getVolume() : hawb1.getActualVolume());
              hawb1.setLength(mawbDetails.getLength() != null ? mawbDetails.getLength() : hawb1.getLength());
              hawb1.setWidth(mawbDetails.getWidth() != null ? mawbDetails.getWidth() : hawb1.getWidth());
              hawb1.setHeight(mawbDetails.getHeight() != null ? mawbDetails.getHeight() : hawb1.getHeight());

              m3Repo.save(mawb2);
              hawb1 = hRepo.save(hawb1);
            } else {

              //      -- SAVE TO TXN MAWB TABLE (ADD DATA)
              if (mawb2.getActualPcs() != null && mawb2.getActualWeight() != null) {
                mawb_previous_act_pcs = mawb2.getActualPcs();
                mawb_prev_act_weight = mawb2.getActualWeight();

                if (mawbDetails.getActualPcs() > mawb_previous_act_pcs) {
                  mawb2.setStatusId(14);
                }

                mawb2.setActualPcs(mawbDetails.getActualPcs() + mawb_previous_act_pcs);
                mawb2.setActualWeight(mawbDetails.getActualWeight() + mawb_prev_act_weight);
                cargoEntity.setActualPcs(mawbDetails.getActualPcs());
              } else {
                mawb2.setActualPcs(mawbDetails.getActualPcs());
                mawb2.setActualWeight(mawbDetails.getActualWeight());
                cargoEntity.setActualPcs(mawbDetails.getActualPcs());
              }

              mawb2.setCargoClassId(cargoClass1.getId());
              mawb2.setCargoCategoryId(category.getId());

              if (is_offload) {
                mawb2.setReceivedRegistryNo(received_registry_no);
                mawb2.setStatusId(17);
              }
              if (is_partial) {
                mawb2.setStatusId(11);
              }
              mawb2.setActualVolume(mawbDetails.getVolume() != null ? mawbDetails.getVolume() : mawb2.getActualVolume());
              mawb2.setLength(mawbDetails.getLength() != null ? mawbDetails.getLength() : mawb2.getLength());
              mawb2.setWidth(mawbDetails.getWidth() != null ? mawbDetails.getWidth() : mawb2.getWidth());
              mawb2.setHeight(mawbDetails.getHeight() != null ? mawbDetails.getHeight() : mawb2.getHeight());

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
            if (is_partial) {
              cargoEntity.setPartial(is_partial);
              cargoEntity.setFull(false);
            } else {
              updateToFullLogs = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatus(mawb2.getId(), hawb1.getId(), "RECEIVED");
              if (updateToFullLogs.size() > 0) {
                for (CargoActivityLogsEntity log : updateToFullLogs) {
                  log.setPartial(false);
                  log.setFull(true);
                  cargoActivityRepo.save(log);
                  log = new CargoActivityLogsEntity();
                }
                cargoEntity.setPartial(is_partial);
                cargoEntity.setFull(true);
              } else {
                cargoEntity.setPartial(is_partial);
                cargoEntity.setFull(true);
              }
            }
            cargoEntity.setUldId(uld_id);

            cargoEntity.setReceivedReleasedDate(Timestamp.valueOf(new Dates().getCurrentDateTime()));
            cargoEntity.setActualPcs(mawbDetails.getActualPcs());
            cargoEntity.setLocation("RECEIVING AREA");
            cargoEntity.setMawbId(mawb2.getId());
            cargoEntity.setHawbId(hawb1.getId());
            cargoEntity.setFlightId(flights.getId());
            cargoEntity.setCreatedAt(Timestamp.valueOf(new Dates().getCurrentDateTime()));
            cargoEntity.setCreatedById(userId);
            cargoEntity.setActivityStatus("BAD ORDER");
            cargoEntity.setOffload(is_offload);
            if (shipmentStat.getCode() != null && !shipmentStat.getCode().equals("")) {
              cargoEntity.setStatusCode(shipmentStat.getCode());
            } else {
              cargoEntity.setStatusCode("N");
            }
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

            if (hawb1.getId() != 0) {

              if (hawb1.getActualPcs() != null && hawb1.getActualWeight() != null) {
                hawb_previous_act_pcs = hawb1.getActualPcs();
                hawb_prev_act_weight = hawb1.getActualWeight();

                if (mawbDetails.getActualPcs() > hawb_previous_act_pcs) {
                  mawb2.setStatusId(14);
                }

                hawb1.setActualPcs(mawbDetails.getActualPcs() + hawb_previous_act_pcs);
                hawb1.setActualWeight(mawbDetails.getActualWeight() + hawb_prev_act_weight);

                mawb2.setActualPcs(mawbDetails.getActualPcs() + hawb_previous_act_pcs);
                mawb2.setActualWeight(mawbDetails.getActualWeight() + hawb_prev_act_weight);

                cargoEntity.setActualPcs(mawbDetails.getActualPcs());
              } else {
                hawb1.setActualPcs(mawbDetails.getActualPcs());
                hawb1.setActualWeight(mawbDetails.getActualWeight());

                mawb2.setActualPcs(mawbDetails.getActualPcs());
                mawb2.setActualWeight(mawbDetails.getActualWeight());

                cargoEntity.setActualPcs(mawbDetails.getActualPcs());
              }

//              mawb2.setCargoClassId(cargoClass1.getId());
//              mawb2.setCargoCategoryId(category.getId());
              hawb1.setCargoClassId(cargoClass1.getId());
              hawb1.setCargoCategoryId(category.getId());

              if (is_offload) {
                hawb1.setReceivedRegistryNo(received_registry_no);
                mawb2.setStatusId(17);
              }
              if (is_partial) {
                mawb2.setStatusId(11);
              }

              hawb1.setActualVolume(mawbDetails.getVolume() != null ? mawbDetails.getVolume() : hawb1.getActualVolume());
              hawb1.setLength(mawbDetails.getLength() != null ? mawbDetails.getLength() : hawb1.getLength());
              hawb1.setWidth(mawbDetails.getWidth() != null ? mawbDetails.getWidth() : hawb1.getWidth());
              hawb1.setHeight(mawbDetails.getHeight() != null ? mawbDetails.getHeight() : hawb1.getHeight());

              hawb1 = hRepo.save(hawb1);
              m3Repo.save(mawb2);
              hawbs.add(hawb1);
            } else {

              if (mawb2.getActualPcs() != null && mawb2.getActualWeight() != null) {
                mawb_previous_act_pcs = mawb2.getActualPcs();
                mawb_prev_act_weight = mawb2.getActualWeight();

                if (mawbDetails.getActualPcs() > mawb_previous_act_pcs) {
                  mawb2.setStatusId(14);
                }

                mawb2.setActualPcs(mawbDetails.getActualPcs() + mawb_previous_act_pcs);
                mawb2.setActualWeight(mawbDetails.getActualWeight() + mawb_prev_act_weight);
                cargoEntity.setActualPcs(mawbDetails.getActualPcs());
              } else {
                mawb2.setActualPcs(mawbDetails.getActualPcs());
                mawb2.setActualWeight(mawbDetails.getActualWeight());
                cargoEntity.setActualPcs(mawbDetails.getActualPcs());
              }
              //      -- SAVE TO TXN MAWB TABLE (ADD DATA)
              mawb2.setCargoClassId(cargoClass1.getId());
              mawb2.setCargoCategoryId(category.getId());

              if (is_offload) {
                mawb2.setReceivedRegistryNo(received_registry_no);
                mawb2.setStatusId(17);
              }
              if (is_partial) {
                mawb2.setStatusId(11);
              }

              mawb2.setActualVolume(mawbDetails.getVolume() != null ? mawbDetails.getVolume() : mawb2.getActualVolume());
              mawb2.setLength(mawbDetails.getLength() != null ? mawbDetails.getLength() : mawb2.getLength());
              mawb2.setWidth(mawbDetails.getWidth() != null ? mawbDetails.getWidth() : mawb2.getWidth());
              mawb2.setHeight(mawbDetails.getHeight() != null ? mawbDetails.getHeight() : mawb2.getHeight());

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
            if (is_partial) {
              cargoEntity.setPartial(is_partial);
              cargoEntity.setFull(false);
            } else {
              updateToFullLogs = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatus(mawb2.getId(), hawb1.getId(), "RECEIVED");
              if (updateToFullLogs.size() > 0) {
                for (CargoActivityLogsEntity log : updateToFullLogs) {
                  log.setPartial(false);
                  log.setFull(true);
                  cargoActivityRepo.save(log);
                }
                cargoEntity.setPartial(is_partial);
                cargoEntity.setFull(true);
              } else {
                cargoEntity.setPartial(is_partial);
                cargoEntity.setFull(true);
              }
            }
            cargoEntity.setUldId(uld_id);

            cargoEntity.setReceivedReleasedDate(Timestamp.valueOf(new Dates().getCurrentDateTime()));
            cargoEntity.setLocation("RECEIVING AREA");
            cargoEntity.setMawbId(mawb2.getId());
            cargoEntity.setHawbId(hawb1.getId());
            cargoEntity.setFlightId(flights.getId());
            cargoEntity.setCreatedAt(Timestamp.valueOf(new Dates().getCurrentDateTime()));
            cargoEntity.setCreatedById(userId);
            cargoEntity.setActivityStatus("RECEIVED");
            cargoEntity.setOffload(is_offload);
            if (shipmentStat.getCode() != null && !shipmentStat.getCode().equals("")) {
              cargoEntity.setStatusCode(shipmentStat.getCode());
            } else {
              cargoEntity.setStatusCode("N");

            }
            cargoEntity = cargoActivityRepo.save(cargoEntity);
            logsList.add(cargoEntity);

          } else {
            ex1 = new ErrorException(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, "No MAWB NUMBER Found", System.currentTimeMillis());
            throw ex1;
          }

        }

        List<String> list = userRepo.getNotificationTokenForStoring();
        for (String s : list) {
          System.out.println("NOTIFICATION TOKEN>>>>>>> " + s);
          getNotification(new PushNotificationRequest("WIMS MOBILE APP", "MAWB No. [" + mawb2.getMawbNumber() + "] is Ready to STORE", "", s));
          
        }

        resp.setLogList(logsList);
        resp.setCargoActivityLogsId(cargoEntity.getId());
        resp.setMessage("Saved Successfully");
        resp.setStatus(true);
        resp.setStatusCode(200);

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
          String flightNumber,
          String uldNumber,
          String remarks[],
          String uldCondition[],
          int user_id) {
    ApiResponseModel resp = new ApiResponseModel();
    CargoConditionEntity condition = new CargoConditionEntity();
    FlightsEntity flights = new FlightsEntity();
    UldsEntity ulds = new UldsEntity();
    RefULDEntity refUlds = new RefULDEntity();
    UldActivityLogsEntity uldLogs = new UldActivityLogsEntity();

    LocalDateTime date = LocalDateTime.now();
    try {
      int count = 0;

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
          condition = cargoRepo.findByCondition(uldCondition[count]);
          UldImagesEntity images = new UldImagesEntity();
          String filename = f.getOriginalFilename();
          images.setFilePath(fileUploadPath + filename);
          images.setFileName(filename);
          if (remarks.length > 0) {
            images.setRemarks(remarks[count]);
          } else {
            images.setRemarks(null);
          }
          images.setUldNumber(ulds.getUldNumber());
          images.setUldConditionId(condition.getId());
          images.setFlightNumber(flights.getFlightNumber());
          iRepo.save(images);
          condition = new CargoConditionEntity();
          count++;
        }

      } else {
        resp.setMessage("Image Did Not Upload");
        resp.setStatus(false);
        resp.setStatusCode(404);
        resp.setData(0);
      }

      List<String> list = userRepo.getNotificationTokenForReceiving();
      for (String s : list) {
        getNotification(new PushNotificationRequest("WIMS MOBILE APP", "CONTAINER No. [" + ulds.getUldNumber() + "] is Ready to RECEIVE", "", s));

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
      if (resp.isStatus()) {
        for (MultipartFile f : file) {
          CargoImagesEntity images = new CargoImagesEntity();
          String filename = f.getOriginalFilename();
          images.setFilePath(fileUploadPath + filename);
          images.setFileName(filename);
          images.setCargoConditionId(cargoConditionId);
          images.setRemarks(remarks);
          ciRepo.save(images);
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
  public ApiResponseModel checkHawb(int uld_id, int hawb_id, long mawb_id) {
    ApiResponseModel resp = new ApiResponseModel();
    List<CargoActivityLogsEntity> cargo = new ArrayList<>();

    try {
      cargo = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatusAndUldId(mawb_id, hawb_id, "RECEIVED", uld_id);
      if (cargo.size() > 0) {
        resp.setMessage("Cargo Status Found");
        resp.setStatus(true);
        resp.setStatusCode(200);
      } else {
        resp.setMessage("NO DATA FOUND");
        resp.setStatus(true);
        resp.setStatusCode(400);
      }

    } catch (ErrorException e) {
      e.printStackTrace();
      resp.setMessage(e.getMessage());
      resp.setStatus(false);
      resp.setStatusCode(404);
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

//  @Override
//  public ApiResponseModel saveUldNumber(UldsEntity ulds, String[] mawbs, String uldNumber, String flightNumber, String uldType, String registry_number) {
//    ApiResponseModel resp = new ApiResponseModel();
//    TxnUldsEntity tu = new TxnUldsEntity();
//    FlightsEntity flights = new FlightsEntity();
//    UldContainerTypeEntity uc = new UldContainerTypeEntity();
//
//    List<TxnUldsEntity> tuList = new ArrayList<>();
//
//    try {
//      flights = fRepo.findByFlightNumber(flightNumber);
//      uc = ucRepo.findByType(uldType);
//      tuList = tuRepo.findByFlightNumber(flightNumber);
//
//      if (tuList.isEmpty()) {
//        tu = new TxnUldsEntity();
//      } else {
//        tu = tuList.get(0);
//      }
//
//      // SAVING OF ULD
//      if (tu != null) {
//        tu.setUldNumber(uldNumber);
//        tu.setTotalMawb(mawbs.length);
//        tu.setFlightNumber(flightNumber);
//        tu.setUldType(ulds.getUldTypeId());
//        tu = tuRepo.save(tu);
//      } else {
//        tu.setUldNumber(uldNumber);
//        tu.setTotalMawb(mawbs.length);
//        tu.setFlightNumber(flightNumber);
//        tu.setUldType(ulds.getUldTypeId());
//        tu = tuRepo.save(tu);
//      }
//
//      MawbEntity m;
//
//      for (int i = 0; i < mawbs.length; i++) {
//        String mawbNumber = mawbs[i];
//        m = mRepo.findByMawbNumberAndRegistryNumber(mawbNumber, registry_number);
//        m.setUldNumber(tu.getUldNumber());
//        mRepo.save(m);
//
//        resp.setMawbs(mawbs);
//
//        if (tu.getId() > 0) {
//          resp.setData(ulds);
//          resp.setMessage("SUCCESSFULLY ADDED ULD NO. [ " + tu.getUldNumber() + " ]");
//          resp.setStatus(true);
//          resp.setStatusCode(200);
//
//        } else {
//          resp.setMessage("NOT SUCCESSFULLY ADDED");
//          resp.setStatus(false);
//          resp.setStatusCode(404);
//        }
//      }
//
//    } catch (ErrorException e) {
//      e.printStackTrace();
//      resp.setMessage("NOT SUCCESSFULLY ADDED");
//      resp.setStatus(false);
//      resp.setStatusCode(404);
//    }
//
//    return resp;
//  }
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
  public Integer uploadImage(MultipartFile[] file, int cargoConditionId[], String cargoCondition[], String remarks[], boolean is_badOrder[], int hawbId[], String mawbNumber[], int quantity[], int uld_id, String registry_number) {
    Integer resp = 0;
    long id = 0;

    MawbEntity mawb = new MawbEntity();
    CargoActivityLogsEntity cal = new CargoActivityLogsEntity();
    List<CargoActivityLogsEntity> cargoList = new ArrayList<>();
    CargoConditionEntity condition = new CargoConditionEntity();
    ImagesEntity images1 = new ImagesEntity();

    try {
      boolean hasBadOrder = false;
      for (boolean badOrder : is_badOrder) {
        if (badOrder) {
          hasBadOrder = true;
          break;
        }
      }

      for (int i = 0; i < file.length; i++) {
        mawb = mRepo.findByMawbNumberAndRegistryNumber(mawbNumber[i], registry_number);

        if (hawbId[i] == 0) {
          if (hasBadOrder) {
            cargoList = cargoActivityRepo.getByMawbIdAndActivityStatusAndUldId(mawb.getId(), "BAD ORDER", false, false, uld_id);
          } else {
            cargoList = cargoActivityRepo.getByMawbIdAndActivityStatusAndUldId(mawb.getId(), "RECEIVED", false, false, uld_id);
          }

        } else {
          if (hasBadOrder) {
            cargoList = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatusAndUldId(mawb.getId(), hawbId[i], "BAD ORDER", uld_id);
          } else {
            cargoList = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatusAndUldId(mawb.getId(), hawbId[i], "RECEIVED", uld_id);
          }

        }

        cal = cargoList.get(cargoList.size() - 1);

        condition = cargoRepo.findByCondition(cargoCondition[i]);

        for (CargoActivityLogsEntity c : cargoList) {
          MultipartFile file1 = file[i];
          ImagesEntity images = new ImagesEntity();
          String filename = file1.getOriginalFilename();
          images.setFilePath(fileUploadPath + filename);
          images.setFileName(filename);
          images.setCargoConditionId(condition.getId());
          images.setCargoActivityLogId(c.getId());

          if (remarks.length > 0) {
            images.setRemarks(remarks[i]);
          } else {
            images.setRemarks(null);
          }

          imgRepo.save(images);
          condition = new CargoConditionEntity();
        }

      }

      resp = 1;
    } catch (Exception e) {
      resp = 0;
    }
    return resp;

  }

  @Override
  public Integer uploadVideo(MultipartFile[] file, boolean is_badOrder, int hawbId, String mawbNumber, int uld_id, String registry_number) {
    Integer resp = 0;
    long id = 0;

    MawbEntity mawb = new MawbEntity();
    CargoActivityLogsEntity cal = new CargoActivityLogsEntity();
    List<CargoActivityLogsEntity> cargoList = new ArrayList<>();
    TxnCargoVideosEntity vid = new TxnCargoVideosEntity();

    try {
      boolean hasBadOrder = is_badOrder;

      mawb = mRepo.findByMawbNumberAndRegistryNumber(mawbNumber, registry_number);

      if (hawbId == 0) {
        if (hasBadOrder) {
          cargoList = cargoActivityRepo.getByMawbIdAndActivityStatusAndUldId(mawb.getId(), "BAD ORDER", false, false, uld_id);
        } else {
          cargoList = cargoActivityRepo.getByMawbIdAndActivityStatusAndUldId(mawb.getId(), "RECEIVED", false, false, uld_id);
        }

      } else {
        if (hasBadOrder) {
          cargoList = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatusAndUldId(mawb.getId(), hawbId, "BAD ORDER", uld_id);
        } else {
          cargoList = cargoActivityRepo.getByMawbIdAndHawbIdAndActivityStatusAndUldId(mawb.getId(), hawbId, "RECEIVED", uld_id);
        }
      }

      for (MultipartFile f : file) {

        String filename = f.getOriginalFilename();
        vid.setFilePath(fileUploadPathVideo + filename);
        vid.setFileName(filename);
        vid.setCargoActivityLogId(cargoList.get(0).getId());

        vidRepo.save(vid);
        vid = new TxnCargoVideosEntity();
      }

      resp = 1;
    } catch (Exception e) {
      resp = 0;
    }
    return resp;

  }

  private List<MawbListModel> mawbMapper(List<MawbEntity> mawb) {
    List<MawbListModel> mawbs = new ArrayList<>();
    List<HawbForReceivingEntity> checkHawbIfExist = new ArrayList<>();
    for (MawbEntity m : mawb) {
      List<HawbEntity> hawbs = new ArrayList<>();
      List<HawbEntity> hawbs1 = new ArrayList<>();
      hawbs = hRepo.findByMawbNumber(m.getMawbNumber());
      MawbListModel mawbList = new MawbListModel();
      mawbList.setId(m.getId());
      mawbList.setDateOfArrival(m.getDateOfArrival());
      mawbList.setDestinationCode(m.getDestinationCode());
      mawbList.setMawbNumber(m.getMawbNumber());
//      mawbList.setNumberOfContainers(m.getNumberOfContainers());
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
      mawbList.setUldId(m.getUldId());
      mawbList.setPriority(m.getIsPriority());

      for (HawbEntity h : hawbs) {
        checkHawbIfExist = new ArrayList<>();
        checkHawbIfExist = hrRepo.findByCustomId(h.getId());
        for (HawbForReceivingEntity ch : checkHawbIfExist) {
          if (Boolean.valueOf(ch.getResult().contains("t"))) {
            hawbs1.add(h);
          }
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

  @Override
  public ApiResponseModel saveMawbHawb(List<HawbModel> hawbModel, String mawb_number, String flight_number, String uld_number, String registry_number, int quantity, int userId) {
    ApiResponseModel resp = new ApiResponseModel();
    UnmanifestedEntity entity = new UnmanifestedEntity();
    LocalDateTime date = LocalDateTime.now();

    try {

      if (hawbModel.size() > 0) {
        for (HawbModel m : hawbModel) {
          entity.setMawbNumber(mawb_number);
          entity.setHawbNumber(m.getHawb_number());
          entity.setFlightNumber(flight_number);
          entity.setUldNumber(uld_number);
          entity.setQuantity(quantity);
          entity.setRegistryNumber(registry_number);
          entity.setCreatedById(userId);
          entity.setCreatedAt(Timestamp.valueOf(date));
          unmanifestedRepo.save(entity);
          entity = new UnmanifestedEntity();
        }
      } else {
        entity.setMawbNumber(mawb_number);
        entity.setFlightNumber(flight_number);
        entity.setUldNumber(uld_number);
        entity.setQuantity(quantity);
        entity.setRegistryNumber(registry_number);
        entity.setCreatedById(userId);
        entity.setCreatedAt(Timestamp.valueOf(date));
        unmanifestedRepo.save(entity);

      }

      resp.setMessage("Successfully tag this MAWB [ " + mawb_number + " ] as UNMANIFESTED");
      resp.setStatus(true);
      resp.setStatusCode(200);

    } catch (ErrorException e) {
      e.printStackTrace();
    }

    return resp;
  }

  @Override
  public ApiResponseModel getPartialHawbs(int hawb_id, int mawb_id, String flightNumber) {
    ApiResponseModel resp = new ApiResponseModel();
    List<PartialCargoPerULDEntity> entity = new ArrayList<>();
    List<CargoActivityLogsEntity> cargoList = new ArrayList<>();
    int total_hawb = 0;
    boolean last_item = false;

    try {
      entity = partialRepo.findByHawbIdAndMawbIdAndFlightNumber(hawb_id, mawb_id, flightNumber);
      total_hawb = entity.size();

      if (total_hawb > 1) {
        last_item = false;
      } else {
        last_item = true;
      }

      if (entity.size() == 0) {
        message = "No Data to Show";
        status = false;
        statusCode = 400;
      } else {
        resp.setMessage("Data found!");
        resp.setStatus(true);
        resp.setStatusCode(200);
        resp.setPartialHawbs(entity);
        resp.setLastHawbToReceive(last_item);
      }
    } catch (ErrorException e) {
      e.printStackTrace();
    }

    return resp;
  }

  @Override
  public ApiResponseModel searchMawb(String mawb_number) {
    ApiResponseModel resp = new ApiResponseModel();
    CheckOffloadedMawbEntity offload = new CheckOffloadedMawbEntity();

    try {
      offload = offloadRepo.findByMawbNumber(mawb_number);
      if (offload != null) {
        resp.setSearchMawb(offload);
        resp.setMessage("MAWB Found");
        resp.setStatus(true);
        resp.setStatusCode(200);
      } else {
        resp.setMessage("NO MAWB FOUND");
        resp.setStatus(false);
        resp.setStatusCode(404);
      }
    } catch (ErrorException e) {
      e.printStackTrace();
      resp.setMessage(e.getMessage());
      resp.setStatus(false);
      resp.setStatusCode(404);
    }

    return resp;
  }

  @Override
  public ApiResponseModel saveEditedContainer(String container_number, String mawb_number, String flight_number, int mawb_id, String current_cont_number) {
    ApiResponseModel resp = new ApiResponseModel();
    TxnUldsEntity container = new TxnUldsEntity();
    RefULDEntity refContainer = new RefULDEntity();

    try {

      refContainer = refUldRepo.findByUldNo(container_number);

      if (refContainer == null) {
        resp.setMessage("Container Number does not exist");
        resp.setStatus(false);
        resp.setStatusCode(404);
      } else {

        container = tuRepo.findByUldNumberAndMawbId(current_cont_number, mawb_id);
        if (container.getId() > 0) {
          container.setUldNumber(container_number);
          container.setMawbNumber(mawb_number);
          container.setFlightNumber(flight_number);
          container.setMawbId(mawb_id);

          container.setUldType((long) 0);
          container.setTotalMawb(1);
          container.setUldActivityLogId((long) 0);
          container.setIsReceived(false);
          tuRepo.save(container);

          if (refContainer.getUldStatus() != 11) {
            refContainer.setUldStatus(2);
            refUldRepo.save(refContainer);
          }

          resp.setMessage("Successfully Saved");
          resp.setStatus(true);
          resp.setStatusCode(200);
        }
      }

    } catch (ErrorException e) {
      e.printStackTrace();
      resp.setMessage(e.getMessage());
      resp.setStatus(false);
      resp.setStatusCode(404);
    }

    return resp;
  }

  public void getNotification(PushNotificationRequest request) {
    pushNotifService.sendPushNotificationToToken(request);
    System.out.println("princr");
  }

}
