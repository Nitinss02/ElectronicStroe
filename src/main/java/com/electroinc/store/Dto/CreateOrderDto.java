package com.electroinc.store.Dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderDto {
    private String OrderId;
    private String userId;

    private String orderStatus = "PENDING";
    private String paymentStatus = "NOTPAID";
    private String billingAddress;
    private String billingName;
    private int billingPhone;

}
