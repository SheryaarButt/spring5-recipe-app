package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryConverterTest {

    Category testEntity;
    CategoryCommand testCommand;

    Recipe testRecipeEntity1;
    Recipe testRecipeEntity2;

    RecipeCommand testRecipeCommand1;
    RecipeCommand testRecipeCommand2;

    CategoryConverter categoryConverter;

    @Before
    public void setUp() throws Exception {
        testRecipeEntity1 = Recipe.builder().id(1L).build();
        testRecipeEntity2 = Recipe.builder().id(2L).build();
        testEntity = Category.builder().id(1L).categoryName("Test").build();
        testEntity.getRecipes().add(testRecipeEntity1);
        testEntity.getRecipes().add(testRecipeEntity2);
        testCommand = CategoryCommand.builder().id(1L).categoryName("Test").build();
        categoryConverter = new CategoryConverter();
    }

    @Test
    public void convertEntityToCommand() {
        CategoryCommand returnCommand = categoryConverter.convertEntityToCommand(testEntity);
        assertEquals(testEntity.getId(),returnCommand.getId());
        assertEquals(testEntity.getCategoryName(),returnCommand.getCategoryName());
//        assertTrue(returnCommand.getRecipes().containsAll(testEntity.getRecipes()));
    }

    @Test
    public void convertCommandToEntity() {
        Category returnEntity = categoryConverter.convertCommandToEntity(testCommand);
        assertEquals(testCommand.getId(),returnEntity.getId());
        assertEquals(testCommand.getCategoryName(),returnEntity.getCategoryName());
//        assertTrue(returnEntity.getRecipes().containsAll(testCommand.getRecipes()));
    }
}