package com.electroinc.store.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.internal.bytebuddy.agent.builder.AgentBuilder.PoolStrategy.Eager;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Entity

public class Orders {
    @Id
    @Column(name = "order_id")
    private String orderId;
    private String orderStatus;
    private String paymentStatus;
    private int orderAmmount;
    @Column(length = 1000)
    private String billingAddress;
    @Column(nullable = false)
    private String billingNames;
    private int billingPhone;
    private Date orderedDate;
    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();
}
