/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.MawbEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ASCENT
 */
public interface MawbRepository extends JpaRepository<MawbEntity, Long> {

  @Query(
          value = "SELECT * FROM manifest.txn_cargo_manifest cm\n"
          + "INNER JOIN manifest.txn_mawb m ON cm.mawb_number = m.mawb_number\n"
          + "WHERE cm.uld_no = :uld_no",
          nativeQuery = true)
  List<MawbEntity> getMawbs(@Param("uld_no") String uld_no);

  @Query(
          value = "SELECT m.* FROM public.txn_receiving_logs rl\n"
          + "INNER JOIN manifest.txn_mawb m ON rl.mawb_number = m.mawb_number\n"
          + "WHERE rl.registry_number = :registry_number AND rl.mawb_number = :mawb_number",
          nativeQuery = true)
  List<MawbEntity> getHawbs(@Param("registry_number") String registryNumber, @Param("mawb_number") String mawbNumber);

  @Query(
          value = "SELECT m.* FROM public.txn_receiving_logs rl\n"
          + "INNER JOIN manifest.txn_cargo_manifest m ON rl.inbound_status = m.inbound_status\n"
          + "WHERE rl.inbound_status = 'For Storage'",
          nativeQuery = true)
  List<MawbEntity> getStoreCargo();
}
