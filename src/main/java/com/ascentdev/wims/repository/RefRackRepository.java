package com.ascentdev.wims.repository;
import com.ascentdev.wims.entity.RefRackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author ASCENT SOLUTIONS INC
 */
public interface RefRackRepository extends JpaRepository<RefRackEntity, Long>{
  
  RefRackEntity findByRackCode(String rack_code);
  
}
