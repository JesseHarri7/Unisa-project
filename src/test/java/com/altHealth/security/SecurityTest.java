package com.altHealth.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.altHealth.Utils.ServiceHelper;
import com.altHealth.entity.User;
import com.altHealth.entity.UserRole;
import com.altHealth.repository.UserRepo;
import com.altHealth.repository.UserRoleRepo;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SecurityTest {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserRoleRepo userRoleRepo;
	@Autowired
	ServiceHelper service;

	@Test
	public void createDefaultAccess() {
		String email = "jesse.harri@gmail.com";
		User user = service.getUserService().findByEmail(email);
		UserRole userRoleAssign = service.getUserRoleService().findByEmail(email);

		if(user == null) {
			User newUser = new User();
			newUser.setEmail(email);
			newUser.setFirstName("Jesse");
			newUser.setLastName("Harri");
			newUser.setPassword("123");
			
			user = userRepo.save(newUser);
		}
		if(userRoleAssign == null) {
			UserRole newUserRole = new UserRole();
			newUserRole.setEmail(user);
			newUserRole.setRole("ADMIN");
			
			userRoleRepo.save(newUserRole);
		}
	}
}
