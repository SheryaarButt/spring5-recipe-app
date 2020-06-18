package guru.springframework.commands;

import guru.springframework.domain.Difficulty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCommand {
    private Long id;
    private Set<CategoryCommand> categories = new HashSet<>();
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Byte[] image;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<IngredientCommand> ingredients = new HashSet<>();

    @Builder
    public RecipeCommand(Long id, String description,
                         Integer prepTime, Integer cookTime,
                         Integer servings, String source,
                         String url, String directions,
                         Byte[] image, Difficulty difficulty,
                         NotesCommand notes) {
        this.id = id;
        this.description = description;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.source = source;
        this.url = url;
        this.directions = directions;
        this.image = image;
        this.difficulty = difficulty;
        this.notes = notes;
    }
}
