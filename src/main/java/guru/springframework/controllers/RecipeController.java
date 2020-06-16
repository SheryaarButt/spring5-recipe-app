package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/{id}")
    public String show(@PathVariable String id, Model model){

        long parsedId;
        Recipe returnRecipe;

        try{
            parsedId = Long.parseLong(id);
        } catch(NumberFormatException e){
            System.out.println("Invalid ID: " + e.getMessage());
            return "recipe/error";
        }

        if((returnRecipe = recipeService.getRecipe(parsedId)) != null){
            model.addAttribute("recipe",returnRecipe);
            return "recipe/show";
        } else {
            return "recipe/error";
        }

    }

}
