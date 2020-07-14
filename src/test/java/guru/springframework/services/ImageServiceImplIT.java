package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ImageServiceImplIT {

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    ImageService imageService;

    @Test
    public void saveImage() {
        //given
        byte[] primBytes = "heythere".getBytes();
        Byte[] boxedBytes = new Byte[primBytes.length];
        int i = 0;
        for(byte b : primBytes){
            boxedBytes[i++] = b;
        }
        Recipe recipe = Recipe.builder().image(boxedBytes).build();
        Recipe savedRecipe = recipeRepository.save(recipe);
        byte[] imageBytes = "new file bytes".getBytes();
        MultipartFile imageFile = new MockMultipartFile("imagefile","text.txt","text/plain",imageBytes);

        //when
        imageService.saveImage(savedRecipe.getId(),imageFile);
        Byte[] updatedImageBytes = recipeRepository.findById(savedRecipe.getId()).get().getImage();

        //then
        assertEquals(imageBytes.length,updatedImageBytes.length);
        recipeRepository.deleteById(savedRecipe.getId());
    }
}