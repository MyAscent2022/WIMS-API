/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.CargoManifestEntity;
import com.ascentdev.wims.entity.FlightsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface CargoManifestRepository extends JpaRepository<CargoManifestEntity, Long>{
  @Query(
          value = "WITH rellogs as (SELECT unnest (string_to_array(user_id, ',')) as rel_handler, * FROM public.txn_receiving_logs)\n"
          + "SELECT cm.* FROM rellogs rel\n"
          + "INNER JOIN manifest.txn_cargo_manifest cm ON rel.flight_number = cm.flight_number\n"
          + "where :user_id IN(rel.rel_handler)",
          nativeQuery = true)
  List<CargoManifestEntity> getFlightsByUserId(@Param("user_id") String user_id);
}
