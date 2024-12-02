package com.electroinc.store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electroinc.store.Entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
