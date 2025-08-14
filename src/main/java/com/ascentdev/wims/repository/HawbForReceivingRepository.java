/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.HawbForReceivingEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT SOLUTIONS INC
 */
public interface HawbForReceivingRepository extends JpaRepository<HawbForReceivingEntity, Integer> {
//  List<HawbForReceivingEntity> findByIdAndUldId(int id, int uld_id);

  @Query(value = "SELECT DISTINCT th.id, "
          + "CASE WHEN cal.hawb_id IS NULL OR cal.is_full IS FALSE THEN true ELSE false END AS result "
          + "FROM txn_hawb th "
          + "LEFT JOIN cargo_activity_logs cal ON th.id = cal.hawb_id "
          + "INNER JOIN txn_mawb tm ON tm.mawb_number = th.mawb_number "
          + "WHERE th.id = :id "
          + "ORDER BY th.id DESC",
          nativeQuery = true)
  List<HawbForReceivingEntity> findByCustomId(@Param("id") int id);
}
