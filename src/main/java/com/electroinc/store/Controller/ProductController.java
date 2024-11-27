package com.electroinc.store.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electroinc.store.Dto.ApiResponseMessage;
import com.electroinc.store.Dto.ImageResponse;
import com.electroinc.store.Dto.PageableResponse;
import com.electroinc.store.Dto.ProductDto;
import com.electroinc.store.service.FileService;
import com.electroinc.store.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    // create
    @PostMapping
    public ResponseEntity<ProductDto> CreateProduct(@RequestBody ProductDto productDto) {
        // TODO: process POST request
        ProductDto createProduct = productService.CreateProduct(productDto);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> UpdateProduct(@PathVariable String productId,
            @RequestBody ProductDto productDto) {
        // TODO: process PUT request
        ProductDto updateProduct = productService.UpdateProduct(productDto, productId);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> DeleteProduct(@PathVariable String productId) {
        productService.DeleteProduct(productId);
        return new ResponseEntity<>(
                ApiResponseMessage.builder().Message("product Deleted Sucessfully !!").status(HttpStatus.OK)
                        .sucess(true).build(),
                HttpStatus.OK);
    }

    // getOne
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId) {
        ProductDto singleProduct = productService.getSingleProduct(productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }

    // getAll
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> GetAllProduct(
            @RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
            @RequestParam(value = "sortBy", defaultValue = "productTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        PageableResponse<ProductDto> getAllProduct = productService.GetAllProduct(pagenumber, pagesize, sortBy,
                sortDir);
        return new ResponseEntity<>(getAllProduct, HttpStatus.OK);
    }

    // search by Title
    @GetMapping("/search/{productTitle}")
    public ResponseEntity<PageableResponse<ProductDto>> SearchProduct(
            @PathVariable String productTitle,
            @RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
            @RequestParam(value = "sortBy", defaultValue = "productTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        PageableResponse<ProductDto> getAllProduct = productService.SearchProduct(productTitle, pagenumber, pagesize,
                sortBy,
                sortDir);
        return new ResponseEntity<>(getAllProduct, HttpStatus.OK);
    }

    // isLive
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> liveProduct(

            @RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
            @RequestParam(value = "sortBy", defaultValue = "productTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        PageableResponse<ProductDto> getAllProduct = productService.GetAllLive(pagenumber, pagesize, sortBy,
                sortDir);
        return new ResponseEntity<>(getAllProduct, HttpStatus.OK);
    }

    // Update Image
    @PutMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> UploadImage(@RequestParam("productImage") MultipartFile image,
            @PathVariable String productId) {
        String uploadImage = fileService.UploadImage(image, imagePath);
        ProductDto product = productService.getSingleProduct(productId);
        product.setProductImage(uploadImage);
        productService.UpdateProduct(product, productId);
        ImageResponse imageResponse = ImageResponse.builder().Message("image is uploaded").imageName(uploadImage)
                .status(HttpStatus.CREATED).sucess(true).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {

        ProductDto product = productService.getSingleProduct(productId);
        InputStream resource = fileService.getResource(imagePath, product.getProductImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
}
