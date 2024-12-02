package com.electroinc.store.Dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.electroinc.store.Entity.CartItem;
import com.electroinc.store.Entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CartDto {

    private String cartId;
    private Date createdAt;
    private User user;
    private List<CartItem> item = new LinkedList<>();

}
