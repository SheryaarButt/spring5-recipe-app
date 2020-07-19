package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeConverter;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService, RecipeConverter recipeConverter) {
        this.recipeService = recipeService;
    }


    @GetMapping("/{id}/show")
    public ModelAndView show(@PathVariable Long id, Model model){
        return new ModelAndView("recipe/show")
                    .addObject("recipe",recipeService.getRecipe(id));
    }

    @GetMapping("/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model){
        RecipeCommand foundRecipe = recipeService.getRecipe(id);
        model.addAttribute("recipe",foundRecipe);
        return "recipe/recipeform";
    }

    @PostMapping
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult result){
        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> {
                log.debug(error.getDefaultMessage());
            });
            return "recipe/recipeform";
        }
        RecipeCommand savedRecipe = recipeService.saveRecipe(command);
        return "redirect:/recipe/" + savedRecipe.getId() + "/show";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        log.debug("Deleting recipe: " + id);
        recipeService.deleteRecipe(id);
        return "redirect:";
    }
}
