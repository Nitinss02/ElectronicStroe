package com.electroinc.store.Dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.electroinc.store.Entity.OrderItem;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class OrderDto {

    private String orderId;
    private String orderStatus = "PENDING";
    private String paymentStatus = "NOTPAID";
    private int orderAmmount;
    @NotBlank(message = "Billing Address is requried")
    private String billingAddress;
    @NotBlank(message = "Billing Name is requried")
    private String billingName;
    @NotBlank(message = "Billing phone is requried")
    private int billingPhone;
    private Date orderedDate;
    private Date deliveredDate;

    // private UserDto user;
    private List<OrderItemDto> orderItems = new ArrayList<>();
}