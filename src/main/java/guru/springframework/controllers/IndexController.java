package guru.springframework.controllers;

import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeRepository recipeRepository;

    public IndexController(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"","/","/index","index.html"})
    public String index(Model model){

        log.debug("In index controller!");

        model.addAttribute("recipes",recipeRepository.findAll());

        return "index";
    }

}
