package guru.springframework.converters;

import guru.springframework.commands.*;
import guru.springframework.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class RecipeConverterTest {

    RecipeConverter recipeConverter;

    Recipe testEntity;
    RecipeCommand testCommand;

    Notes testNotesEntity;
    NotesCommand testNotesCommand;

    Category testCatEntity1;
    Category testCatEntity2;
    CategoryCommand testCatCommand1;
    CategoryCommand testCatCommand2;
    Set<Category> categoryEntities;
    Set<CategoryCommand> categoryCommands;

    Ingredient testIngEntity1;
    Ingredient testIngEntity2;
    IngredientCommand testIngCommand1;
    IngredientCommand testIngCommand2;
    Set<Ingredient> ingredientEntities;
    Set<IngredientCommand> ingredientCommands;
    
    UnitOfMeasure testUomEntity1;
    UnitOfMeasure testUomEntity2;
    UnitOfMeasureCommand testUomCommand1;
    UnitOfMeasureCommand testUomCommand2;

    byte[] testImage = new byte[5];

    @Mock
    IngredientConverter ingredientConverter;
    @Mock
    CategoryConverter categoryConverter;
    @Mock
    NotesConverter notesConverter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testNotesEntity = Notes.builder().id(1L).recipeNotes("TestNotes").build();
        testNotesCommand = NotesCommand.builder().id(1L).recipeNotes("TestNotes").build();
        
        testCatEntity1 = Category.builder().id(1L).categoryName("TestCat").build();
        testCatEntity2 = Category.builder().id(2L).categoryName("TestCat2").build();
        categoryEntities = new HashSet<>();
        categoryEntities.add(testCatEntity1);
        categoryEntities.add(testCatEntity2);

        testCatCommand1 = CategoryCommand.builder().id(1L).categoryName("TestCat").build();
        testCatCommand2 = CategoryCommand.builder().id(2L).categoryName("TestCat2").build();
        categoryCommands = new HashSet<>();
        categoryCommands.add(testCatCommand1);
        categoryCommands.add(testCatCommand2);
        
        testUomEntity1 = UnitOfMeasure.builder().id(1L).uom("TestUom1").build();
        testUomEntity2 = UnitOfMeasure.builder().id(2L).uom("TestUom2").build();
        testUomCommand1 = UnitOfMeasureCommand.builder().id(1L).uom("TestUom1").build();
        testUomCommand2 = UnitOfMeasureCommand.builder().id(2L).uom("TestUom2").build();

        testIngEntity1 = Ingredient.builder()
                .id(5L)
                .amount(new BigDecimal("5.5"))
                .description("TestIngredient1")
                .unitOfMeasure(testUomEntity1)
                .build();
        testIngEntity2 = Ingredient.builder()
                .id(3L)
                .amount(new BigDecimal("3.5"))
                .description("TestIngredient2")
                .unitOfMeasure(testUomEntity2)
                .build();
        ingredientEntities = new HashSet<>();
        ingredientEntities.add(testIngEntity1);
        ingredientEntities.add(testIngEntity2);

        testIngCommand1 = IngredientCommand.builder()
                .id(5L)
                .amount(new BigDecimal("5.5"))
                .description("TestIngredient1")
                .unitOfMeasure(testUomCommand1)
                .build();
        testIngCommand2 = IngredientCommand.builder()
                .id(3L)
                .amount(new BigDecimal("3.5"))
                .description("TestIngredient2")
                .unitOfMeasure(testUomCommand2)
                .build();
        ingredientCommands = new HashSet<>();
        ingredientCommands.add(testIngCommand1);
        ingredientCommands.add(testIngCommand2);

        testEntity = Recipe.builder()
                .id(1L)
                .notes(testNotesEntity)
                .image(testImage)
                .directions("TestDirections")
                .difficulty(Difficulty.EASY)
                .description("TestDescription")
                .cookTime(5)
                .prepTime(3)
                .servings(7)
                .source("TestSource")
                .url("TestURL")
                .build();
        testEntity.addCategory(testCatEntity1);
        testEntity.addCategory(testCatEntity2);
        testEntity.addIngredient(testIngEntity1);
        testEntity.addIngredient(testIngEntity2);

        testCommand = RecipeCommand.builder()
                .id(1L)
                .notes(testNotesCommand)
                .image(testImage)
                .directions("TestDirections")
                .difficulty(Difficulty.EASY)
                .description("TestDescription")
                .cookTime(5)
                .prepTime(3)
                .servings(7)
                .source("TestSource")
                .url("TestURL")
                .build();
        testCommand.addCategory(testCatCommand1);
        testCommand.addCategory(testCatCommand2);
        testCommand.addIngredient(testIngCommand1);
        testCommand.addIngredient(testIngCommand2);

        recipeConverter = new RecipeConverter(ingredientConverter,categoryConverter,notesConverter);
    }


    @Test
    public void convertEntityToCommand() {
        when(ingredientConverter.convertEntityToCommand(testIngEntity1)).thenReturn(testIngCommand1);
        when(ingredientConverter.convertEntityToCommand(testIngEntity2)).thenReturn(testIngCommand2);
        when(categoryConverter.convertEntityToCommand(testCatEntity1)).thenReturn(testCatCommand1);
        when(categoryConverter.convertEntityToCommand(testCatEntity2)).thenReturn(testCatCommand2);
        when(notesConverter.convertEntityToCommand(testNotesEntity)).thenReturn(testNotesCommand);

        RecipeCommand returnCommand = recipeConverter.convertEntityToCommand(testEntity);
        assertEquals(testEntity.getDescription(),returnCommand.getDescription());
        assertEquals(testEntity.getPrepTime(),returnCommand.getPrepTime());
        assertEquals(testEntity.getCookTime(),returnCommand.getCookTime());
        assertEquals(testEntity.getServings(),returnCommand.getServings());
        assertEquals(testEntity.getSource(),returnCommand.getSource());
        assertEquals(testEntity.getUrl(),returnCommand.getUrl());
        assertEquals(testEntity.getDirections(),returnCommand.getDirections());
        assertEquals(testEntity.getImage().length,returnCommand.getImage().length);
        assertEquals(testEntity.getDifficulty(),returnCommand.getDifficulty());
        assertEquals(testEntity.getNotes().getId(),returnCommand.getNotes().getId());
        assertEquals(testEntity.getNotes().getRecipeNotes(),returnCommand.getNotes().getRecipeNotes());

        assertTrue(categoryCommands.containsAll(returnCommand.getCategories()));
        assertTrue(ingredientCommands.containsAll(returnCommand.getIngredients()));

    }

    @Test
    public void convertCommandToEntity() {
        when(ingredientConverter.convertCommandToEntity(testIngCommand1)).thenReturn(testIngEntity1);
        when(ingredientConverter.convertCommandToEntity(testIngCommand2)).thenReturn(testIngEntity2);
        when(categoryConverter.convertCommandToEntity(testCatCommand1)).thenReturn(testCatEntity1);
        when(categoryConverter.convertCommandToEntity(testCatCommand2)).thenReturn(testCatEntity2);
        when(notesConverter.convertCommandToEntity(testNotesCommand)).thenReturn(testNotesEntity);

        Recipe returnEntity = recipeConverter.convertCommandToEntity(testCommand);
        assertEquals(testCommand.getDescription(),returnEntity.getDescription());
        assertEquals(testCommand.getPrepTime(),returnEntity.getPrepTime());
        assertEquals(testCommand.getCookTime(),returnEntity.getCookTime());
        assertEquals(testCommand.getServings(),returnEntity.getServings());
        assertEquals(testCommand.getSource(),returnEntity.getSource());
        assertEquals(testCommand.getUrl(),returnEntity.getUrl());
        assertEquals(testCommand.getDirections(),returnEntity.getDirections());
        assertEquals(testCommand.getImage().length,returnEntity.getImage().length);
        assertEquals(testCommand.getDifficulty(),returnEntity.getDifficulty());
        assertEquals(testCommand.getNotes().getId(),returnEntity.getNotes().getId());
        assertEquals(testCommand.getNotes().getRecipeNotes(),returnEntity.getNotes().getRecipeNotes());

        assertTrue(categoryEntities.containsAll(returnEntity.getCategories()));
        assertTrue(ingredientEntities.containsAll(returnEntity.getIngredients()));

    }
}