package com.electroinc.store.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electroinc.store.Dto.PageableResponse;
import com.electroinc.store.Dto.UserDto;
import com.electroinc.store.Entity.User;
import com.electroinc.store.Exception.ResourceNotFound;
import com.electroinc.store.Helper.helper;
import com.electroinc.store.Repository.UserRepository;
import com.electroinc.store.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String ImagePath;

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
                .orElseThrow(() -> new ResourceNotFound("User is not Found by Given Id " + UserId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        User newuser = userRepository.save(user);
        UserDto NewUserDto = EntityToDto(newuser);
        return NewUserDto;
    }

    @Override
    public void DeleteUser(String UserId) {
        User user = userRepository.findById(UserId)
                .orElseThrow(() -> new ResourceNotFound("User is not Found by Given Id"));
        String fullpath = ImagePath + user.getImageName();

        try {
            Path path = Paths.get(fullpath);
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        userRepository.delete(user);

    }

    @Override
    public PageableResponse<UserDto> GetAllUser(int pagenumber, int pagesize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = helper.getPageableResponse(page, UserDto.class);
        return response;
    }

    @Override
    public UserDto GetSingelUser(String UserId) {
        User user = userRepository.findById(UserId)
                .orElseThrow(() -> new ResourceNotFound("User is not Found by Given Id"));
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
                .orElseThrow(() -> new ResourceNotFound("User is not found by Email"));
        return EntityToDto(userEmail);
    }

    @Override
    public List<UserDto> searchUserName(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> user1 = users.stream().map((user) -> EntityToDto(user)).collect(Collectors.toList());
        return user1;
    }

}
