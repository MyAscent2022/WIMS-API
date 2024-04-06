/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.repository;

import com.ascentdev.wims.model.ApprovedReleasingModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;

/**
 *
 * @author User
 */
@Repository
public class ApprovedReleasingDao {

  private final String CON_STRING = "jdbc:postgresql://192.168.10.62:5440/wms_1";
  private final String CON_USERNAME = "postgres";
  private final String CON_PASSWORD = "postgres";

  public Connection connect() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(CON_STRING, CON_USERNAME, CON_PASSWORD);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return conn;
  }

  public List<ApprovedReleasingModel> getForReleasing(Connection conn) throws SQLException {
    List<ApprovedReleasingModel> list = new ArrayList<>();
    String sql = "SELECT masterawb,houseawb FROM cargo_status_logs WHERE status='approved_releasing'";
    PreparedStatement prepStmt = conn.prepareStatement(sql);
    try {

      ApprovedReleasingModel model;
      ResultSet rs = prepStmt.executeQuery();

      while (rs.next()) {
        model = new ApprovedReleasingModel();
        model.setMawb(rs.getString("masterawb"));
        model.setHawb(rs.getString("houseawb"));
        list.add(model);
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return list;
  }

  public int updateCargoStatusLogs(Connection conn, String mawb, String hawb) throws SQLException {

    String sql = "";
    PreparedStatement prepStmt = null;
    try {

      if (hawb == null) {
        sql = "UPDATE cargo_status_logs SET status='released' WHERE masterawb=? AND houseawb=null";

        prepStmt = conn.prepareStatement(sql);
        prepStmt.setString(1, mawb);

        prepStmt.executeUpdate();
      } else {
        sql = "UPDATE cargo_status_logs SET status='released' WHERE masterawb=? AND houseawb=?";

        prepStmt = conn.prepareStatement(sql);
        prepStmt.setString(1, mawb);
        prepStmt.setString(2, hawb);

      }
      prepStmt.executeUpdate();

      return 1;
    } catch (Exception ex) {
      Logger.getLogger(ApprovedReleasingDao.class.getName()).log(Level.SEVERE, null, ex);
      ex.printStackTrace();
    }
    return 0;
  }
}
