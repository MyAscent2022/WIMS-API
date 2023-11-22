/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.StorageCargoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface StorageCargoRepository extends JpaRepository<StorageCargoEntity, Integer>{
  List<StorageCargoEntity> findByCargoStatus(@Param("cargo_status") int cargoStatus);
}
