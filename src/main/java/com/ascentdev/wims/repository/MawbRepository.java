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
public interface MawbRepository extends JpaRepository<MawbEntity, Integer> {

//  List<MawbEntity> findByFlightNumber(@Param("flight_number") String flightNumber);

  List<MawbEntity> findByUldNumber(@Param("uld_number") String uldNumber);

  MawbEntity findByMawbNumber(@Param("mawb_number") String mawbNumber);
}
