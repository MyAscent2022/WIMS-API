/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.RackDetailsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface RackDetailsRepository extends JpaRepository<RackDetailsEntity, Integer>{
  RackDetailsEntity findByMawbNumber(@Param("mawb_number") String mawb_number);
  RackDetailsEntity findByHawbNumber(@Param("hawb_number") String hawb_number);
}
