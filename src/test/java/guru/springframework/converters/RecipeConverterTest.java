package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

public class RecipeConverterTest {

    RecipeConverter recipeConverter;

    Recipe testEntity;
    RecipeCommand testCommand;

    Byte[] testImage;

    Notes testNotes;


    @Before
    public void setUp() throws Exception {

        testImage = new Byte[5];
        testNotes = Notes.builder().id(1L).recipeNotes("TestNotes").recipe(testEntity).build();
        recipeConverter = new RecipeConverter();
    }


    @Test
    public void convertEntityToCommand() {


    }

    @Test
    public void convertCommandToEntity() {
    }
}