package com.electroinc.store.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electroinc.store.Entity.Order;
import com.electroinc.store.Entity.User;

public interface OrderRepository extends JpaRepository<Order, String> {
    public List<Order> findByUser(User user);

}
