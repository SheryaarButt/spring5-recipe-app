package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(CategoryRepository categoryRepository,
                      RecipeRepository recipeRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Recipe recipe = new Recipe();
        recipe.setCookTime(10);
        recipe.setDescription("How to Make Perfect Guacamole");
        recipe.setDifficulty(Difficulty.EASY);
        recipe.setDirections("Cut the avocado, remove flesh: Cut the avocados in half. " +
                "Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. " +
                "(See How to Cut and Peel an Avocado.) Place in a bowl.");
        recipe.setPrepTime(10);
        recipe.setServings(4);
        recipe.setSource("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        recipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        UnitOfMeasure tsp = new UnitOfMeasure();
        tsp.setUom("teaspoon");
        UnitOfMeasure tsb = new UnitOfMeasure();
        tsb.setUom("tablespoon");
        UnitOfMeasure dash = new UnitOfMeasure();
        dash.setUom("dash");
        UnitOfMeasure each = new UnitOfMeasure();
        each.setUom("each");

        unitOfMeasureRepository.save(tsp);
        unitOfMeasureRepository.save(tsb);
        unitOfMeasureRepository.save(dash);
        unitOfMeasureRepository.save(each);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setDescription("ripe avocados");
        ingredient1.setAmount(new BigDecimal(2));
        ingredient1.setUnitOfMeasure(each);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setDescription("salt, more to taste");
        ingredient2.setAmount(new BigDecimal("0.25"));
        ingredient2.setUnitOfMeasure(tsp);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setDescription("fresh lime juice or lemon juice");
        ingredient3.setAmount(new BigDecimal(1));
        ingredient3.setUnitOfMeasure(tsb);

        Ingredient ingredient4 = new Ingredient();
        ingredient4.setDescription("minced red onion or thinly sliced green onion");
        ingredient4.setAmount(new BigDecimal(2));
        ingredient4.setUnitOfMeasure(tsb);

        Ingredient ingredient5 = new Ingredient();
        ingredient5.setDescription("serrano chiles, stems and seeds removed, minced");
        ingredient5.setAmount(new BigDecimal(1));
        ingredient5.setUnitOfMeasure(each);
        
        Ingredient ingredient6 = new Ingredient();
        ingredient6.setDescription("cilantro (leaves and tender stems), finely chopped");
        ingredient6.setAmount(new BigDecimal(2));
        ingredient6.setUnitOfMeasure(tsb);

        Ingredient ingredient7 = new Ingredient();
        ingredient7.setDescription("freshly grated black pepper");
        ingredient7.setAmount(new BigDecimal(1));
        ingredient7.setUnitOfMeasure(dash);

        Ingredient ingredient8 = new Ingredient();
        ingredient8.setDescription("ripe tomato, seeds and pulp removed, chopped");
        ingredient8.setAmount(new BigDecimal("0.5"));
        ingredient8.setUnitOfMeasure(each);
        
        Ingredient ingredient9 = new Ingredient();
        ingredient9.setDescription("Red radishes or jicama, to garnish");
        ingredient9.setAmount(null);
        ingredient9.setUnitOfMeasure(each);

        Ingredient ingredient10 = new Ingredient();
        ingredient10.setDescription("Tortilla chips, to serve");
        ingredient10.setAmount(null);
        ingredient10.setUnitOfMeasure(each);

        Category american = new Category();
        american.setCategoryName("American");

        Category mexican = new Category();
        mexican.setCategoryName("Mexican");

        mexican.getRecipes().add(recipe);
        Category spanish = new Category();
        spanish.setCategoryName("Spanish");

        Category veggie = new Category();
        veggie.setCategoryName("Vegetarian");
        veggie.getRecipes().add(recipe);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        recipe.addIngredient(ingredient4);
        recipe.addIngredient(ingredient5);
        recipe.addIngredient(ingredient6);
        recipe.addIngredient(ingredient7);
        recipe.addIngredient(ingredient8);
        recipe.addIngredient(ingredient9);
        recipe.addIngredient(ingredient10);

        recipe.getCategories().add(mexican);
        recipe.getCategories().add(veggie);

        Notes notes = new Notes();
        notes.setRecipeNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.");
        notes.setRecipe(recipe);

        recipe.setNotes(notes);

        recipeRepository.save(recipe);
        categoryRepository.save(mexican);
        categoryRepository.save(veggie);
        categoryRepository.save(american);
        categoryRepository.save(spanish);


    }
}
