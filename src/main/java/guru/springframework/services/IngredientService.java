package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

import java.util.Set;

public interface IngredientService {
    Set<IngredientCommand> getIngredients();
}
