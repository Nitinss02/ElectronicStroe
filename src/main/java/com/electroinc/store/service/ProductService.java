package com.electroinc.store.service;

import com.electroinc.store.Dto.PageableResponse;
import com.electroinc.store.Dto.ProductDto;

public interface ProductService {
    public ProductDto CreateProduct(ProductDto productDto);

    public ProductDto UpdateProduct(ProductDto productDto, String productId);

    public PageableResponse<ProductDto> GetAllProduct(int pagenumber, int pagesize, String sortBy, String sortDir);

    public ProductDto getSingleProduct(String productId);

    public PageableResponse<ProductDto> GetAllLive(int pagenumber, int pagesize, String sortBy, String sortDir);

    public PageableResponse<ProductDto> SearchProduct(String productTitle, int pagenumber, int pagesize, String sortBy,
            String sortDir);

    public String DeleteProduct(String productId);

    public ProductDto createWithCategory(ProductDto productDto, String categoryId);

    public ProductDto UpdateCategorywithProduct(String categoryId, String productId);

    public PageableResponse<ProductDto> GetAllCategory(String categoryId, int pagenumber, int pagesize, String sortBy,
            String sortDir);
}
