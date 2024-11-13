package com.electroinc.store.service.Impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electroinc.store.Dto.UserDto;
import com.electroinc.store.Entity.User;
import com.electroinc.store.Repository.UserRepository;
import com.electroinc.store.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto CreateUser(UserDto userDto) {

        String UserId = UUID.randomUUID().toString();
        userDto.setUserId(UserId);
        User newUser = DtoToEntity(userDto);
        User savedUser = userRepository.save(newUser);
        UserDto NewDto = EntityToDto(savedUser);

        return NewDto;
    }

    @Override
    public UserDto UpdateUser(UserDto userDto, String UserId) {
        User user = userRepository.findById(UserId)
                .orElseThrow(() -> new RuntimeException("User is not Found by Given Id " + UserId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());

        User newuser = userRepository.save(user);
        UserDto NewUserDto = EntityToDto(newuser);
        return NewUserDto;
    }

    @Override
    public void DeleteUser(String UserId) {
        User user = userRepository.findById(UserId)
                .orElseThrow(() -> new RuntimeException("User is not Found by Given Id"));
        userRepository.delete(user);

    }

    @Override
    public List<UserDto> GetAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> alluser = users.stream().map(user -> EntityToDto(user)).collect(Collectors.toList());
        return alluser;
    }

    @Override
    public UserDto GetSingelUser(String UserId) {
        User user = userRepository.findById(UserId)
                .orElseThrow(() -> new RuntimeException("User is not Found by Given Id"));
        UserDto userDto = EntityToDto(user);
        return userDto;
    }

    private UserDto EntityToDto(User user) {
        // UserDto userDto =
        // UserDto.builder().Email(user.getEmail()).Name(user.getName()).Gender(user.getGender())
        // .UserId(user.getUserId()).build();
        // return userDto;
        return mapper.map(user, UserDto.class);
    }

    private User DtoToEntity(UserDto userDto) {
        // User user =
        // User.builder().Email(userDto.getEmail()).Name(userDto.getName()).Gender(userDto.getGender())
        // .UserId(userDto.getUserId())
        // .build();
        // return user;

        return mapper.map(userDto, User.class);
    }

    @Override
    public UserDto GetUserFromEmail(String Email) {
        User userEmail = userRepository.findByEmail(Email)
                .orElseThrow(() -> new RuntimeException("User is not found by Email"));
        return EntityToDto(userEmail);
    }

    @Override
    public List<UserDto> searchUserName(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> user1 = users.stream().map((user) -> EntityToDto(user)).collect(Collectors.toList());
        return user1;
    }

}
