/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.TxnUldsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ASCENT
 */
public interface TxnUldsRepository extends JpaRepository<TxnUldsEntity, Long>{
  
}
