package guru.springframework.services;

import guru.springframework.converters.RecipeConverter;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private RecipeRepository recipeRepository;
    private RecipeConverter recipeConverter;

    public ImageServiceImpl(RecipeRepository recipeRepository, RecipeConverter recipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeConverter = recipeConverter;
    }

    @Override
    public void saveImage(Long recipeId, MultipartFile imageFile) {

        Optional<Recipe> foundRecipeOptional = recipeRepository.findById(recipeId);

        if(foundRecipeOptional.isPresent()){
            Recipe foundRecipe = foundRecipeOptional.get();
            try{
                foundRecipe.setImage(imageFile.getBytes());
            } catch(IOException e){
                log.debug("Image file could not be converted to bytes: " + e.getMessage());
                return;
            }
            recipeRepository.save(foundRecipe);
        } else {
            log.debug("Recipe not found - recipeId: " + recipeId);
        }
    }
}
