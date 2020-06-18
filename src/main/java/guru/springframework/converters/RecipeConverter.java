package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RecipeConverter implements BaseConverter<Recipe, RecipeCommand> {

    @Override
    public RecipeCommand convertEntityToCommand(Recipe entity) {
        RecipeCommand returnCommand = new RecipeCommand();
        BeanUtils.copyProperties(entity,returnCommand);
        return returnCommand;
    }

    @Override
    public Recipe convertCommandToEntity(RecipeCommand command) {
        Recipe returnEntity = new Recipe();
        BeanUtils.copyProperties(command,returnEntity);
        return returnEntity;
    }
}
