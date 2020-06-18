package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class IngredientConverter implements BaseConverter<Ingredient, IngredientCommand> {
    @Override
    public IngredientCommand convertEntityToCommand(Ingredient entity) {
        IngredientCommand returnCommand = new IngredientCommand();
        BeanUtils.copyProperties(entity,returnCommand);
        return returnCommand;
    }

    @Override
    public Ingredient convertCommandToEntity(IngredientCommand command) {
        Ingredient returnEntity = new Ingredient();
        BeanUtils.copyProperties(command,returnEntity);
        return returnEntity;
    }
}
