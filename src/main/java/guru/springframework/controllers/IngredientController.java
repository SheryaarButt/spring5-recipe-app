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
@RequestMapping("/recipe/{recipeId:[1-9]\\d*}/ingredient")
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
    public String showRecipeIngredients(@PathVariable String recipeId, Model model){
        model.addAttribute("recipe",recipeService.getRecipe(Long.parseLong(recipeId)));
        return "recipe/ingredient/list";
    }

    @GetMapping("/{ingredientId:[1-9]\\d*}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId){
        ingredientService.deleteIngredient(Long.parseLong(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredient/showAll";
    }

    @GetMapping("/{ingredientId:[1-9]\\d*}/show")
    public String show(@PathVariable String recipeId,@PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient",ingredientService.getIngredient(Long.parseLong(ingredientId)));
        return "recipe/ingredient/show";
    }

    @GetMapping("/new")
    public String newIngredientForm(@PathVariable String recipeId,Model model){
        model.addAttribute("recipeId",recipeId);
        model.addAttribute("ingredient", new IngredientCommand());
        model.addAttribute("uomList",unitOfMeasureService.getUoms());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/{ingredientId:[1-9]\\d*}/update")
    public String updateIngredientForm(@PathVariable String recipeId,@PathVariable String ingredientId,Model model){
        model.addAttribute("recipeId",recipeId);
        model.addAttribute("ingredient",ingredientService.getIngredient(Long.parseLong(ingredientId)));
        model.addAttribute("uomList",unitOfMeasureService.getUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    public String addOrUpdateIngredient(@ModelAttribute IngredientCommand ingredient, @PathVariable String recipeId){
        recipeService.saveIngredient(Long.parseLong(recipeId),ingredient);
        return "redirect:/recipe/" + recipeId + "/ingredient/showAll";
    }

}
