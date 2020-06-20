package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureConverter implements BaseConverter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Nullable
    @Override
    public UnitOfMeasureCommand convertEntityToCommand(UnitOfMeasure entity) {
        if(entity == null){
            return null;
        }
        UnitOfMeasureCommand returnCommand = new UnitOfMeasureCommand();
        BeanUtils.copyProperties(entity,returnCommand);
        return returnCommand;
    }

    @Nullable
    @Override
    public UnitOfMeasure convertCommandToEntity(UnitOfMeasureCommand command) {
        if(command == null){
            return null;
        }
        UnitOfMeasure returnEntity = new UnitOfMeasure();
        BeanUtils.copyProperties(command,returnEntity);
        return returnEntity;
    }
}
