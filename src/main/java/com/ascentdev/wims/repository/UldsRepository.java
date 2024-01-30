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

  UldsEntity findByUldNumberAndFlightNumber(String uldNumber, String flightNumber);
  UldsEntity findByUldNumber (String uldNumber);
  List<UldsEntity> findByFlightNumber(String flightNumber);
}
