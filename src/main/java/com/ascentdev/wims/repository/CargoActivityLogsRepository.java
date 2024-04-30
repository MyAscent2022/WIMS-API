/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface CargoActivityLogsRepository extends JpaRepository<CargoActivityLogsEntity, Long> {

  @Query(
          value = "SELECT cal.* FROM cargo_activity_logs cal\n"
          + "WHERE cal.mawb_id = :mawb_id\n"
          + "AND cal.hawb_id = :hawb_id\n"
          + "AND cal.activity_status = 'STORING'",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getByMawbIdAndHawbId(@Param("mawb_id") long mawbId, @Param("hawb_id") long hawbId);
  
  @Query(
          value = "SELECT cal.* FROM cargo_activity_logs cal\n"
          + "WHERE cal.mawb_id = :mawb_id\n"
          + "AND cal.hawb_id = :hawb_id\n"
          + "AND cal.activity_status = :activity_status",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getByMawbIdAndHawbIdAndActivityStatus(@Param("mawb_id") long mawbId, @Param("hawb_id") long hawbId, @Param("activity_status") String activityStatus);
//  List<CargoActivityLogsEntity> findByMawbIdAndHawbIdAndActivityStatus(int mawbId, int hawbId, String activityStatus);
  
  List<CargoActivityLogsEntity> findByMawbIdAndHawbId(int mawbId, int hawbId);
  
  
    @Query(
          value = "SELECT cal.* FROM cargo_activity_logs cal\n"
          + "WHERE cal.mawb_id = :mawb_id\n"
          + "AND cal.activity_status = :activity_status",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getByMawbIdAndActivityStatus(@Param("mawb_id") long mawbId, @Param("activity_status") String activityStatus);
  
  
      @Query(
          value = "SELECT cal.* FROM cargo_activity_logs cal\n"
          + "WHERE cal.hawb_id = :hawb_id\n"
          + "AND cal.activity_status = :activity_status",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getByHawbIdAndActivityStatus(@Param("hawb_id") long hawbId, @Param("activity_status") String activityStatus);
  
  
  
//  List<CargoActivityLogsEntity> findByMawbIdAndActivityStatus(int mawbId, String activityStatus);

  CargoActivityLogsEntity findByMawbIdAndLocationAndReceivedReleasedDateNull(int mawbId, String location);

  CargoActivityLogsEntity findByHawbIdAndLocationAndReceivedReleasedDateNull(int hawb_id, String location);

  @Query(
          value = "SELECT tci.* FROM cargo_activity_logs cal\n"
          + "INNER JOIN txn_cargo_images tci ON tci.cargo_activity_log_id = cal.id\n"
          + "WHERE cal.flight_id = :flight_id \n"
          + "AND cal.mawb_id = :mawb_id\n"
          + "AND cal.hawb_id = :hawb_id",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getCargoImages(@Param("flight_id") long flight_id, @Param("mawb_id") int mawb_id, @Param("hawb_id") int hawb_id);
  
  CargoActivityLogsEntity findById(long id);

}
