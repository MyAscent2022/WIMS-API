/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.entity.JobAssignmentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ASCENT
 */
public interface JobAssignmentRepository extends JpaRepository<JobAssignmentEntity, Long> {

  @Query(
          value = "SELECT ja.* FROM job_assignments ja\n"
          + "INNER JOIN cargo_activity_logs cal ON cal.job_assignment_id = ja.id\n"
          + "INNER JOIN flights f ON f.id = ja.flight_id\n"
          + "WHERE ja.assigned_user_id = :assigned_user_id\n"
          + "AND ja.flight_id = :flight_id",
          nativeQuery = true)
  List<JobAssignmentEntity> getAssignedUserIdAndFlightId(@Param("assigned_user_id") long assignedUserId, @Param("flight_id") long flightId);
  
  List<JobAssignmentEntity> findByAssignedUserIdAndFlightId(long assignedUserId, long flightId);
}
