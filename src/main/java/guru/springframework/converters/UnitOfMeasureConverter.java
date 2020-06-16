package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureConverter implements BaseConverter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Override
    public UnitOfMeasureCommand convertEntityToCommand(UnitOfMeasure entity) {
        return null;
    }

    @Override
    public UnitOfMeasure convertCommandToEntity(UnitOfMeasureCommand command) {
        return null;
    }
}
