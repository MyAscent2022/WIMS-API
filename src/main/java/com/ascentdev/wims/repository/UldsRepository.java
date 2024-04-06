/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.UldsEntity;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ASCENT
 */
@Repository
public interface UldsRepository extends JpaRepository<UldsEntity, Long> {

  UldsEntity findByUldNumberAndFlightNumber(String uldNumber, String flightNumber);

//  UldsEntity findByUldNumberAndMawbNumber(String uldNumber, String mawbNumber);

  UldsEntity findByUldNumber(String uldNumber);

  List<UldsEntity> findByFlightNumber(String flightNumber);

//  @Modifying
//  @Transactional
//  @Query(
//          value = "UPDATE txn_ulds\n"
//          + "SET uld_status = 10\n"
//          + "WHERE id = :uld_id",
//          nativeQuery = true)
//  void updateById(@Param("uld_id") int uldId);

}
