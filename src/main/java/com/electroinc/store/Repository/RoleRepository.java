package com.electroinc.store.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electroinc.store.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
