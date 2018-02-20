package it.relatech.pack.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import it.relatech.mail.EmailConfig;
import it.relatech.model.Comment;
import it.relatech.model.Idea;
import it.relatech.model.User;
import it.relatech.repository.IdeaDao;
import it.relatech.repository.UserDao;
import it.relatech.services.IdeaServiceImpl;

public class IdeaServiceTest {

	@Mock
	private IdeaDao idao;

	@Mock
	private UserDao userDao;

	@Mock
	private EmailConfig emailService;

	@InjectMocks
	private IdeaServiceImpl ideaService;

	private String contenuto;
	private int id;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		contenuto = "Prova idea";
		id = 1;
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void saveTest() throws Exception {
		Idea idea = new Idea();
		idea.setText(contenuto);

		// when(idao.save(any(Idea.class))).thenAnswer(new Answer() {
		// public Object answer(InvocationOnMock invocation) {
		// return invocation.getArguments()[0];
		// }
		// });

		when(idao.save(idea)).thenReturn(idea);

		// Controlliamo che la data sia stata impostata
		when(userDao.findAll()).thenReturn(new ArrayList<User>());

		when(idao.save(any(Idea.class))).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return invocation.getArguments()[0];
			}
		});

		// Controlliamo che la data sia stata impostata
		Idea ideaReturn = ideaService.save(idea);
		assertNotNull(ideaReturn.getDateIdea());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void voteTest() throws Exception {

		int voteCounter = 1;
		double voteAverage = 4;

		int vote = 5;

		Idea idea = new Idea();
		idea.setId(id);
		idea.setText(contenuto);
		idea.setVotecounter(voteCounter);
		idea.setVoteaverage(voteAverage);

		when(idao.save(any(Idea.class))).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return invocation.getArguments()[0];
			}
		});
		when(idao.getIdeaById(id)).thenReturn(idea);

		voteCounter++;

		// Controlliamo la correttezza del calcolo della media e del contatore
		Idea ideaReturn = ideaService.vote(idea, vote);
		assertEquals((vote + voteAverage) / voteCounter, ideaReturn.getVoteaverage(), 0.001);
		assertEquals(voteCounter, ideaReturn.getVotecounter());

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void acceptedTest() throws Exception {

		Idea idea = new Idea();
		idea.setText(contenuto);
		idea.setId(id);

		when(userDao.findAll()).thenReturn(new ArrayList<User>());

		when(idao.getIdeaById(id)).thenReturn(idea);

		when(idao.save(any(Idea.class))).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return invocation.getArguments()[0];
			}
		});

		// Controlliamo che l'idea sia accettata
		Idea ideaReturn = ideaService.accept(id);
		assertEquals(true, ideaReturn.isAccepted());

	}

	@SuppressWarnings("rawtypes")
	@Test
	public void updateTest() {
		Idea idea = new Idea();
		idea.setText(contenuto);
		idea.setId(id);
		idea.setAccepted(true);
		Timestamp time = Timestamp.from(Instant.now());
		idea.setDateIdea(time);

		when(userDao.findAll()).thenReturn(new ArrayList<User>());

		when(idao.save(any(Idea.class))).thenAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				return invocation.getArguments()[0];
			}
		});

		// Controlliamo che l'idea ritornata non sia accettata
		// e che la data sia maggiore di quella iniziale
		Idea ideaReturn = ideaService.update(idea);
		assertEquals(false, ideaReturn.isAccepted());
		boolean result = time.before(ideaReturn.getDateIdea());
		assertTrue(!result);
	}

	@Test
	public void listAcceptedTest() {
		List<Idea> listIdeaAccepted = new ArrayList<>();
		List<Comment> listComment1 = new ArrayList<>();
		List<Comment> listComment2 = new ArrayList<>();

		Comment comment = new Comment();
		comment.setAccepted(true);
		comment.setText("Commento 1");

		Comment comment2 = new Comment();
		comment2.setAccepted(true);
		comment2.setText("Commento 2");

		Comment comment3 = new Comment();
		comment3.setAccepted(false);
		comment3.setText("Commento 3");

		listComment1.add(comment);
		listComment1.add(comment3);

		listComment2.add(comment2);

		Idea idea = new Idea();
		idea.setId(id);
		idea.setText(contenuto);
		idea.setAccepted(true);

		idea.setComlist(listComment1);

		Idea idea2 = new Idea();
		idea2.setId(id);
		idea2.setText(contenuto + " " + contenuto);
		idea2.setAccepted(true);

		idea2.setComlist(listComment2);

		listIdeaAccepted.add(idea);
		listIdeaAccepted.add(idea2);

		when(idao.getIdeaByAccepted(true)).thenReturn(listIdeaAccepted);
		List<Idea> listReturn = ideaService.listAccepted();

		// Controlliamo che la lista di idee ritornate contiene solo idee accettate con
		// commenti accettati
		for (Idea idea_ : listReturn) {
			assertTrue(idea_.isAccepted());
			for (Comment comment_ : idea_.getComlist()) {
				assertTrue(comment_.isAccepted());
			}
		}

	}

}
