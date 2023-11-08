/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.controller;

import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASCENT
 */
@RestController
@RequestMapping("/wims_api/")
public class UserController {
  
  @Autowired
  UserServiceImp userServiceImp;
  
  @GetMapping("user_login")
  public ApiResponseModel getUser(@RequestParam("username") String username, @RequestParam("passkey") String passkey) {
    return userServiceImp.userLogin(username, passkey);
  }
}
