/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ascentdev.wims.service;

import com.ascentdev.wims.model.ApiResponseModel;

/**
 *
 * @author ASCENT
 */
public interface UserService {
  ApiResponseModel userLogin (String username, String passkey);
  ApiResponseModel userLogout (String username);
}
