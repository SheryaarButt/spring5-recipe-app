package guru.springframework.commands;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCommand {
    private Long id;
    private Set<RecipeCommand> recipes = new HashSet<>();
    private String categoryName;

    @Builder
    public CategoryCommand(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
