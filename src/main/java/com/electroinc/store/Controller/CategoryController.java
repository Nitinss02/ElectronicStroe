package com.electroinc.store.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electroinc.store.Dto.ApiResponseMessage;
import com.electroinc.store.Dto.CategoryDto;
import com.electroinc.store.Dto.ImageResponse;
import com.electroinc.store.Dto.PageableResponse;
import com.electroinc.store.Dto.UserDto;
import com.electroinc.store.service.CategoryService;
import com.electroinc.store.service.FileService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${Category.image.path}")
    private String ImageUploadPath;

    // create category
    @PostMapping
    public ResponseEntity<CategoryDto> CreateCategory(@Valid @RequestBody CategoryDto categoryDto) {
        // TODO: process POST request
        CategoryDto createCategory = categoryService.CreateCategory(categoryDto);
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId,
            @RequestBody CategoryDto categoryDto) {
        // TODO: process PUT request
        CategoryDto upadateCategory = categoryService.UpadateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(upadateCategory, HttpStatus.ACCEPTED);
    }

    // delete category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> DeleteCategory(@PathVariable String categoryId) {
        categoryService.DeleteCategory(categoryId);
        return new ResponseEntity<>(
                ApiResponseMessage.builder().Message("Category deleted").status(HttpStatus.OK).sucess(true).build(),
                HttpStatus.OK);
    }

    // single Category
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> GetCategory(@PathVariable String categoryId) {
        CategoryDto category = categoryService.GetSingleCategory(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> GetAllCategory(
            @RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        PageableResponse<CategoryDto> getAllCategory = categoryService.GetAllCategory(pagenumber, pagesize, sortBy,
                sortDir);
        return new ResponseEntity<>(getAllCategory, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> getuserBySearch(@PathVariable String keyword) {
        List<CategoryDto> list = categoryService.SearchCategory(keyword).stream().toList();
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> UploadUserImage(@RequestParam("coverImage") MultipartFile image,
            @PathVariable String categoryId) {

        String uploadImage = fileService.UploadImage(image, ImageUploadPath);
        CategoryDto category = categoryService.GetSingleCategory(categoryId);

        category.setCoverImage(uploadImage);
        categoryService.UpadateCategory(category, categoryId);
        ImageResponse imageResponse = ImageResponse.builder().Message("image is uploaded").imageName(uploadImage)
                .status(HttpStatus.CREATED).sucess(true).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    // serve image
    @GetMapping("/image/{categoryId}")
    public void serveUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {

        CategoryDto category = categoryService.GetSingleCategory(categoryId);
        InputStream resource = fileService.getResource(ImageUploadPath, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }

}
