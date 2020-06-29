package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;

import java.util.Set;

public interface RecipeService {

    Set<RecipeCommand> getRecipes();

    RecipeCommand getRecipe(Long id);

    RecipeCommand saveRecipe(RecipeCommand recipe);

    void deleteRecipe(Long id);

}
