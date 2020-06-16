package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import org.springframework.stereotype.Component;

@Component
public class RecipeConverter implements BaseConverter<Recipe, RecipeCommand> {

    @Override
    public RecipeCommand convertEntityToCommand(Recipe entity) {
        return null;
    }

    @Override
    public Recipe convertCommandToEntity(RecipeCommand command) {
        return null;
    }
}
