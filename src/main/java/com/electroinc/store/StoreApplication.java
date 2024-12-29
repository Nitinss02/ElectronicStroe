package com.electroinc.store;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.electroinc.store.Entity.Role;
import com.electroinc.store.Repository.RoleRepository;

@SpringBootApplication
public class StoreApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Value("${admin.role}")
	private String adminId;

	@Value("${normal.role}")
	private String normalId;

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String encode = passwordEncoder.encode("abcd");
		System.out.println(encode);

		try {
			Role adminrole = Role.builder().roleId(adminId).roleName("ROLE_ADMIN").build();
			Role NormalRole = Role.builder().roleId(normalId).roleName("ROLE_NORMAL").build();
			roleRepository.save(adminrole);
			roleRepository.save(NormalRole);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
