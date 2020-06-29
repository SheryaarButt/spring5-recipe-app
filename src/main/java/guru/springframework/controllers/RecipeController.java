package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeConverter;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService, RecipeConverter recipeConverter) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id:[1-9]\\d*}/show")
    public String show(@PathVariable String id, Model model){

        RecipeCommand returnRecipe;
        if((returnRecipe = recipeService.getRecipe(Long.parseLong(id))) != null){
            model.addAttribute("recipe",returnRecipe);
            return "recipe/show";
        } else {
            return "recipe/error";
        }
    }

    @GetMapping("/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping("/{id:[1-9]\\d*}/update")
    public String updateRecipe(@PathVariable String id, Model model){

        RecipeCommand foundRecipe = recipeService.getRecipe(Long.parseLong(id));
        if(foundRecipe == null){
            log.debug("Recipe not found");
            return "recipe/error";
        }
        model.addAttribute("recipe",foundRecipe);
        return "recipe/recipeform";
    }

    @PostMapping
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedRecipe = recipeService.saveRecipe(command);
        return "redirect:/recipe/" + savedRecipe.getId() + "/show";
    }

    @GetMapping("/{id:[1-9]\\d*}/delete")
    public String delete(@PathVariable String id){
        log.debug("Deleting recipe: " + id);
        recipeService.deleteRecipe(Long.parseLong(id));
        return "redirect:/";
    }

}
