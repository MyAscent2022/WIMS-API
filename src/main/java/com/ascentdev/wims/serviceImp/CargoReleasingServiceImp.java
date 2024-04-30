/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.controller.CargoReleasingController;
import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.CargoReleasingEntity;
import com.ascentdev.wims.entity.CargoReleasingImagesEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.ImagesEntity;
import com.ascentdev.wims.entity.MawbEntity;
import com.ascentdev.wims.entity.MawbForReleasingEntity;
import com.ascentdev.wims.entity.RackUtilEntity;
import com.ascentdev.wims.entity.RackUtilHistoryEntity;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.model.ApprovedReleasingModel;
import com.ascentdev.wims.model.CargoReleaseRequestModel;
import com.ascentdev.wims.model.ForCargoReleasingModel;
import com.ascentdev.wims.repository.ApprovedReleasingDao;
import com.ascentdev.wims.repository.CargoActivityLogsRepository;
import com.ascentdev.wims.repository.CargoReleasingImagesRepository;
import com.ascentdev.wims.repository.CargoReleasingRepository;
import com.ascentdev.wims.repository.HawbRepository;
import com.ascentdev.wims.repository.ImagesRepository;
import com.ascentdev.wims.repository.MawbForReleasingRepository;
import com.ascentdev.wims.repository.MawbRepository;
import com.ascentdev.wims.repository.RackUtilHistoryRepository;
import com.ascentdev.wims.repository.RackUtilRepository;
import com.ascentdev.wims.service.CargoReleasingService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author User
 */
@Service
public class CargoReleasingServiceImp implements CargoReleasingService {

  @Autowired
  HawbRepository hRepo;

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
  CargoActivityLogsRepository caRepo;

  @Autowired
  CargoActivityLogsRepository cargoActivityRepo;

  @Autowired
  ImagesRepository imgRepo;

  String filepath = "C:\\wms_paircargo\\SUPPORTING_DOCUMENTS\\images\\";

