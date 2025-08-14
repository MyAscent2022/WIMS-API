/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ASCENT SOLUTIONS INC
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findByUsername(String username);

  @Query(
          value = "SELECT notification_token FROM public.users WHERE role_id = 3 AND NOT notification_token IS NULL",
          nativeQuery = true)
  List<String> getNotificationTokenForReceiving();

  @Query(
          value = "SELECT notification_token FROM public.users WHERE role_id = 4 AND NOT notification_token IS NULL",
          nativeQuery = true)
  List<String> getNotificationTokenForStoring();

}
