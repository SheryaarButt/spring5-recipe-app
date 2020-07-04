package guru.springframework.services;

import guru.springframework.converters.RecipeConverter;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.utils.RecipeAppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;
    private final RecipeConverter recipeConverter;

    public ImageServiceImpl(RecipeRepository recipeRepository, RecipeConverter recipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeConverter = recipeConverter;
    }


    @Override
    @Transactional
    public void saveImage(Long recipeId, MultipartFile imageFile) {

        Optional<Recipe> foundRecipeOptional = recipeRepository.findById(recipeId);

        if(foundRecipeOptional.isPresent()){
            Recipe foundRecipe = foundRecipeOptional.get();
            try{
                Byte[] imageBytes = RecipeAppUtils.boxBytes(imageFile.getBytes());
                foundRecipe.setImage(imageBytes);
                recipeRepository.save(foundRecipe);
            } catch(IOException e){
                log.debug("Image file could not be converted to bytes: " + e.getMessage());
            }
        } else {
            log.debug("Recipe not found - recipeId: " + recipeId);
        }
    }
}
