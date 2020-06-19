package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NotesConverterTest {

    Notes testEntity;
    NotesCommand testCommand;

    NotesConverter notesConverter;

    @Before
    public void setUp() throws Exception{
        testEntity = Notes.builder().id(1L).recipeNotes("TestNotesEntity").build();
        testCommand = NotesCommand.builder().id(2L).recipeNotes("TestNotesCommand").build();
        notesConverter = new NotesConverter();
    }

    @Test
    public void convertEntityToCommand() {
        NotesCommand returnCommand = notesConverter.convertEntityToCommand(testEntity);
        assertEquals(testEntity.getId(),returnCommand.getId());
        assertEquals(testEntity.getRecipeNotes(),returnCommand.getRecipeNotes());
    }

    @Test
    public void convertCommandToEntity() {
        Notes returnEntity = notesConverter.convertCommandToEntity(testCommand);
        assertEquals(testCommand.getId(),returnEntity.getId());
        assertEquals(testCommand.getRecipeNotes(),returnEntity.getRecipeNotes());
    }
}