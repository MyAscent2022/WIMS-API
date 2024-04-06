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

  List<MawbEntity> findByFlightId(int flightId);

  List<MawbEntity> findByUldNumberAndUldStatusNot(String uldNumber, int uldStatus);

  MawbEntity findByMawbNumber(String mawbNumber);
  List<MawbEntity> findByUldNumber(String uldNumber);
}
