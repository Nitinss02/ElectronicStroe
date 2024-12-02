package com.electroinc.store.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrderItemDto {
    private int orderItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;
}
