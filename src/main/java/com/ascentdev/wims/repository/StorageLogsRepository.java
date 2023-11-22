/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.StorageLogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface StorageLogsRepository extends JpaRepository<StorageLogsEntity, Long>{
  StorageLogsEntity findByMawbNumber(@Param("mawb_number") String mawbNumber);
  StorageLogsEntity findByHawbNumber(@Param("hawb_number") String hawbNumber);
}
