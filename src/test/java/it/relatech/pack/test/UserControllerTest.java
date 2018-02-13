package it.relatech.pack.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.relatech.controller.UserController;
import it.relatech.model.User;
import it.relatech.services.UserService;

public class UserControllerTest {

	private MockMvc mockMvc;
	private RequestPostProcessor requestPostProcessor;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	public String asJSonString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		requestPostProcessor = user("admin").password("admin").roles("ADMIN");
	}

	// POST
	@Test
	public void saveTest() throws Exception {
		User user = new User();

		mockMvc.perform(post("/user/").with(requestPostProcessor).contentType(MediaType.APPLICATION_JSON)
				.content(asJSonString(user))).andExpect(status().isCreated());
		verify(userService, times(1)).save(user);
		verifyNoMoreInteractions(userService);
	}

	// GET
	@Test
	public void getListTest() throws Exception {
		mockMvc.perform(get("/user/").with(requestPostProcessor)).andExpect(status().isOk());
		verify(userService, times(1)).getList();
		verifyNoMoreInteractions(userService);
	}

	// PUT
	@Test
	public void updateTest() throws Exception {
		User user = new User();
		int id = 1;

		mockMvc.perform(put("/user/{id}", id).with(requestPostProcessor).contentType(MediaType.APPLICATION_JSON)
				.content(asJSonString(user))).andExpect(status().isCreated());
		verify(userService, times(1)).update(user);
		verifyNoMoreInteractions(userService);
	}

	// DELETE
	@Test
	public void deleteTest() throws Exception {
		int id = 1;

		mockMvc.perform(delete("/user/{id}", id).with(requestPostProcessor)).andExpect(status().isOk());
		verify(userService, times(1)).delete(id);
		verifyNoMoreInteractions(userService);
	}

}
