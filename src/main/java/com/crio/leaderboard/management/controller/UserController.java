package com.crio.leaderboard.management.controller;

import lombok.AllArgsConstructor;
//import com.crio.starter.exchange.ResponseDto;
//import com.crio.starter.service.GreetingsService;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crio.leaderboard.management.entity.UserEntity;
import com.crio.leaderboard.management.exchange.RequestDto;
import com.crio.leaderboard.management.service.UserService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@RestController
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@RequiredArgsConstructor
@AllArgsConstructor
public class UserController {

  //private final GreetingsService greetingsService;

  private UserService userService;

  // @GetMapping("/say-hello")
  // public ResponseDto sayHello(@RequestParam String messageId) {
  //   return greetingsService.getMessage(messageId);
  // }

  @PostMapping("/users")
  public ResponseEntity<String> registerUser(@RequestBody RequestDto request) {
    if(userService.userIdExists(request)){
      return ResponseEntity.badRequest().body("User already exists for id "+request.getUserId());
    }

    UserEntity userEntity = userService.saveUser(request);
    //return ResponseEntity.ok(URI.create("Insertion successful for id "+userEntity.getUserId())).build();
    return ResponseEntity.ok("Insertion successful for id "+userEntity.getUserId());
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserEntity>> getUsers(){
    List<UserEntity> userEntities = userService.getUsers();
    return ResponseEntity.ok(userEntities);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity getUsers(@PathVariable Integer userId){
      Optional<UserEntity> userEntity = userService.findUserById(userId);
      if(userEntity.isPresent())
        return ResponseEntity.ok(userEntity.get());
      else
        return ResponseEntity.ok("User not found for id "+userId);
  }

  //Add put request
 @PutMapping("/users/{userId}")
 public ResponseEntity<Object> updateUsersScore(@PathVariable Integer userId, @RequestParam Integer score){
     int modifiedCount = userService.updateUsersScore(userId, score);
     if(modifiedCount > 0)
       return ResponseEntity.status(HttpStatusCode.valueOf(204)).body("User modified count "+modifiedCount);
     else
       return ResponseEntity.ok("User not found for id "+userId);
 }

  //Add delete request
  @DeleteMapping("/users/{userId}")
  public ResponseEntity<Object> deregisterUser(@PathVariable Integer userId){
    userService.deleteByUserId(userId);
    return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
  }

}
