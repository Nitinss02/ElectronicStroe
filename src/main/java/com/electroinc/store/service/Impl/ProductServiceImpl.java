package com.electroinc.store.service.Impl;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.electroinc.store.Dto.PageableResponse;
import com.electroinc.store.Dto.ProductDto;
import com.electroinc.store.Entity.Category;
import com.electroinc.store.Entity.Product;
import com.electroinc.store.Exception.ResourceNotFound;
import com.electroinc.store.Helper.helper;
import com.electroinc.store.Repository.CategoryRepository;
import com.electroinc.store.Repository.ProductRepository;
import com.electroinc.store.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto CreateProduct(ProductDto productDto) {
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        productDto.setAddedDate(new Date());
        Product product = mapper.map(productDto, Product.class);
        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto UpdateProduct(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product is not Found by Given Id"));
        product.setProductTitle(productDto.getProductTitle());
        product.setProductDescription(productDto.getProductDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        // product.setAddedDate(productDto.getAddedDate());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImage(productDto.getProductImage());
        Product UpdateProduct = productRepository.save(product);
        return mapper.map(UpdateProduct, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> GetAllProduct(int pagenumber, int pagesize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        PageableResponse<ProductDto> response = helper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product is not found By Given id"));
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> GetAllLive(int pagenumber, int pagesize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        PageableResponse<ProductDto> response = helper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public String DeleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product is not Found by Given Id"));
        productRepository.delete(product);

        return "Product Deleted Sucessfully !!";
    }

    @Override
    public PageableResponse<ProductDto> SearchProduct(String productTitle, int pagenumber, int pagesize, String sortBy,
            String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
        Page<Product> page = productRepository.findByProductTitleContaining(productTitle, pageable);
        PageableResponse<ProductDto> response = helper.getPageableResponse(page, ProductDto.class);
        return response;
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Cateory is not found by Given Id"));
        Product product = mapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto UpdateCategorywithProduct(String categoryId, String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product id is not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Category id not found"));
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> GetAllCategory(String categoryId, int pagenumber, int pagesize, String sortBy,
            String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("category is not found by given id"));
        Page<Product> page = productRepository.findByCategory(category, pageable);

        return helper.getPageableResponse(page, ProductDto.class);
    }

}
