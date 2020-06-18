package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class NotesConverter implements BaseConverter<Notes,NotesCommand> {

    @Override
    public NotesCommand convertEntityToCommand(Notes entity) {
        NotesCommand returnCommand = new NotesCommand();
        BeanUtils.copyProperties(entity,returnCommand);
        return returnCommand;
    }

    @Override
    public Notes convertCommandToEntity(NotesCommand command) {
        Notes returnEntity = new Notes();
        BeanUtils.copyProperties(command,returnEntity);
        return returnEntity;
    }
}
