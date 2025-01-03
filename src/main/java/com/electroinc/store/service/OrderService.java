package com.electroinc.store.service;

import java.util.List;

import com.electroinc.store.Dto.CreateOrderDto;
import com.electroinc.store.Dto.OrderDto;
import com.electroinc.store.Dto.PageableResponse;

public interface OrderService {
    public OrderDto createOrder(CreateOrderDto orderDto);

    void removeOrder(String OrderId);

    List<OrderDto> getUserOrder(String userId);

    PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
}
