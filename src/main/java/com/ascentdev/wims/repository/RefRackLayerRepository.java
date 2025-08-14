/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.RefRackLayerEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author
 * ASCENT
 */
public interface RefRackLayerRepository extends JpaRepository<RefRackLayerEntity, Long> {

  RefRackLayerEntity findByLayerNameAndRackName(String layerName, String rackName);

  List<RefRackLayerEntity> findByRackName(String rackName);

  List<RefRackLayerEntity> findById(@Param("id") long id);

}
