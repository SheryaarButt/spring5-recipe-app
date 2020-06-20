package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeConverter;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeConverter recipeConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeConverter recipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeConverter = recipeConverter;
    }

    @Override
    public Set<RecipeCommand> getRecipes() {
        log.debug("I'm in the service");

        Set<RecipeCommand> foundRecipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipeEntity ->
            foundRecipes.add(recipeConverter.convertEntityToCommand(recipeEntity))
        );
        return foundRecipes;
    }

    @Override
    public RecipeCommand getRecipe(Long id) {
        Recipe foundRecipe = recipeRepository.findById(id).orElse(null);
        return recipeConverter.convertEntityToCommand(foundRecipe);
    }

    @Override
    public RecipeCommand saveRecipe(RecipeCommand recipe) {
        Recipe recipeEntity = recipeConverter.convertCommandToEntity(recipe);
        Recipe savedRecipe = recipeRepository.save(recipeEntity);
        log.debug("Added recipe ID: " + savedRecipe.getId());
        return recipeConverter.convertEntityToCommand(savedRecipe);
    }
}
