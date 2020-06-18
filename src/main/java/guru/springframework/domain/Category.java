package guru.springframework.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"recipes"})
@NoArgsConstructor
@Entity
public class Category extends BaseEntity{

    @ManyToMany
    @JoinTable(name = "category_recipes",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private Set<Recipe> recipes = new HashSet<>();

    private String categoryName;

    @Builder
    public Category(Long id, String categoryName) {
        super(id);
        this.categoryName = categoryName;
    }
}
