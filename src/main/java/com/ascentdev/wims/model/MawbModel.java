/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.model;

import com.ascentdev.wims.entity.MawbEntity;
import java.security.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
public class MawbModel {
  List<MawbListModel> mawbs;
}
