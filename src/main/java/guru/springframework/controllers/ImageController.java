package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import guru.springframework.utils.RecipeAppUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
@RequestMapping("/recipe/{recipeId}/image")
public class ImageController {

    private ImageService imageService;
    private RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @ModelAttribute("recipe")
    public RecipeCommand setRecipe(@PathVariable Long recipeId){
        return recipeService.getRecipe(recipeId);
    }

    @GetMapping("/update")
    public String updateImageForm(){

        return "recipe/imageuploadform";
    }

    @PostMapping
    public String updateImage(@ModelAttribute("recipe") RecipeCommand recipe,
                              @ModelAttribute MultipartFile imagefile){
        imageService.saveImage(recipe.getId(),imagefile);
        return "redirect:/recipe/" + recipe.getId() + "/show";
    }

    @GetMapping
    public void getImage(@ModelAttribute("recipe") RecipeCommand recipe,
                         HttpServletResponse response){
        byte[] imageBytes = RecipeAppUtils.unboxBytes(recipe.getImage());
        if(imageBytes != null){
            try(InputStream is = new ByteArrayInputStream(imageBytes)){
                IOUtils.copy(is,response.getOutputStream());
                response.setContentType("image/jpeg");
            } catch(IOException e){
                log.debug("Error retrieving image from recipe ID: " + recipe.getId() + " " + e.getMessage());
            }
        }
    }


}
