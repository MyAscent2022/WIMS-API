/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.UserLogsEntity;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface UserLogsRepository extends JpaRepository<UserLogsEntity, Long> {

  UserLogsEntity findByUserIdAndLogOutAt(long userId, Timestamp logOutAt);
//  UserLogsEntity findByUserIdAndLogOutAtAndIsMobile(long userId, Timestamp logOutAt, boolean isMobile);

  List<UserLogsEntity> findByUserIdAndLogOutAtAndIsMobile(long userId, Timestamp logOutAt, boolean isMobile);
  
  @Query(
          value = "SELECT * FROM user_activity_logs\n"
          + "WHERE user_id = :user_id\n"
          + "AND log_out_at = NULL\n"
          + "AND is_active = true\n"
          + "ORDER BY id DESC\n"
          + "LIMIT 1",
          nativeQuery = true)
  List<UserLogsEntity> userLogout(@Param("user_id") long userId);
} 
