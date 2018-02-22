package it.relatech.pack.test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.relatech.controller.AuthController;
import it.relatech.controller.UserController;
import it.relatech.model.User;
import it.relatech.services.UserService;

public class UserControllerTest {

	private MockMvc mockMvc;

	@Mock
	private HttpServletRequest request;

	@Mock
	private Principal principal;

	@Mock
	private UserService userService;

	@Mock
	private AuthController authController;

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
	}

	// POST
	@Test
	public void saveTest() throws Exception {
		User user = new User();

		when(userService.save(user)).thenReturn(user);

		mockMvc.perform(post("/user/").contentType(MediaType.APPLICATION_JSON).content(asJSonString(user)))
				.andExpect(status().isCreated()).andExpect(content().string(asJSonString(user)));
		verify(userService, times(1)).save(user);
		verifyNoMoreInteractions(userService);
	}

	// GET
	@Test
	public void getListTest() throws Exception {
		List<User> listUser = new LinkedList<>();

		when(userService.getList()).thenReturn(listUser);

		mockMvc.perform(get("/user/")).andExpect(status().isOk()).andExpect(content().string(asJSonString(listUser)));
		verify(userService, times(1)).getList();
		verifyNoMoreInteractions(userService);
	}

	// PUT
	@Test
	public void updateTest() throws Exception {
		User user = new User();
		int id = 1;

		when(userService.checkAuth(principal, id)).thenReturn(true);
		when(userService.update(user)).thenReturn(user);

		mockMvc.perform(put("/user/{id}", id).contentType(MediaType.APPLICATION_JSON).content(asJSonString(user))
				.principal(principal)).andExpect(status().isCreated()).andExpect(content().string(asJSonString(user)));

		verify(userService, times(1)).checkAuth(principal, id);
		verify(userService, times(1)).update(user);
		verifyNoMoreInteractions(userService);
	}

	// DELETE
	@Test
	public void deleteTest() throws Exception {
		int id = 1;

		when(userService.checkAuth(principal, id)).thenReturn(true);
		doNothing().when(authController).logoutPage(request);
		doNothing().when(userService).delete(id);

		mockMvc.perform(delete("/user/{id}", id).principal(principal)).andExpect(status().isOk());

		verify(userService, times(1)).checkAuth(principal, id);
		verify(userService, times(1)).delete(id);
		verifyNoMoreInteractions(userService);
	}

}
