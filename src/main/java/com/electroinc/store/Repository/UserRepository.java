package com.electroinc.store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.electroinc.store.Entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
