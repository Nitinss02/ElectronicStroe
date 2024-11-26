package com.electroinc.store.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electroinc.store.Dto.ApiResponseMessage;
import com.electroinc.store.Dto.PageableResponse;
import com.electroinc.store.Dto.ProductDto;
import com.electroinc.store.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
