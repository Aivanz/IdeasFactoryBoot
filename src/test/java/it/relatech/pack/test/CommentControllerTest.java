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

import java.util.LinkedList;
import java.util.List;
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
import it.relatech.model.Idea;
import it.relatech.services.CommentService;
import it.relatech.services.IdeaService;

public class CommentControllerTest {

	private MockMvc mockMvc;

	@Mock
	private IdeaService ideaService;

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

		when(commentService.save(comment)).thenReturn(comment);

		mockMvc.perform(post("/comment/").contentType(MediaType.APPLICATION_JSON).content(asJSonString(comment)))
				.andExpect(status().isCreated()).andExpect(content().string(asJSonString(comment)));

		verify(commentService, times(1)).save(comment);
		verifyNoMoreInteractions(commentService);
	}

	@Test
	public void saveLinkTest() throws Exception {
		Comment comment = new Comment(), commentLinked = new Comment();
		Idea idea = new Idea();
		int id = 1;

		idea.setId(id);
		commentLinked.setIdea(idea);

		when(ideaService.getById(id)).thenReturn(idea);
		when(commentService.save(comment)).thenReturn(commentLinked);

		mockMvc.perform(post("/comment/{id}", idea.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJSonString(comment))).andExpect(status().isCreated())
				.andExpect(content().string(asJSonString(commentLinked)));

		verify(commentService, times(1)).save(comment);
		verifyNoMoreInteractions(commentService);
	}

	// PUT
	@Test
	public void updateTest() throws Exception {
		Comment comment = new Comment();
		comment.setId(1);
		comment.setText("Testo cambiato");

		when(commentService.update(comment)).thenReturn(comment);

		mockMvc.perform(put("/comment/").contentType(MediaType.APPLICATION_JSON).content(asJSonString(comment)))
				.andExpect(status().isCreated()).andExpect(content().string(asJSonString(comment)));
		verify(commentService, times(1)).update(comment);
		verifyNoMoreInteractions(commentService);
	}

	@Test
	public void acceptedTest() throws Exception {
		Comment comment = new Comment(), commentAccettato = new Comment();
		int id = 1;
		comment.setAccepted(false);
		comment.setId(id);
		commentAccettato.setId(id);
		commentAccettato.setAccepted(true);

		when(commentService.accept(comment.getId())).thenReturn(commentAccettato);

		mockMvc.perform(
				put("/comment/accepting").contentType(MediaType.APPLICATION_JSON).content(asJSonString(comment)))
				.andExpect(status().isCreated()).andExpect(content().string(asJSonString(commentAccettato)));
		verify(commentService, times(1)).accept(comment.getId());
		verifyNoMoreInteractions(commentService);
	}

	// GET
	@Test
	public void listEvaluatingTest() throws Exception {
		List<Comment> listAccepted = new LinkedList<>();

		when(commentService.listEvaluating()).thenReturn(listAccepted);

		mockMvc.perform(get("/comment/")).andExpect(status().isOk())
				.andExpect(content().string(asJSonString(listAccepted)));
		verify(commentService, times(1)).listEvaluating();
		verifyNoMoreInteractions(commentService);
	}

	@Test
	public void listTest() throws Exception {
		List<Comment> listComplete = new LinkedList<>();

		when(commentService.list()).thenReturn(listComplete);

		mockMvc.perform(get("/comment/listAll")).andExpect(status().isOk())
				.andExpect(content().string(asJSonString(listComplete)));
		verify(commentService, times(1)).list();
		verifyNoMoreInteractions(commentService);
	}

	// DELETE
	@Test
	public void deleteTest() throws Exception {
		Comment comment = new Comment();
		int id = 1;

		comment.setId(id);

		doNothing().when(commentService).deleteId(comment.getId());

		mockMvc.perform(delete("/comment/{id}", id)).andExpect(status().isOk());
		verify(commentService, times(1)).deleteId(comment.getId());
		verifyNoMoreInteractions(commentService);
	}
}
