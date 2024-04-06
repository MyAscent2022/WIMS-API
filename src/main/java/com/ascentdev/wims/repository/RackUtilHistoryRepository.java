/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.RackUtilEntity;
import com.ascentdev.wims.entity.RackUtilHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface RackUtilHistoryRepository extends JpaRepository<RackUtilHistoryEntity, Long>{
  
  
  
}
