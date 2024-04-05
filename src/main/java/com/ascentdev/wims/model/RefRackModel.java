/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.model;

import com.ascentdev.wims.entity.GetRacksEntity;
import com.ascentdev.wims.entity.RefRackEntity;
import java.util.List;
import lombok.Data;

/**
 *
 * @author ASCENT
 */
@Data
public class RefRackModel {
  List<RefRackEntity> layers;
  List<GetRacksEntity> refRacks;
}
