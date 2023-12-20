/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.Acceptance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface AcceptanceRepository extends JpaRepository<Acceptance, Long> {

  @Query(
          value = "SELECT a.*, m.flight_number, m.mawb_number, h.hawb_number, cc.classdesc, h.number_of_packages, a.actual_pcs FROM public.txn_acceptance a\n"
          + "INNER JOIN manifest.txn_mawb m ON m.id = a.txn_mawb_id\n"
          + "INNER JOIN manifest.txn_hawb h ON h.id = a.txn_hawb_id\n"
          + "INNER JOIN refs.ref_cargo_class cc ON cc.id = a.cargo_class\n"
          + "WHERE a.cargo_status = 3",
          nativeQuery = true)
  List<Acceptance> getStorageDetails();
  
  Acceptance findByTxnHawbId(@Param("txn_hawb_id") int txnHawbId);

}
