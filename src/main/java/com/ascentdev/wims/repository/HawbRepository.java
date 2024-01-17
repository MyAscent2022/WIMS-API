/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.HawbEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface HawbRepository extends JpaRepository<HawbEntity, Integer> {
  
  List<HawbEntity> findByMawbNumberAndHawbNumber(String mawbNumber, String hawbNumber);
  List<HawbEntity> findByMawbNumber(String mawbNumber);
  HawbEntity findByHawbNumber(String hawbNumber);
}
