/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.UldsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ASCENT
 */
@Repository
public interface UldsRepository extends JpaRepository<UldsEntity, Long> {

  List<UldsEntity> findByUldNo(String uldNo);

  @Query(
          value = "SELECT u.* FROM manifest.txn_cargo_manifest cm\n"
          + "INNER JOIN public.txn_ulds u ON cm.uld_no = u.uld_no\n"
          + "WHERE cm.flight_number = :flight_number",
          nativeQuery = true)
  List<UldsEntity> getUlds(@Param("flight_number") String flightNumber);
}
