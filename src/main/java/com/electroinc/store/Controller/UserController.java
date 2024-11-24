package com.electroinc.store.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electroinc.store.Dto.ApiResponseMessage;
import com.electroinc.store.Dto.ImageResponse;
import com.electroinc.store.Dto.PageableResponse;
import com.electroinc.store.Dto.UserDto;
import com.electroinc.store.service.FileService;
import com.electroinc.store.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String ImageUploadPath;

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
    public ResponseEntity<PageableResponse<UserDto>> GetAllUser(
            @RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        PageableResponse<UserDto> getAllUser = userService.GetAllUser(pagenumber, pagesize, sortBy, sortDir);
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

    // upload File
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> UploadUserImage(@RequestParam("imageName") MultipartFile image,
            @PathVariable String userId) {

        String uploadImage = fileService.UploadImage(image, ImageUploadPath);
        UserDto user = userService.GetSingelUser(userId);
        user.setImageName(uploadImage);
        userService.UpdateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder().Message("image is uploaded").imageName(uploadImage)
                .status(HttpStatus.CREATED).sucess(true).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    // serve image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.GetSingelUser(userId);
        InputStream resource = fileService.getResource(ImageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
}
