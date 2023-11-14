/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.CargoManifestDetailsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface CargoManifestDetailsRepository extends JpaRepository<CargoManifestDetailsEntity, Long> {

  @Query(
          value = "SELECT cmd.* FROM manifest.txn_cargo_manifest cm\n"
          + "INNER JOIN manifest.txn_cargo_manifest_details cmd ON cm.id = cmd.txn_cargo_manifest_id\n"
          + "WHERE cm.mawb_number = :mawb_number",
          nativeQuery = true)
  List<CargoManifestDetailsEntity> getMawbs(@Param("mawb_number") String mawbNumber);
}
