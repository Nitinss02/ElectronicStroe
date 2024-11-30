package com.electroinc.store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electroinc.store.Entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
