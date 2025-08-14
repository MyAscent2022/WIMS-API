/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.model;

import com.ascentdev.wims.entity.HawbEntity;
import java.util.List;
import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
public class HawbModel {
  List<HawbEntity> hawbs;
  HawbEntity hawb;
  
  String hawb_number;
  
}
