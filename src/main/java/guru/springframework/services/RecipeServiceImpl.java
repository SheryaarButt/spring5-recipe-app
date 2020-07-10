package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeConverter;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeConverter recipeConverter;
    private final IngredientService ingredientService;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeConverter recipeConverter,
                             IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.recipeConverter = recipeConverter;
        this.ingredientService = ingredientService;
    }

    @Override
    @Transactional
    public Set<RecipeCommand> getRecipes() {
        log.debug("I'm in the service");
        return StreamSupport.stream(recipeRepository.findAll().spliterator(),true)
                    .map(recipeConverter::convertEntityToCommand)
                    .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    @Transactional
    public RecipeCommand getRecipe(Long id) {
        Recipe foundRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe id: " + id + " not found."));
        return recipeConverter.convertEntityToCommand(foundRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipe(RecipeCommand recipe) {
        Recipe recipeEntity = recipeConverter.convertCommandToEntity(recipe);
        Recipe savedRecipe = recipeRepository.save(recipeEntity);
        log.debug("Added recipe ID: " + savedRecipe.getId());
        return recipeConverter.convertEntityToCommand(savedRecipe);
    }

    @Override
    @Transactional
    public void deleteRecipe(Long id){
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RecipeCommand saveIngredient(Long recipeId, IngredientCommand ingredient) {
        RecipeCommand foundRecipe = getRecipe(recipeId);
        if (foundRecipe != null) {
            foundRecipe.updateIngredient(ingredient.getId(), ingredient);
            return saveRecipe(foundRecipe);
        }
        return null;
    }

}
