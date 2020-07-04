package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
@RequestMapping("/recipe/{recipeId:[1-9]\\d*}/image")
public class ImageController {

    private ImageService imageService;
    private RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/update")
    public String updateImageForm(@PathVariable String recipeId, Model model){
        model.addAttribute("recipeId",recipeId);
        return "recipe/imageuploadform";
    }

    @PostMapping
    public String updateImage(@PathVariable String recipeId, @ModelAttribute MultipartFile imagefile){
        imageService.saveImage(Long.parseLong(recipeId),imagefile);
        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping
    public void getImage(@PathVariable String recipeId, HttpServletResponse response){
        RecipeCommand foundRecipe = recipeService.getRecipe(Long.parseLong(recipeId));
        Byte[] boxedImage = foundRecipe.getImage();
        if(boxedImage != null){
            byte[] unboxedImage = new byte[boxedImage.length];
            int i = 0;
            for(Byte b : boxedImage){
                unboxedImage[i++] = b;
            }
            InputStream is = new ByteArrayInputStream(unboxedImage);
            try{
                IOUtils.copy(is,response.getOutputStream());
                response.setContentType("image/jpeg");
            } catch(IOException e){
                log.debug("Error retrieving image from recipe ID: " + recipeId + " " + e.getMessage());
            }
        }
    }


}
