/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ascentdev.wims.serviceImp;

import com.ascentdev.wims.entity.SearchUserEntity;
import com.ascentdev.wims.entity.UserEntity;
import com.ascentdev.wims.entity.UserLogsEntity;
import com.ascentdev.wims.error.ErrorException;
import com.ascentdev.wims.model.ApiResponseModel;
import com.ascentdev.wims.repository.SearchUserRepository;
import com.ascentdev.wims.repository.UserLogsRepository;
import com.ascentdev.wims.repository.UserProfileRepository;
import com.ascentdev.wims.repository.UserRepository;
import com.ascentdev.wims.service.UserService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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

  @Autowired
  UserLogsRepository ulRepo;

  @Autowired
  SearchUserRepository suRepo;

  @Override
  public ApiResponseModel userLogin(String username, String passkey) {
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    SearchUserEntity searchUser = new SearchUserEntity();
    UserEntity user = new UserEntity();

    try {
      searchUser = suRepo.findByUsername(username);
      user = uRepo.findByUsername(username);
      if (user == null) {
        message = "Wrong username";
        statusCode = 404;
        status = false;
      } else {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(passkey, user.getPasskey())) {
          user = uRepo.save(user);
          resp.setData(user);

          List<UserLogsEntity> activeSessions = ulRepo.findByUserIdAndLogOutAtAndIsMobile(searchUser.getUserId(), null, true);
          for (UserLogsEntity activeSession : activeSessions) {
            activeSession.setLogOutAt(Timestamp.valueOf(date));
            activeSession.setActive(false);
            ulRepo.save(activeSession);
          }

          if (searchUser != null) {
            UserLogsEntity userLogs = new UserLogsEntity();
            userLogs.setLogInAt(Timestamp.valueOf(date));
            userLogs.setName(searchUser.getFirstname() + " " + searchUser.getLastname());
            userLogs.setRoleId(searchUser.getRoleId());
            userLogs.setUserId(searchUser.getUserId());
            userLogs.setActive(true);
            userLogs.setMobile(true);
            userLogs = ulRepo.save(userLogs);

            resp.setMessage(message);
            resp.setStatus(status);
            resp.setStatusCode(statusCode);
          } else {
            message = "Wrong User";
            statusCode = 404;
            status = false;
          }
        } else {
          message = "Wrong password";
          statusCode = 404;
          status = false;
        }
      }
    } catch (ErrorException e) {
      e.printStackTrace();
    }
    return resp;
  }

  @Override
  public ApiResponseModel userLogout(long userId) {
    ApiResponseModel resp = new ApiResponseModel();
    LocalDateTime date = LocalDateTime.now();

    SearchUserEntity searchUser = new SearchUserEntity();
    UserLogsEntity userLogs = new UserLogsEntity();

    try {
      searchUser = suRepo.findByUserId(userId);
      if (searchUser != null) {
        userLogs = ulRepo.findByUserIdAndLogOutAtAndIsMobile(searchUser.getUserId(), null, true).get(0);
        userLogs.setLogOutAt(Timestamp.valueOf(date));
        userLogs.setActive(false);
        userLogs = ulRepo.save(userLogs);

        resp.setMessage(message);
        resp.setStatus(status);
        resp.setStatusCode(statusCode);
      } else {
        message = "No User Found";
        statusCode = 404;
        status = false;
      }
    } catch (ErrorException e) {
      e.printStackTrace();
    }
    return resp;
  }

}
