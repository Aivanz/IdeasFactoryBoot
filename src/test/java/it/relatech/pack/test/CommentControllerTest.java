package it.relatech.pack.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.relatech.controller.CommentController;
import it.relatech.model.Comment;
import it.relatech.services.CommentService;

public class CommentControllerTest {
	private MockMvc mockMvc;

	@Mock
	private CommentService commentService;

	@InjectMocks
	private CommentController commentController;

	String testo;

	public String asJSonString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

		testo = "Commento di test";
	}

	// POST
	@Test
	public void saveTest() throws Exception {
		Comment comment = new Comment();
		comment.setText(testo);

		mockMvc.perform(post("/comment/").contentType(MediaType.APPLICATION_JSON).content(asJSonString(comment)))
				.andExpect(status().isCreated());
		verify(commentService, times(1)).save(comment);
		verifyNoMoreInteractions(commentService);
	}

	// ERRORE 500
	@Test
	public void saveLinkTest() throws Exception {
		Comment comment = new Comment();
		comment.setText(testo);
		int id = 1;

		mockMvc.perform(
				post("/comment/{id}", id).contentType(MediaType.APPLICATION_JSON).content(asJSonString(comment)))
				.andExpect(status().isCreated()).andDo(print());

		verify(commentService, times(1)).save(comment);
		verifyNoMoreInteractions(commentService);
	}

	// PUT
	@Test
	public void updateTest() throws Exception {
		Comment comment = new Comment();
		comment.setId(1);
		comment.setText("Testo cambiato");

		mockMvc.perform(put("/comment/").contentType(MediaType.APPLICATION_JSON).content(asJSonString(comment)))
				.andExpect(status().isCreated());
		verify(commentService, times(1)).update(comment);
		verifyNoMoreInteractions(commentService);
	}

	@Test
	public void acceptedTest() throws Exception {
		Comment comment = new Comment();
		comment.setAccepted(false);

		mockMvc.perform(put("/comment/accepting").with(user("admin").password("admin").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON).content(asJSonString(comment)))
				.andExpect(status().isCreated());
		verify(commentService, times(1)).accept(comment);
		verifyNoMoreInteractions(commentService);
	}

	// GET
	@Test
	public void listEvaluatingTest() throws Exception {
		mockMvc.perform(get("/comment/").with(user("admin").password("admin").roles("ADMIN")))
				.andExpect(status().isOk());
		verify(commentService, times(1)).listEvaluating();
		verifyNoMoreInteractions(commentService);
	}

	@Test
	public void listTest() throws Exception {
		mockMvc.perform(get("/comment/listAll")).andExpect(status().isOk());
		verify(commentService, times(1)).list();
		verifyNoMoreInteractions(commentService);
	}

	// DELETE
	@Test
	public void deleteTest() throws Exception {
		int id = 1;

		mockMvc.perform(delete("/comment/{id}", id).with(user("admin").password("admin").roles("ADMIN")))
				.andExpect(status().isOk());
		verify(commentService, times(1)).deleteId(id);
		verifyNoMoreInteractions(commentService);
	}
}
