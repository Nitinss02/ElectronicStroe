package com.electroinc.store.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.electroinc.store.Dto.ApiResponseMessage;
import com.electroinc.store.Dto.UserDto;
import com.electroinc.store.service.UserService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // create user
    @PostMapping("/create")
    public ResponseEntity<UserDto> CreateUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUser = userService.CreateUser(userDto);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    // update user
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> UpdateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {

        UserDto updatedUser = userService.UpdateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }

    // delete User
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> DeleteUser(@PathVariable String userId) {
        userService.DeleteUser(userId);
        ApiResponseMessage meassage = ApiResponseMessage.builder().Message("User Delete Sucessfully !!").sucess(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(meassage, HttpStatus.OK);
    }

    // Get All User
    @GetMapping
    public ResponseEntity<List<UserDto>> GetAllUser() {
        List<UserDto> getAllUser = userService.GetAllUser();
        return new ResponseEntity<>(getAllUser, HttpStatus.OK);
    }

    // get user from userid
    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable String userId) {
        UserDto user = userService.GetSingelUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto userget = userService.GetUserFromEmail(email);
        return new ResponseEntity<>(userget, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> getuserBySearch(@PathVariable String keyword) {
        List<UserDto> searchUserName = userService.searchUserName(keyword).stream().toList();
        return new ResponseEntity<>(searchUserName, HttpStatus.OK);
    }

}
