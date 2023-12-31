/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.UserLogsEntity;
import java.sql.Timestamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface UserLogsRepository extends JpaRepository<UserLogsEntity, Long>{
  UserLogsEntity findByUserIdAndLogOutAt(@Param("user_id") long userId, @Param("log_out_at") Timestamp logOutAt);
}
