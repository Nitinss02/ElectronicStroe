package com.electroinc.store.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electroinc.store.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByProductTitleContaining(String keyTitle, Pageable pageable);

    Page<Product> findByLiveTrue(Pageable pageable);
}
