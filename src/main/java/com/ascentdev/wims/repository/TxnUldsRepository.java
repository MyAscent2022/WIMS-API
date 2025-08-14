/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.TxnUldsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface TxnUldsRepository extends JpaRepository<TxnUldsEntity, Integer>{
  List<TxnUldsEntity> findByFlightNumber (String flightNumber);
  List<TxnUldsEntity> findByUldNumber(String uldNumber);
  TxnUldsEntity findByUldNumberAndMawbId(String uldNumber, int mawb_id);
//  TxnUldsEntity findById(int id);
  
//    @Query(
//          value = "SELECT * FROM public.txn_ulds \n"
//          + "WHERE id = :id",
//          nativeQuery = true)
//  TxnUldsEntity getUld(@Param("id") int id);
  
}
