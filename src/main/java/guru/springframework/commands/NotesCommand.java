package guru.springframework.commands;

import guru.springframework.domain.Recipe;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotesCommand {
    private Long id;
    private Recipe recipe;
    private String recipeNotes;
}
