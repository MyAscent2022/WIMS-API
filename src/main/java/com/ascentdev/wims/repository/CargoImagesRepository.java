/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.CargoImagesEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface CargoImagesRepository extends JpaRepository<CargoImagesEntity, Long> {
//  List<CargoImagesEntity> findByMawbNumber(@Param("mawb_number") String mawbNumber);
//  List<CargoImagesEntity> findByHawbNumber(@Param("hawb_number") String hawbNumber);
  List<CargoImagesEntity> findByCargoActivityLogId(long cargoActivityLogId);

  @Query(
          value = "SELECT cal.* FROM public.txn_cargo_images tci\n"
          + "INNER JOIN public.cargo_activity_log_old cal ON cal.id = tci.cargo_activity_log_id\n"
          + "WHERE cal.hawb_id = :hawb_id",
          nativeQuery = true)
  List<CargoImagesEntity> searchHawbId(@Param("hawb_id") int hawb_id);

  @Query(
          value = "SELECT cal.* FROM public.txn_cargo_images tci\n"
          + "INNER JOIN public.cargo_activity_log_old cal ON cal.id = tci.cargo_activity_log_id\n"
          + "WHERE cal.mawb_id = :mawb_id",
          nativeQuery = true)
  List<CargoImagesEntity> searchMawbId(@Param("mawb_id") long mawb_id);

  @Query(
          value = "SELECT tci.* FROM cargo_activity_log_old cal\n"
          + "INNER JOIN txn_cargo_images tci ON tci.cargo_activity_log_id = cal.id\n"
          + "WHERE cal.flight_id = :flight_id \n"
          + "AND cal.mawb_id = :mawb_id\n"
          + "AND cal.hawb_id = :hawb_id",
          nativeQuery = true)
  List<CargoImagesEntity> getCargoImages(@Param("flight_id") long flight_id, @Param("mawb_id") int mawb_id, @Param("hawb_id") int hawb_id);

}
