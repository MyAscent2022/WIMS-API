/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.model;

import com.ascentdev.wims.entity.CargoActivityLogsEntity;
import com.ascentdev.wims.entity.HawbEntity;
import com.ascentdev.wims.entity.MawbEntity;
import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
public class ConfirmCargoModel {
  CargoActivityLogsEntity cargoLogs;
  MawbEntity mawbDetails;
  HawbEntity hawbDetails;
}
