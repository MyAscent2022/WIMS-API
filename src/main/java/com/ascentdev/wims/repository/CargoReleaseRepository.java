/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.CargoReleaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author
 * ASCENT
 */
public interface CargoReleaseRepository extends JpaRepository<CargoReleaseEntity, Long> {

  CargoReleaseEntity findByMawbNumber(@Param("mawb_number") String mawbNumber);

  CargoReleaseEntity findByHawbNumber(@Param("hawb_number") String hawbNumber);
}
