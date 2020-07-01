package guru.springframework.services;

import guru.springframework.commands.*;
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
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class RecipeServiceImplIT {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeService recipeService;

    RecipeCommand testRecipe;

    NotesCommand testNotes;

    CategoryCommand testCat1;
    CategoryCommand testCat2;
    Set<CategoryCommand> categories;

    IngredientCommand testIng1;
    IngredientCommand testIng2;

    UnitOfMeasureCommand testUom1;
    UnitOfMeasureCommand testUom2;
    UnitOfMeasureCommand testUom3;

    IngredientCommand testIngAdd;
    IngredientCommand testIngUpdate;




    @Before
    public void setUp() throws Exception {

        testNotes = NotesCommand.builder().recipeNotes("TestNotes").build();

        testCat1 = CategoryCommand.builder().id(1L).categoryName("American").build();
        testCat2 = CategoryCommand.builder().id(2L).categoryName("Mexican").build();
        categories = new HashSet<>();
        categories.add(testCat1);
        categories.add(testCat2);

        testUom1 = UnitOfMeasureCommand.builder().id(1L).uom("teaspoon").build();
        testUom2 = UnitOfMeasureCommand.builder().id(2L).uom("tablespoon").build();
        testUom3 = UnitOfMeasureCommand.builder().id(3L).uom("dash").build();

        testIng1 = IngredientCommand.builder()
                .amount(new BigDecimal("5.5"))
                .description("TestIngredient1")
                .unitOfMeasure(testUom1)
                .build();
        testIng2 = IngredientCommand.builder()
                .amount(new BigDecimal("3.5"))
                .description("TestIngredient2")
                .unitOfMeasure(testUom2)
                .build();
        testIngAdd = IngredientCommand.builder()
                .amount(new BigDecimal("3.5"))
                .description("ADD")
                .unitOfMeasure(testUom3)
                .build();
        testIngUpdate = IngredientCommand.builder()
                .id(12L)
                .amount(new BigDecimal("3.5"))
                .description("UPDATE")
                .unitOfMeasure(testUom3)
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
        testRecipe.addCategory(testCat1);
        testRecipe.addCategory(testCat2);
        testRecipe.addIngredient(testIng1);
        testRecipe.addIngredient(testIng2);
    }

    @Test
    public void saveRecipe() {
        RecipeCommand returnRecipe = recipeService.saveRecipe(testRecipe);
        Long savedId = returnRecipe.getId();
        Recipe foundRecipe = recipeRepository.findById(savedId).get();
        assertEquals(savedId,foundRecipe.getId());
    }

    @Test
    public void saveIngredientUpdate(){
        RecipeCommand returnRecipe = recipeService.saveRecipe(testRecipe);
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