  @Override
  public ApiResponseModel getCargoReleasingList() {
    ApiResponseModel res = new ApiResponseModel();
    Connection conn = aRepo.connect();

    List<ApprovedReleasingModel> approvedList = new ArrayList<>();
    List<ForCargoReleasingModel> list = new ArrayList<>();
    ForCargoReleasingModel item = new ForCargoReleasingModel();
    MawbForReleasingEntity mawb = new MawbForReleasingEntity();
    HawbEntity hawb = new HawbEntity();

    String message = "";
    int statusCode = 0;
    boolean isStatus = false;
    try {
      approvedList = aRepo.getForReleasing(conn);
      conn.close();
    } catch (SQLException ex) {
      Logger.getLogger(CargoReleasingServiceImp.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (approvedList.size() > 0) {

      for (ApprovedReleasingModel m : approvedList) {
        item = new ForCargoReleasingModel();
        mawb = mRepo.findByMawbNumber(m.getMawb());
        if (mawb == null) {
          message = "No Data Found";
          statusCode = 404;
          isStatus = false;
        } else {
          if (m.getHawb() == null) {
            mawb = new MawbForReleasingEntity();

            item.setId(mawb.getId());
            item.setAwb(m.getMawb());
            item.setConsignee(mawb.getConsigneeName());
            item.setUom("PCS");
            item.setQty(mawb.getNumberOfPackages());
            item.setShawb(false);
          } else {
            hawb = hRepo.findByHawbNumber(m.getHawb());

            if (hawb == null) {
            } else {
              item.setId(hawb.getId());
              item.setAwb(m.getHawb());
              item.setConsignee(mawb.getConsigneeName());
              item.setQty(hawb.getNumberOfPackages());
              item.setUom("PCS");
              item.setShawb(true);
            }

          }

          message = "Found";
          statusCode = 200;
          isStatus = true;
          list.add(item);
        }

      }
    }

    res.setData(list);
    res.setStatusCode(statusCode);
    res.setStatus(isStatus);
    res.setMessage(message);

    return res;

  }

  @Override
  public ApiResponseModel saveCargoReleasing(CargoReleaseRequestModel model) {

    Connection conn = aRepo.connect();
    ApiResponseModel res = new ApiResponseModel();
    String[] filenames = model.getFilenames().split(",");
    MawbForReleasingEntity mawb = new MawbForReleasingEntity();
    MawbEntity mawb1 = new MawbEntity();
    HawbEntity hawb = new HawbEntity();
    List<CargoActivityLogsEntity> cargoLogs = new ArrayList<>();
    CargoActivityLogsEntity log = new CargoActivityLogsEntity();
    String stHawb = null;
    String stMawb = null;
    int mawb_id = 0;
    int hawb_id = 0;
    CargoReleasingEntity cargoEntity = new CargoReleasingEntity();
    cargoEntity.setAwb_id(model.getAwb_id());
    cargoEntity.setPlateNo(model.getPlateNo());
    cargoEntity.setTrucker(model.getTrucker());
    cargoEntity.setHawb(model.isShawb());
    cargoEntity.setCreatedBy(model.getUser_id());

    cargoEntity = cRepo.save(cargoEntity);

    ImagesEntity images = new ImagesEntity();

    if (model.isShawb()) {
      cargoLogs = cargoActivityRepo.getByHawbIdAndActivityStatus(model.getAwb_id(), "STORED");
    } else {
      cargoLogs = cargoActivityRepo.getByMawbIdAndActivityStatus(model.getAwb_id(), "STORED");
    }

    log = cargoLogs.get(cargoLogs.size() - 1);

    if (cargoEntity == null) {
      res.setData(0);
      res.setStatus(false);
      res.setMessage("Not saved");
      res.setStatusCode(400);
    } else {
      for (String s : filenames) {
        images = new ImagesEntity();
        images.setFileName(s);
        images.setFilePath(filepath + "\\" + s);
        images.setCargoActivityLogId(log.getId());

        imgRepo.save(images);
      }

      RackUtilEntity rEntity = new RackUtilEntity();
      RackUtilHistoryEntity hisEntity = new RackUtilHistoryEntity();
      rEntity = model.isShawb() ? rRepo.findByTxnHawbId(model.getAwb_id()) : rRepo.findByTxnMawbId(model.getAwb_id());

      hisEntity.setCreatedAt(rEntity.getCreatedAt());
      hisEntity.setCreatedById(rEntity.getCreatedById());
      hisEntity.setLocation(rEntity.getLocation());
      hisEntity.setModifiedAt(rEntity.getModifiedAt());
      hisEntity.setModifiedById(rEntity.getModifiedById());
      hisEntity.setNoOfPieces(rEntity.getNoOfPieces());
      hisEntity.setRefRackId(rEntity.getRefRackId());
      hisEntity.setStoredById(rEntity.getStoredById());
      hisEntity.setStoredDt(rEntity.getStoredDt());
      hisEntity.setTxnHawbId(rEntity.getTxnHawbId());
      hisEntity.setTxnMawbId(rEntity.getTxnMawbId());
      hisEntity.setTxnRackUtilizationId(rEntity.getId());
      hisEntity.setVolume(rEntity.getVolume());

      hiRepo.save(hisEntity);
      rRepo.delete(rEntity);

      if (model.isShawb()) {

        hawb = hRepo.findById(model.getAwb_id()).get();
        stHawb = hawb.getHawbNumber();
        stMawb = hawb.getMawbNumber();
        mawb1 = m1Repo.findByMawbNumber(stMawb);

        hawb_id = hawb.getId();
        mawb_id = mawb1.getId();

      } else {
        mawb1 = m1Repo.findById(model.getAwb_id()).get();
        stMawb = mawb1.getMawbNumber();
        mawb_id = mawb1.getId();
      }

      List<CargoActivityLogsEntity> cargoLogsList = new ArrayList<>();
      cargoLogsList = caRepo.getByMawbIdAndHawbIdAndActivityStatus(mawb_id, hawb_id, "STORED");

      for (CargoActivityLogsEntity cal : cargoLogsList) {
        LocalDateTime date = LocalDateTime.now();
        CargoActivityLogsEntity m = new CargoActivityLogsEntity();
        m.setActivityStatus("RELEASED");
        m.setActualPcs(cal.getActualPcs());
        m.setCreatedAt(cal.getCreatedAt());
        m.setCreatedById(cal.getCreatedById());
        m.setFlightId(cal.getFlightId());
        m.setHandledById(cal.getHandledById());
        m.setHawbId(cal.getHawbId());
        m.setMawbId(cal.getMawbId());
        m.setReceivedReleasedDate(Timestamp.valueOf(date));
        m.setLocation("RELEASING AREA");
        m.setUpdatedAt(Timestamp.valueOf(date));
        m.setUpdatedById(model.getUser_id());

        caRepo.save(m);
      }

      try {
        aRepo.updateCargoStatusLogs(conn, stMawb, stHawb);
        conn.close();
      } catch (SQLException ex) {
        Logger.getLogger(CargoReleasingServiceImp.class.getName()).log(Level.SEVERE, null, ex);
      }

      res.setData(1);
      res.setStatus(true);
      res.setStatusCode(200);
      res.setMessage("saved");
    }

    return res;
  }

  @Override
  public ApiResponseModel saveCargoReleasingImage(MultipartFile[] files) {

    ApiResponseModel res = new ApiResponseModel();

    for (MultipartFile f : files) {
      saveImage(f);
    }
    res.setData(1);
    res.setStatus(true);
    res.setStatusCode(200);
    res.setMessage("saved");

    return res;
  }

  private void saveImage(MultipartFile file) {

    try {
      byte[] data = file.getBytes();
      Path path = Paths.get(filepath + "\\" + file.getOriginalFilename());
      Files.write(path, data);
    } catch (IOException ex) {
      Logger.getLogger(CargoReleasingController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}
