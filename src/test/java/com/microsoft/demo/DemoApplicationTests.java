package com.microsoft.demo;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



import com.microsoft.demo.controller.UserController;
import com.microsoft.demo.model.User;
import com.microsoft.demo.repository.UserRepository;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
class DemoApplicationTests {

	@Mock 
	private UserRepository userRepository;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testGetAllUsers() throws Exception {

		User user = new User();
		user.setName("test");
		user.setSurname("test");
		user.setAge(1);
		user.setEmail("asdasd@testcom");
		user.setPhoneNumber("123456789");

		when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

		mockMvc.perform(get("/users")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("test"))
				.andExpect(jsonPath("$[0].surname").value("test"))
				.andExpect(jsonPath("$[0].age").value(1))
				.andExpect(jsonPath("$[0].email").value("asdasd@testcom"))
				.andExpect(jsonPath("$[0].phoneNumber").value("123456789"));
	}

	@Test
	public void testGetUserByEmail() throws Exception {

		User user = new User();
		user.setName("test");
		user.setSurname("test");
		user.setAge(1);
		user.setEmail("asdasd@test.com");
		user.setPhoneNumber("123456789");

		when(userRepository.findByEmail("asdasd@test.com")).thenReturn(java.util.Optional.of(user));

		mockMvc.perform(get("/users/by-email?email=asdasd@test.com")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("test"))
				.andExpect(jsonPath("$.surname").value("test"))
				.andExpect(jsonPath("$.age").value(1))
				.andExpect(jsonPath("$.email").value("asdasd@test.com"))
				.andExpect(jsonPath("$.phoneNumber").value("123456789"));
	}

	@Test
	public void testCreateUser() throws Exception {

		User user = new User();
		user.setName("test");
		user.setSurname("test");
		user.setAge(1);
		user.setEmail("asdasd@testcom");
		user.setPhoneNumber("123456789");

		when(userRepository.save(user)).thenReturn(user);

		mockMvc.perform(post("/users")
				.contentType("application/json")
				.content("{\"name\":\"test\",\"surname\":\"test\",\"age\":1,\"email\":\"asdasd@test.com\",\"phoneNumber\":\"123456789\"}"))
				.andExpect(status().isOk());
	}

	@Test
	public void testCreateUserWithInvalidEmail() throws Exception {


		mockMvc.perform(post("/users")
				.contentType("application/json")
				.content("{\"name\":\"test\",\"surname\":\"test\",\"age\":1,\"email\":\"asdasdtestcom\",\"phoneNumber\":\"123456789\"}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testAverageAge() throws Exception {

		User user = new User();
		user.setName("test");
		user.setSurname("test");
		user.setAge(1);
		user.setEmail("asdasd@test.com");
		user.setPhoneNumber("123456789");

		// create user2
		User user2 = new User();
		user2.setName("test2");
		user2.setSurname("test2");
		user2.setAge(2);
		user2.setEmail("asdasd@aasd.com");
		user2.setPhoneNumber("123456789");

		when(userRepository.findAll()).thenReturn(Arrays.asList(user, user2));

		mockMvc.perform(get("/users/average-age")).andExpect(status().isOk())
				.andExpect(content().string("1.5"));
	}

}
