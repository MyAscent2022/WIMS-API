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
public interface HawbRepository extends JpaRepository<HawbEntity, Long> {
  
  List<HawbEntity> findByMawbNumber(@Param("mawb_number") String mawbNumber);
  
  HawbEntity findByHawbNumber(@Param("hawb_number") String hawbNumber);
}
