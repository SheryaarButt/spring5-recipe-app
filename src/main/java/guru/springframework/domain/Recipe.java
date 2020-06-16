package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe extends BaseEntity{

    @Builder
    public Recipe(Long id, String description,
                  Integer prepTime, Integer cookTime, Integer servings,
                  String source, String url, String directions, Byte[] image,
                  Difficulty difficulty, Notes notes) {
        super(id);
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

    @ManyToMany(mappedBy = "recipes")
    private Set<Category> categories = new HashSet<>();

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    //Setting up BLOB in DB
    @Lob
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    //Notes is owner - associated notes will be deleted if parent recipe is deleted
    @OneToOne(cascade = CascadeType.ALL, mappedBy="recipe")
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="recipe")
    private Set<Ingredient> ingredients = new HashSet<>();




    public void setNotes(Notes notes) {
        notes.setRecipe(this);
        this.notes = notes;
    }

    public void addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        ingredients.add(ingredient);
    }

}
