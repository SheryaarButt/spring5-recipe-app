package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/recipe/{recipeId:[1-9]\\d*}/ingredient/{ingredientId:[1-9]\\d*}/show")
    public String show(@PathVariable String recipeId,@PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient",ingredientService.getIngredient(Long.parseLong(ingredientId)));
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId:[1-9]\\d*}/ingredient/new")
    public String newIngredientForm(@PathVariable String recipeId,Model model){
        model.addAttribute("recipeId",recipeId);
        model.addAttribute("ingredient", new IngredientCommand());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId:[1-9]\\d*}/ingredient/{ingredientId:[1-9]\\d*}/update")
    public String updateIngredientForm(@PathVariable String recipeId,@PathVariable String ingredientId,Model model){
        model.addAttribute("recipeId",recipeId);
        model.addAttribute("ingredient",ingredientService.getIngredient(Long.parseLong(ingredientId)));
//        model.addAttribute("uomList",)
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipeId:[1-9]\\d*}/ingredient")
    public String addOrUpdateIngredient(@ModelAttribute IngredientCommand ingredient, @PathVariable String recipeId){
        return null;
    }

}
