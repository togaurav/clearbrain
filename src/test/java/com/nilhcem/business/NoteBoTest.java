package com.nilhcem.business;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.nilhcem.core.test.TestUtils;
import com.nilhcem.model.Note;
import com.nilhcem.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-test.xml"})
public class NoteBoTest {
	private static final String NOTE_NAME = "My first note";

	@Autowired
	private TestUtils testUtils;
	@Autowired
	private NoteBo noteService;

	@Test
	public void testNote() throws Exception {
		User user = testUtils.getTestUser();
		testAddNote(user);
	}

	private void testAddNote(User user) {
		Date before = Calendar.getInstance().getTime();
		Note note = noteService.addNote(user, NOTE_NAME);
		Date after = Calendar.getInstance().getTime();
		assertNull(note.getCategory());
		assertNull(note.getResolvedDate());
		assertEquals(NOTE_NAME, note.getName());
		assertEquals(user, note.getUser());
		assertFalse(before.after(note.getCreationDate()));
		assertFalse(after.before(note.getCreationDate()));
	}
}