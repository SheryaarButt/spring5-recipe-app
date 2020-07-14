package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class RecipeServiceImplIT {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    UnitOfMeasureService unitOfMeasureService;


    RecipeCommand testRecipe;

    NotesCommand testNotes;

    IngredientCommand testIng1;
    IngredientCommand testIng2;

    UnitOfMeasureCommand testUom;

    IngredientCommand testIngAdd;
    IngredientCommand testIngUpdate;

    @Before
    public void setUp() throws Exception {

        testNotes = NotesCommand.builder().recipeNotes("TestNotes").build();

        testUom = unitOfMeasureService.getUoms().stream().findFirst().get();

        testIng1 = IngredientCommand.builder()
                .amount(new BigDecimal("5.5"))
                .description("TestIngredient1")
                .unitOfMeasure(testUom)
                .build();
        testIng2 = IngredientCommand.builder()
                .amount(new BigDecimal("3.5"))
                .description("TestIngredient2")
                .unitOfMeasure(testUom)
                .build();
        testIngAdd = IngredientCommand.builder()
                .amount(new BigDecimal("3.5"))
                .description("ADD")
                .unitOfMeasure(testUom)
                .build();
        testIngUpdate = IngredientCommand.builder()
                .amount(new BigDecimal("3.5"))
                .description("UPDATE")
                .unitOfMeasure(testUom)
                .build();

        testRecipe = RecipeCommand.builder()
                .notes(testNotes)
                .directions("TestDirections")
                .difficulty(Difficulty.EASY)
                .description("TestDescription")
                .cookTime(5)
                .prepTime(3)
                .servings(7)
                .source("TestSource")
                .url("TestURL")
                .build();
        testRecipe.addIngredient(testIng1);
        testRecipe.addIngredient(testIng2);
    }

    @Test
    public void saveRecipe() {
        RecipeCommand returnRecipe = recipeService.saveRecipe(testRecipe);
        Long savedId = returnRecipe.getId();
        Recipe foundRecipe = recipeRepository.findById(savedId).get();
        assertEquals(savedId,foundRecipe.getId());
        recipeRepository.deleteById(savedId);
    }

    @Test
    public void saveIngredientUpdate(){
        RecipeCommand returnRecipe = recipeService.saveRecipe(testRecipe);

        Long updateId = returnRecipe.getIngredients().stream().findFirst().get().getId();

        testIngUpdate.setId(updateId);

        assertNotEquals(returnRecipe
                .getIngredients()
                .stream()
                .filter(ingredient ->
                        ingredient.getId().equals(testIngUpdate.getId())
                ).findFirst()
                .get()
                .getDescription(), testIngUpdate.getDescription());

        assertEquals(recipeService.saveIngredient(returnRecipe.getId(),testIngUpdate)
                .getIngredients()
                .stream()
                .filter(ingredient ->
                            ingredient.getId().equals(testIngUpdate.getId())
                ).findFirst()
                .get()
                .getDescription(), testIngUpdate.getDescription());

        recipeRepository.deleteById(returnRecipe.getId());
    }

    @Test
    public void saveIngredientAdd(){
        RecipeCommand returnRecipe = recipeService.saveRecipe(testRecipe);
        Long addedRecipeId = returnRecipe
                .getIngredients()
                .stream()
                .map(IngredientCommand::getId)
                .max(Long::compareTo)
                .get() + 1;

        assertTrue(recipeService.saveIngredient(returnRecipe.getId(),testIngAdd)
                .getIngredients()
                .stream()
                .anyMatch(ingredient -> ingredient.getId().equals(addedRecipeId)));
    }
}