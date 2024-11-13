package com.electroinc.store.service;

import java.util.List;

import com.electroinc.store.Dto.UserDto;

public interface UserService {
    // Create
    UserDto CreateUser(UserDto userDto);

    // Update
    UserDto UpdateUser(UserDto userDto, String UserId);

    // Delete
    void DeleteUser(String UserId);

    // Get All User
    List<UserDto> GetAllUser();

    // Get Single user
    UserDto GetSingelUser(String UserId);

    // get User from Email
    UserDto GetUserFromEmail(String Email);

    // Search form Name
    List<UserDto> searchUserName(String keyword);
}
