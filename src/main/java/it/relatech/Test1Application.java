package it.relatech;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import it.relatech.model.User;
import it.relatech.services.UserService;

@SpringBootApplication
@EnableAsync
public class Test1Application {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(Test1Application.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			List<User> userList = new LinkedList<>();
			userList = (List<User>) userService.getList();

			if (userList.isEmpty()) {
				User admin = new User();
				admin.setUsername("admin");
				admin.setPassword("admin");
				admin.setMail("admin@admin.it");

				userService.save(admin);
			}
		};
	}
}
