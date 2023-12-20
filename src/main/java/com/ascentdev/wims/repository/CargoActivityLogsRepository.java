/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author
 * ASCENT
 */
public interface CargoActivityLogsRepository extends JpaRepository<CargoActivityLogsEntity, Integer>{
  
  CargoActivityLogsEntity findByMawbIdAndHawbId(@Param("mawb_id") int mawbId, @Param("hawb_id") int hawb_id);
  CargoActivityLogsEntity findByMawbIdAndLocationAndReleasedDatetimeNull(@Param("mawb_id") int mawbId, @Param("location") String location);
  CargoActivityLogsEntity findByHawbIdAndLocationAndReleasedDatetimeNull(@Param("hawb_id") int hawb_id, @Param("location") String location);
  
}
