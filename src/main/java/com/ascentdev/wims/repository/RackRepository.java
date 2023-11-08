/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.RackEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ASCENT
 */
public interface RackRepository extends JpaRepository<RackEntity, Long> {

  @Query(
          value = "SELECT * FROM public.txn_rack_utilization ru\n"
          + "WHERE is_paid = true",
          nativeQuery = true)
  List<RackEntity> getRacks();
}
