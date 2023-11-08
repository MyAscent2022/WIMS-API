/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.entity.UserEntity;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.repository.UserRepository;
import com.ascentdev.wims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ASCENT
 */
@Service
public class UserServiceImp implements UserService {

  boolean status = true;
  String message = "Success!";
  int statusCode = 200;

  @Autowired
  UserRepository uRepo;

  @Override
  public ApiResponseModel userLogin(String username, String passkey) {
    ApiResponseModel resp = new ApiResponseModel();

    UserEntity user = uRepo.findByUsername(username);
    if (user == null) {
      message = "Wrong username";
      statusCode = 404;
      status = false;
    } else {
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      if (passwordEncoder.matches(passkey, user.getPasskey())) {
        user = uRepo.save(user);
        resp.setData(user);
        resp.setMessage(message);
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      } else {
        message = "Wrong password";
        statusCode = 404;
        status = false;
      }
    }
    return resp;
  }

}
