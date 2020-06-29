package guru.springframework.controllers;

import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{id:[1-9]\\d*}/ingredients")
    public String showRecipeIngredients(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.getRecipe(Long.parseLong(id)));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId:[1-9]\\d*}/ingredient/{ingredientId:[1-9]\\d*}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId){
        ingredientService.deleteIngredient(Long.parseLong(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }



}
