package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class IngredientConverter implements BaseConverter<Ingredient, IngredientCommand> {

    private UnitOfMeasureConverter unitOfMeasureConverter;

    public IngredientConverter(UnitOfMeasureConverter unitOfMeasureConverter) {
        this.unitOfMeasureConverter = unitOfMeasureConverter;
    }

    @Override
    public IngredientCommand convertEntityToCommand(Ingredient entity) {
        IngredientCommand returnCommand = new IngredientCommand();
        BeanUtils.copyProperties(entity,returnCommand,"unitOfMeasure");

        UnitOfMeasure unitOfMeasureEntity;
        if((unitOfMeasureEntity = entity.getUnitOfMeasure()) != null){
            UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
            BeanUtils.copyProperties(unitOfMeasureEntity,unitOfMeasureCommand);
            returnCommand.setUnitOfMeasure(unitOfMeasureCommand);
        }
        return returnCommand;
    }

    @Override
    public Ingredient convertCommandToEntity(IngredientCommand command) {
        Ingredient returnEntity = new Ingredient();
        BeanUtils.copyProperties(command,returnEntity,"unitOfMeasure");

        UnitOfMeasureCommand unitOfMeasureCommand;
        if((unitOfMeasureCommand = command.getUnitOfMeasure()) != null){
            UnitOfMeasure unitOfMeasureEntity = new UnitOfMeasure();
            BeanUtils.copyProperties(unitOfMeasureCommand,unitOfMeasureEntity);
            returnEntity.setUnitOfMeasure(unitOfMeasureEntity);
        }
        return returnEntity;
    }
}
