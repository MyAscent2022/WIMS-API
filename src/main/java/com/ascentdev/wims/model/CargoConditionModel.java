/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ascentdev.wims.model;

import com.ascentdev.wims.entity.CargoConditionEntity;
import java.util.List;
import lombok.Data;

/**
 *
 * @author
 * ASCENT
 */
@Data
public class CargoConditionModel {
    List<CargoConditionEntity> condition;

  public List<CargoConditionEntity> getCondition() {
    return condition;
  }

  public void setCondition(List<CargoConditionEntity> condition) {
    this.condition = condition;
  }
    
    
}
