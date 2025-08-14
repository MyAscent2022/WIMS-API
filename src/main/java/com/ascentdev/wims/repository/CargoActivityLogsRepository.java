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
          + "AND cal.activity_status = :activity_status\n"
          + "AND cal.is_released = false",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getByMawbIdAndHawbIdAndActivityStatus(@Param("mawb_id") long mawbId, @Param("hawb_id") long hawbId, @Param("activity_status") String activityStatus);

  @Query(
          value = "SELECT cal.* FROM cargo_activity_logs cal\n"
          + "WHERE cal.mawb_id = :mawb_id\n"
          + "AND cal.hawb_id = :hawb_id\n"
          + "AND cal.activity_status = :activity_status\n"
          + "AND cal.uld_id = :uld_id\n"
          + "AND cal.is_released = false",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getByMawbIdAndHawbIdAndActivityStatusAndUldId(@Param("mawb_id") long mawbId, @Param("hawb_id") long hawbId, @Param("activity_status") String activityStatus, @Param("uld_id") int uld_id);
//  List<CargoActivityLogsEntity> findByMawbIdAndHawbIdAndActivityStatus(int mawbId, int hawbId, String activityStatus);

  List<CargoActivityLogsEntity> findByMawbIdAndHawbId(int mawbId, int hawbId);

  @Query(
          value = "SELECT cal.* FROM cargo_activity_logs cal\n"
          + "INNER JOIN txn_mawb tm ON tm.id = cal.mawb_id\n"
          + "WHERE cal.mawb_id = :mawb_id\n"
          + "AND cal.activity_status = :activity_status \n"
          + "AND cal.is_released = :is_released\n"
          + "AND cal.is_pull_out = :is_pull_out\n"
          + "LIMIT 1;",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getByMawbIdAndActivityStatus(@Param("mawb_id") long mawbId, @Param("activity_status") String activityStatus, @Param("is_released") boolean isReleased, @Param("is_pull_out") boolean isPullOut);

  @Query(
          value = "SELECT cal.* FROM cargo_activity_logs cal\n"
          + "WHERE cal.mawb_id = :mawb_id\n"
          + "AND cal.activity_status = :activity_status \n"
          + "AND cal.is_released = :is_released\n"
          + "AND cal.is_pull_out = :is_pull_out\n"
          + "AND cal.uld_id = :uld_id",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getByMawbIdAndActivityStatusAndUldId(@Param("mawb_id") long mawbId, @Param("activity_status") String activityStatus, @Param("is_released") boolean isReleased, @Param("is_pull_out") boolean isPullOut, @Param("uld_id") int uld_id);

  @Query(
          value = "SELECT cal.* FROM cargo_activity_logs cal\n"
          + "INNER JOIN txn_hawb th ON th.id = cal.hawb_id\n"
          + "WHERE cal.hawb_id = :hawb_id\n"
          + "AND cal.activity_status = :activity_status\n"
          + "AND cal.is_released = :is_released\n"
          + "AND cal.is_pull_out = :is_pull_out\n"
          + "LIMIT 1;",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getByHawbIdAndActivityStatus(@Param("hawb_id") long hawbId, @Param("activity_status") String activityStatus, @Param("is_released") boolean isReleased, @Param("is_pull_out") boolean isPullOut);

  @Query(
          value = "SELECT cal.* FROM cargo_activity_logs cal\n"
          + "WHERE cal.hawb_id = :hawb_id\n"
          + "AND cal.activity_status = :activity_status\n"
          + "AND cal.is_released = :is_released\n"
          + "AND cal.is_pull_out = :is_pull_out\n"
          + "AND cal.uld_id = :uld_id\n"
          + "LIMIT 1;",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getByHawbIdAndActivityStatusAndUldId(@Param("hawb_id") long hawbId, @Param("activity_status") String activityStatus, @Param("is_released") boolean isReleased, @Param("is_pull_out") boolean isPullOut, @Param("uld_id") int uld_id);

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

  @Query(
          value = "SELECT cal.*\n"
          + "FROM cargo_activity_logs cal\n"
          + "INNER JOIN txn_mawb tm ON tm.id = cal.mawb_id\n"
          + "LEFT JOIN txn_hawb th ON th.id = cal.hawb_id\n"
          + "WHERE cal.activity_status = 'RECEIVED'\n"
          + "AND (tm.cargo_class_id IN (11, 10, 9) OR th.cargo_class_id IN (11, 10, 9))\n"
          + "AND cal.is_released = false\n"
          + "AND cal.is_pull_out = false\n",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getTransitForPullOut();

  @Query(
          value = "SELECT cal.* FROM cargo_activity_logs cal\n"
          + "INNER JOIN txn_mawb tm ON tm.id = cal.mawb_id\n"
          + "LEFT JOIN txn_hawb th ON th.id = cal.hawb_id\n"
          + "WHERE cal.activity_status = 'PULL OUT'\n"
          + "AND (tm.cargo_class_id IN (11, 10, 9) OR th.cargo_class_id IN (11, 10, 9))\n"
          + "AND cal.is_released = false\n"
          + "AND cal.is_pull_out = true\n",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getTransitForReleasing();

}
