package com.easysign;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.easysign.dao.AppRoleRepository;
import com.easysign.dao.AppUserRepository;
import com.easysign.entities.AppRole;
import com.easysign.service.AccountService;
import com.easysign.service.DataRegister;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UaaServiceApplicationTests {

	@Autowired
	private AccountService accountService;

	@Autowired
	AppRoleRepository roleRepo;

	AppUserRepository userRepo;

	@Test
	public void can_create_role_in_accountService() {
		assertNotNull(accountService.save(new AppRole(null, "ADMIN")));
		assertNotNull(accountService.save(new AppRole(null, "USER")));

	}

	@Test
	public void can_create_user_in_accountService() {
		DataRegister userData = new DataRegister("zakaria", "ennajeh", "ennaje.zakari@gmail.com", "0666778899",
				"zakaria123", "zakaria123");

		assertNotNull(accountService.saveUser(userData));

	}

	/**
	 * User can add a document in the accountService
	 */

	@Test
	public void can_add_role_to_user_in_accountService() {
		AppRole role = new AppRole();
		role.setRoleName("ADMIN");

		roleRepo.save(role);
		userRepo.findByEmail("ennaje.zakari@gmail.com").getRoles().add(roleRepo.findByRoleName("ADMIN"));
		assertNotNull(true);

	}

	@Test
	public void can_get_user_by_username() {
		String mail = "ennaje.zakari@gmail.com";

		assertNotNull(accountService.loadUserByUsername(mail));
	}

}
