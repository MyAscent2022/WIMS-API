/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.ImagesEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface ImagesRepository extends JpaRepository<ImagesEntity, Long> {

  @Query(
          value = "SELECT * FROM manifest.txn_images\n"
          + "WHERE file_type = 2 AND mawb_id = :mawb_id",
          nativeQuery = true)
  List<ImagesEntity> getImages(@Param("mawb_id") Long mawbId);
}
