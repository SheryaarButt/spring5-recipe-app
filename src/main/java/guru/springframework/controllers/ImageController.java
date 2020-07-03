package guru.springframework.controllers;

import guru.springframework.services.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/recipe/{recipeId:[1-9]\\d*}/image")
public class ImageController {

    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
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


}
