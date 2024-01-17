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
          + "AND cal.location = 'STORING'",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getByMawbIdAndHawbId(@Param("mawb_id") long mawbId, @Param("hawb_id") long hawbId);

  List<CargoActivityLogsEntity> findByMawbId(long mawbId);

  CargoActivityLogsEntity findByMawbIdAndLocationAndReceivedReleasedDateNull(long mawbId, String location);

  CargoActivityLogsEntity findByHawbIdAndLocationAndReceivedReleasedDateNull(int hawb_id, String location);

  @Query(
          value = "SELECT tci.* FROM cargo_activity_logs cal\n"
          + "INNER JOIN txn_cargo_images tci ON tci.cargo_activity_log_id = cal.id\n"
          + "WHERE cal.flight_id = :flight_id \n"
          + "AND cal.mawb_id = :mawb_id\n"
          + "AND cal.hawb_id = :hawb_id",
          nativeQuery = true)
  List<CargoActivityLogsEntity> getCargoImages(@Param("flight_id") long flight_id, @Param("mawb_id") int mawb_id, @Param("hawb_id") int hawb_id);

}
