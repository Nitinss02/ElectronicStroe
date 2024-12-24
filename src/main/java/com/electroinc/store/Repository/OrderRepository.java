package com.electroinc.store.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electroinc.store.Entity.Orders;
import com.electroinc.store.Entity.User;

public interface OrderRepository extends JpaRepository<Orders, String> {
    public List<Orders> findByUser(User user);

}
