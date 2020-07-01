package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;

import java.util.Set;

public interface RecipeService {

    Set<RecipeCommand> getRecipes();

    RecipeCommand getRecipe(Long id);

    RecipeCommand saveRecipe(RecipeCommand recipe);

    void deleteRecipe(Long id);

    public RecipeCommand saveIngredient(Long recipeId, IngredientCommand ingredient);

}
