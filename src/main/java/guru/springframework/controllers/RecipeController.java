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

    @GetMapping("/{id}/show")
    public String show(@PathVariable String id, Model model){

        long parsedId;
        RecipeCommand returnRecipe;

        try{
            parsedId = Long.parseLong(id);
        } catch(NumberFormatException e){
            log.debug("Incorrect format for id: " + e.getMessage());
            return "recipe/error";
        }

        if((returnRecipe = recipeService.getRecipe(parsedId)) != null){
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

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){

        Long formattedId;
        try{
            formattedId = Long.parseLong(id);
        } catch(NumberFormatException e){
            log.debug("Incorrect format for id: " + e.getMessage());
            return "recipe/error";
        }
        RecipeCommand foundRecipe = recipeService.getRecipe(formattedId);
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

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id){
        Long formattedId;
        try{
            formattedId = Long.parseLong(id);
        } catch(NumberFormatException e){
            log.debug("Incorrect format for id: " + e.getMessage());
            return "recipe/error";
        }
        log.debug("Deleting recipe: " + formattedId);
        recipeService.deleteRecipe(formattedId);
        return "redirect:/";
    }

}
