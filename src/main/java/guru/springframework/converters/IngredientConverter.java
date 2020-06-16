package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientConverter implements BaseConverter<Ingredient, IngredientCommand> {
    @Override
    public IngredientCommand convertEntityToCommand(Ingredient entity) {
        return null;
    }

    @Override
    public Ingredient convertCommandToEntity(IngredientCommand command) {
        return null;
    }
}
