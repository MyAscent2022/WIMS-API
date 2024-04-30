/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.RackUtilEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface RackUtilRepository extends JpaRepository<RackUtilEntity, Long> {

  RackUtilEntity findById(long id);
  List<RackUtilEntity> findByTxnMawbIdAndTxnHawbId(long txnMawbId, long txnHawbId);
  
  RackUtilEntity findByTxnHawbId(int txtHawbId);
  RackUtilEntity findByTxnMawbId(int txnMawbId);
  
}
