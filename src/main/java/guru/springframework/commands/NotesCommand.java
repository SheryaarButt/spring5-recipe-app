package guru.springframework.commands;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotesCommand {
    private Long id;
    private RecipeCommand recipe;
    private String recipeNotes;
}
