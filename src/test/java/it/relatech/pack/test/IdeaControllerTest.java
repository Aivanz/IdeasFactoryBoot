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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.relatech.controller.IdeaController;
import it.relatech.model.Idea;
import it.relatech.services.IdeaService;

public class IdeaControllerTest {

	private MockMvc mockMvc;

	@Mock
	private IdeaService ideaService;

	@InjectMocks
	private IdeaController ideaController;

	String contenuto;
	String id;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(ideaController).build();

		contenuto = "Idea di test";
	}

	public String asJSonString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	// POST
	@Test
	public void saveTest() throws Exception {
		Idea idea = new Idea();
		idea.setText(contenuto);

		mockMvc.perform(post("/idea/").contentType(MediaType.APPLICATION_JSON).content(asJSonString(idea)))
				.andExpect(status().isCreated());
		verify(ideaService, times(1)).save(idea);
		verifyNoMoreInteractions(ideaService);
	}

	// PUT
	@Test
	public void updateTest() throws Exception {
		Idea idea = new Idea();
		idea.setText("Testo update");

		mockMvc.perform(put("/idea/").with(user("admin").password("admin").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON).content(asJSonString(idea))).andExpect(status().isCreated());
		verify(ideaService, times(1)).update(idea);
		verifyNoMoreInteractions(ideaService);
	}

	@Test
	public void voteTest() throws Exception {
		Idea idea = new Idea();
		int voto = 2;

		mockMvc.perform(
				put("/idea/vote/{voto}", voto).contentType(MediaType.APPLICATION_JSON).content(asJSonString(idea)))
				.andExpect(status().isCreated());
		verify(ideaService, times(1)).vote(idea, voto);
		verifyNoMoreInteractions(ideaService);
	}

	@Test
	public void acceptedTest() throws Exception {
		Idea idea = new Idea();

		mockMvc.perform(put("/idea/accepting/").with(user("admin").password("admin").roles("ADMIN"))
				.contentType(MediaType.APPLICATION_JSON).content(asJSonString(idea))).andExpect(status().isCreated());
		verify(ideaService, times(1)).accept(idea);
		verifyNoMoreInteractions(ideaService);
	}

	// GET
	@Test
	public void listTest() throws Exception {
		mockMvc.perform(get("/idea/evaluating/").with(user("admin").password("admin").roles("ADMIN")))
				.andExpect(status().isOk());
		verify(ideaService, times(1)).list();
		verifyNoMoreInteractions(ideaService);
	}

	@Test
	public void listAcceptedTest() throws Exception {
		mockMvc.perform(get("/idea/")).andExpect(status().isOk());
		verify(ideaService, times(1)).listAccepted();
		verifyNoMoreInteractions(ideaService);
	}

	@Test
	public void getByIdTest() throws Exception {
		int id = 1;

		mockMvc.perform(get("/idea/{id}", id)).andExpect(status().isOk());
		verify(ideaService, times(1)).getById(id);
		verifyNoMoreInteractions(ideaService);
	}

	// UNICO CHE FALLISCE, ERROR 500
	@Test
	public void listCommentTest() throws Exception {
		int id = 1;

		mockMvc.perform(get("/idea/comlist/{id}", id)).andExpect(status().isOk());
		verify(ideaService, times(1)).getById(id).getComlist();
		verifyNoMoreInteractions(ideaService);
	}

	// DELETE
	@Test
	public void deleteTest() throws Exception {
		int id = 1;

		mockMvc.perform(delete("/idea/{id}", id).with(user("admin").password("admin").roles("ADMIN")))
				.andExpect(status().isOk());
		verify(ideaService, times(1)).deleteId(id);
		verifyNoMoreInteractions(ideaService);
	}

}
