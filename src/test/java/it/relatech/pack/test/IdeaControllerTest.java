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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.relatech.controller.IdeaController;
import it.relatech.model.Comment;
import it.relatech.model.Idea;
import it.relatech.services.IdeaService;

@WebMvcTest
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

		when(ideaService.save(idea)).thenReturn(idea);

		mockMvc.perform(post("/idea/").contentType(MediaType.APPLICATION_JSON).content(asJSonString(idea)))
				.andExpect(status().isCreated()).andExpect(content().string(asJSonString(idea))).andDo(print());

		verify(ideaService, times(1)).save(idea);
		verifyNoMoreInteractions(ideaService);
	}

	// PUT
	@Test
	public void updateTest() throws Exception {
		Idea ideaNuova = new Idea();
		int id = 1;

		ideaNuova.setId(id);
		ideaNuova.setText("Testo nuovo");

		when(ideaService.update(ideaNuova)).thenReturn(ideaNuova);

		mockMvc.perform(put("/idea/").contentType(MediaType.APPLICATION_JSON).content(asJSonString(ideaNuova)))
				.andExpect(status().isCreated()).andExpect(content().string(asJSonString(ideaNuova)));

		verify(ideaService, times(1)).update(ideaNuova);
		verifyNoMoreInteractions(ideaService);
	}

	@Test
	public void voteTest() throws Exception {
		Idea idea = new Idea(), ideaVoto = new Idea();
		int voto = 2;

		ideaVoto.setVoteaverage(2);
		ideaVoto.setVotecounter(1);

		when(ideaService.vote(idea, voto)).thenReturn(ideaVoto);

		mockMvc.perform(
				put("/idea/vote/{voto}", voto).contentType(MediaType.APPLICATION_JSON).content(asJSonString(idea)))
				.andExpect(status().isCreated()).andExpect(content().string(asJSonString(ideaVoto)));

		verify(ideaService, times(1)).vote(idea, voto);
		verifyNoMoreInteractions(ideaService);
	}

	@Test
	public void acceptedTest() throws Exception {
		Idea idea = new Idea(), ideaAccettata = new Idea();
		int id = 1;

		idea.setId(id);
		idea.setAccepted(false);
		ideaAccettata.setId(id);
		ideaAccettata.setAccepted(true);

		when(ideaService.accept(id)).thenReturn(ideaAccettata);

		mockMvc.perform(
				put("/idea/accepting/{id}", id).contentType(MediaType.APPLICATION_JSON).content(asJSonString(idea)))
				.andExpect(status().isCreated()).andExpect(content().string(asJSonString(ideaAccettata)));

		verify(ideaService, times(1)).accept(id);
		verifyNoMoreInteractions(ideaService);
	}

	// GET
	@Test
	public void listTest() throws Exception {
		List<Idea> listaCompleta = new LinkedList<>();

		when(ideaService.listNotAccepted()).thenReturn(listaCompleta);

		mockMvc.perform(get("/idea/evaluating/")).andExpect(status().isOk())
				.andExpect(content().string(asJSonString(listaCompleta)));
		verify(ideaService, times(1)).listNotAccepted();
		verifyNoMoreInteractions(ideaService);
	}

	@Test
	public void listAcceptedTest() throws Exception {
		List<Idea> listaAccettate = new LinkedList<>();

		when(ideaService.listAccepted()).thenReturn(listaAccettate);

		mockMvc.perform(get("/idea/")).andExpect(status().isOk())
				.andExpect(content().string(asJSonString(listaAccettate)));
		verify(ideaService, times(1)).listAccepted();
		verifyNoMoreInteractions(ideaService);
	}

	@Test
	public void getByIdTest() throws Exception {
		Idea idea = new Idea();
		int id = 1;

		idea.setText(contenuto);
		idea.setId(id);

		when(ideaService.getById(idea.getId())).thenReturn(idea);

		mockMvc.perform(get("/idea/{id}", id)).andExpect(status().isOk())
				.andExpect(content().string(asJSonString(idea)));
		verify(ideaService, times(1)).getById(idea.getId());
		verifyNoMoreInteractions(ideaService);
	}

	@Test
	public void listCommentTest() throws Exception {
		Idea idea = new Idea();
		Comment comment = new Comment();
		List<Comment> list = new ArrayList<>();
		int id = 1;

		idea.setId(id);
		idea.setComlist(list);
		comment.setId(id);
		comment.setIdea(idea);
		list.add(comment);

		when(ideaService.getListComment(idea.getId())).thenReturn(list);

		mockMvc.perform(get("/idea/comlist/{id}", id)).andExpect(status().isOk())
				.andExpect(content().string(asJSonString(list)));

		verify(ideaService, times(1)).getListComment(id);
		verifyNoMoreInteractions(ideaService);
	}

	// DELETE
	@Test
	public void deleteTest() throws Exception {
		Idea idea = new Idea();
		int id = 1;
		idea.setId(id);

		// Per assicurarsi che il metodo torni void
		doNothing().when(ideaService).deleteId(id);

		mockMvc.perform(delete("/idea/{id}", id)).andExpect(status().isOk());
		verify(ideaService, times(1)).deleteId(id);
		verifyNoMoreInteractions(ideaService);
	}

}
