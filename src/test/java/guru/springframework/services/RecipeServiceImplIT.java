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

import static org.junit.Assert.assertEquals;


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
    Set<IngredientCommand> ingredients;

    UnitOfMeasureCommand testUom1;
    UnitOfMeasureCommand testUom2;


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
        ingredients = new HashSet<>();
        ingredients.add(testIng1);
        ingredients.add(testIng2);

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
        Recipe returnRecipe = recipeService.saveRecipe(testRecipe);
        Long savedId = returnRecipe.getId();
        Recipe foundRecipe = recipeRepository.findById(savedId).get();
        assertEquals(savedId,foundRecipe.getId());
    }
}