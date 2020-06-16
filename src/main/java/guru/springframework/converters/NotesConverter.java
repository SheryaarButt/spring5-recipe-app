package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.springframework.stereotype.Component;

@Component
public class NotesConverter implements BaseConverter<Notes,NotesCommand> {

    @Override
    public NotesCommand convertEntityToCommand(Notes entity) {
        return null;
    }

    @Override
    public Notes convertCommandToEntity(NotesCommand command) {
        return null;
    }
}
