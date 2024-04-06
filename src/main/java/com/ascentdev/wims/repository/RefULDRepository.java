/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.RefULDEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author
 * ASCENT
 */
public interface RefULDRepository extends JpaRepository<RefULDEntity, Long>{
  
  List<RefULDEntity> findByFlightNumber(String flightNumber);
  RefULDEntity findByUldNo(String uld_no);
  
}
