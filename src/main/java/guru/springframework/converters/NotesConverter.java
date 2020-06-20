package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesConverter implements BaseConverter<Notes,NotesCommand> {

    @Nullable
    @Override
    public NotesCommand convertEntityToCommand(Notes entity) {
        if(entity == null){
            return null;
        }
        NotesCommand returnCommand = new NotesCommand();
        BeanUtils.copyProperties(entity,returnCommand);
        return returnCommand;
    }

    @Nullable
    @Override
    public Notes convertCommandToEntity(NotesCommand command) {
        if(command == null){
            return null;
        }
        Notes returnEntity = new Notes();
        BeanUtils.copyProperties(command,returnEntity);
        return returnEntity;
    }
}
