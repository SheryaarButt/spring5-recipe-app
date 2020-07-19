package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/recipe/{recipeId}/ingredient")
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/showAll")
    public String showRecipeIngredients(@PathVariable Long recipeId, Model model){
        model.addAttribute("recipe",recipeService.getRecipe(recipeId));
        return "recipe/ingredient/list";
    }

    @GetMapping("/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable Long ingredientId){
        ingredientService.deleteIngredient(ingredientId);
        return "redirect:/recipe/" + recipeId + "/ingredient/showAll";
    }

    @GetMapping("/{ingredientId}/show")
    public String show(@PathVariable String recipeId,@PathVariable Long ingredientId, Model model){
        model.addAttribute("ingredient",ingredientService.getIngredient(ingredientId));
        return "recipe/ingredient/show";
    }

    @GetMapping("/new")
    public String newIngredientForm(@PathVariable Long recipeId,Model model){
        model.addAttribute("recipeId",recipeId);
        model.addAttribute("ingredient", new IngredientCommand());
        model.addAttribute("uomList",unitOfMeasureService.getUoms());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/{ingredientId}/update")
    public String updateIngredientForm(@PathVariable String recipeId,@PathVariable Long ingredientId,Model model){
        model.addAttribute("recipeId",recipeId);
        model.addAttribute("ingredient",ingredientService.getIngredient(ingredientId));
        model.addAttribute("uomList",unitOfMeasureService.getUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    public String addOrUpdateIngredient(@ModelAttribute IngredientCommand ingredient, @PathVariable Long recipeId){
        recipeService.saveIngredient(recipeId,ingredient);
        return "redirect:/recipe/" + recipeId + "/ingredient/showAll";
    }

}
