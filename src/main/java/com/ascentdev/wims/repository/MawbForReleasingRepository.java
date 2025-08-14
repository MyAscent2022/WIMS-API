/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.MawbForReleasingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT SOLUTIONS INC
 */
public interface MawbForReleasingRepository extends JpaRepository<MawbForReleasingEntity, Integer> {

  MawbForReleasingEntity findByMawbNumber(String mawbNumber);

//  MawbForReleasingEntity findById(int id);
  MawbForReleasingEntity findByIdAndYellowReceipt(int id, String yellow_receipt);
  MawbForReleasingEntity findByYellowReceipt(String yellow_receipt);

  @Query(
          value = "SELECT tm.id, \n"
          + "tm.date_of_arrival, \n"
          + "tm.destination_code, \n"
          + "tm.mawb_number,\n"
          + "string_agg(tu.mawb_number, ', ') AS mawb1,\n"
          + "tm.number_of_packages,\n"
          + "tm.origin_code,\n"
          + "tm.registry_number,\n"
          + "tm.time_of_arrival,\n"
          + "tm.volume,\n"
          + "string_agg(tu.uld_number, ', ') AS uld_number,\n"
          + "string_agg(ru.uld_no, ', ') AS uld_no,\n"
          + "tm.uld_container_type_id,\n"
          + "tm.cargo_status,\n"
          + "tm.length,\n"
          + "tm.width,\n"
          + "tm.height,\n"
          + "tm.actual_weight,\n"
          + "tm.actual_volume,\n"
          + "tm.actual_pcs,\n"
          + "tm.cargo_category_id,\n"
          + "tm.cargo_class_id,\n"
          + "tm.flight_id,\n"
          + "tm.consignee_name,\n"
          + "string_agg(CAST(ru.uld_status AS text), ', ') AS uld_status,\n"
          + "rcc.classdesc AS cargo_class, \n"
          + "tm.yellow_receipt_no\n"
          + "FROM txn_mawb tm\n"
          + "INNER JOIN ref_cargo_class rcc ON rcc.id = tm.cargo_class_id\n"
          + "INNER JOIN txn_ulds tu ON tu.mawb_number = tm.mawb_number \n"
          + "INNER JOIN ref_uld ru ON ru.uld_no = tu.uld_number\n"
          + "WHERE tm.cargo_class_id = 11\n"
          + "AND tm.id = :mawb_id\n"
          + "GROUP BY\n"
          + "tm.id, \n"
          + "tm.date_of_arrival, \n"
          + "tm.destination_code, \n"
          + "tm.mawb_number,\n"
          + "tm.number_of_containers,\n"
          + "tm.number_of_packages,\n"
          + "tm.origin_code,\n"
          + "tm.registry_number,\n"
          + "tm.time_of_arrival,\n"
          + "tm.volume,tm.uld_container_type_id,\n"
          + "tm.cargo_status,\n"
          + "tm.length,\n"
          + "tm.width,\n"
          + "tm.height,\n"
          + "tm.actual_weight,\n"
          + "tm.actual_volume,\n"
          + "tm.actual_pcs,\n"
          + "tm.cargo_category_id,\n"
          + "tm.cargo_class_id,\n"
          + "tm.flight_id,\n"
          + "tm.consignee_name,\n"
          + "rcc.classdesc,\n"
          + "tm.yellow_receipt_no",
          nativeQuery = true)
  MawbForReleasingEntity getTransitMawbForReleasing(@Param("mawb_id") int id);

}
