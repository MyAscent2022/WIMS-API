/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.FlightsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ASCENT
 */
public interface FlightsRepository extends JpaRepository<FlightsEntity, Long> {

  List<FlightsEntity> findByIdIn(long[] ids);

  @Query(
          value = "SELECT rl.* FROM public.txn_flight_status fs\n"
          + "INNER JOIN public.txn_receiving_logs rl ON fs.flight_number = rl.flight_number\n"
          + "WHERE fs.flight_status = 'Landed'",
          nativeQuery = true)
  List<FlightsEntity> getFlights();
}
