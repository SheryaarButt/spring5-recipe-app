package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureConverter implements BaseConverter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Override
    public UnitOfMeasureCommand convertEntityToCommand(UnitOfMeasure entity) {
        UnitOfMeasureCommand returnCommand = new UnitOfMeasureCommand();
        BeanUtils.copyProperties(entity,returnCommand);
        return returnCommand;
    }

    @Override
    public UnitOfMeasure convertCommandToEntity(UnitOfMeasureCommand command) {
        UnitOfMeasure returnEntity = new UnitOfMeasure();
        BeanUtils.copyProperties(command,returnEntity);
        return returnEntity;
    }
}
