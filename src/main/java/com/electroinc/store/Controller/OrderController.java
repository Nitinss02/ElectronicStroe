package com.electroinc.store.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electroinc.store.Dto.ApiResponseMessage;
import com.electroinc.store.Dto.CreateOrderDto;
import com.electroinc.store.Dto.OrderDto;
import com.electroinc.store.Dto.PageableResponse;
import com.electroinc.store.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> CreateOrder(@RequestBody CreateOrderDto orderDto) {
        OrderDto createOrder = orderService.CreateOrder(orderDto);
        return new ResponseEntity<>(createOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> DeleteOrder(@PathVariable String orderId) {
        orderService.RemoveOrder(orderId);
        return new ResponseEntity<>(
                ApiResponseMessage.builder().Message("Order is Removed").status(HttpStatus.OK).sucess(true).build(),
                HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderDto>> GetOrderByUser(@PathVariable String userId) {
        List<OrderDto> UserOrder = orderService.GetUserOrder(userId);
        return new ResponseEntity<>(UserOrder, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> GetAllOrder(
            @RequestParam(value = "pagenumber", defaultValue = "0", required = false) int pagenumber,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) int pagesize,
            @RequestParam(value = "sortBy", defaultValue = "productTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        PageableResponse<OrderDto> allOrders = orderService.getAllOrders(pagenumber, pagesize, sortBy, sortDir);
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }
}
